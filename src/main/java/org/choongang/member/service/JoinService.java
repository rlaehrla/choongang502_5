package org.choongang.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.AddressAssist;
import org.choongang.farmer.blog.service.BlogCreateService;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.constants.Authority;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.*;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final FarmerRepository farmerRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator joinValidator;
    private final PasswordEncoder encoder;
    private final FileUploadService uploadService;
    private final AddressRepository addressRepository;
    private final HttpServletRequest request ;
    private final BlogCreateService blogCreateService;
    private final PointSaveService pointSaveService;

    public void process(RequestJoin form, Errors errors) {
        joinValidator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        String mType = request.getParameter("mType") ;

        // mType에 따라 회원 구분
        AbstractMember member = mType.equals("F") ? new Farmer() : new Member();
        member.setEmail(form.getEmail());
        member.setUsername(form.getUsername());
        member.setPassword(hash);
        member.setUserId(form.getUserId());
        member.setGid(form.getGid());
        member.setNickname(form.getNickname());
        member.setTel(form.getTel());

        Authorities authorities = new Authorities();

        // mType에 따라 구별하여 처리
        if (mType.equals("F")) {
            // 농장주 회원
            Farmer farmer = (Farmer) member;
            farmer.setFarmTitle(form.getFarmTitle());
            farmer.setBusinessPermitNum(form.getBusinessPermitNum());

            processFarmer(farmer);

            // 농부 회원 --> FARMER 권한 부여
            authorities.setMember(farmer);
            authorities.setAuthority(Authority.FARMER);

            // 블로그 게시판 자동 생성
            // 블로그의 이름 : farmTitle
            blogCreateService.create(member.getUserId(), form.getFarmTitle());

        } else {
            // 일반 회원
            Member _member = (Member) member;

            processMember(_member);

            // 일반 회원 --> USER 권한 부여
            authorities.setMember(_member);
            authorities.setAuthority(Authority.USER);
        }

        authoritiesRepository.saveAndFlush(authorities);

        AddressAssist addr =  form.getAddr();
        // 주소 처리
        Address address = Address.builder()
                .zoneCode(addr.getZoneCode())
                .address(addr.getAddress())
                .addressSub(addr.getAddressSub())
                .member(member)
                .defaultAddress(true)
                .build();

        addressRepository.saveAndFlush(address);

        // 파일 업로드 완료 처리
        uploadService.processDone(form.getGid());

    }

    public void processMember(Member member) {


        memberRepository.saveAndFlush(member);
        pointSaveService.save(member, 1000, "회원가입을 축하합니다!");
    }

    public void processFarmer(Farmer farmer) {
        farmerRepository.saveAndFlush(farmer);
    }
}

