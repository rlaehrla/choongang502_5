package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
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

    public void process(RequestJoin form, Errors errors) {
        joinValidator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        String mType = form.getMtype();
        // mType에 따라 회원 구분
        AbstractMember member = mType.equals("F") ? new Farmer() : new Member();
        member.setEmail(form.getEmail());
        member.setUsername(form.getUsername());
        member.setPassword(hash);
        member.setUserId(form.getUserId());
        member.setGid(form.getGid());
        member.setNickname(form.getNickname());
        member.setTel(form.getTel());

        // mType에 따라 구별하여 처리
        if (mType.equals("F")) {
            // 농장주 회원
            Farmer farmer = (Farmer) member;
            farmer.setFarmTitle(form.getFarmTitle());
            farmer.setFarmZonecode(form.getFarmZonecode());
            farmer.setFarmAddress(form.getFarmAddress());
            farmer.setFarmAddressSub(form.getFarmAddressSub());

            processFarmer(farmer);
        } else {
            // 일반 회원
            Member _member = (Member) member;
            _member.setGender(Gender.valueOf(form.getGender()));
            _member.setBirthdate(form.getBirthdate());

            processMember(_member);
        }

        // 회원 가입시에는 일반 사용자 권한 부여(USER)
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);

        // 파일 업로드 완료 처리
        uploadService.processDone(form.getGid());

    }

    public void processMember(Member member) {
        memberRepository.saveAndFlush(member);
    }

    public void processFarmer(Farmer farmer) {
        farmerRepository.saveAndFlush(farmer);
    }
}

