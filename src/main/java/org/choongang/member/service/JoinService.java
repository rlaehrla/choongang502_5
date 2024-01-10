package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.Authority;
import org.choongang.member.controllers.FarmerJoinValidator;
import org.choongang.member.controllers.FarmerRequestJoin;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Farmer;
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
    private final FarmerRepository farmerRepository ;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator joinValidator;
    private final FarmerJoinValidator farmerJoinValidator ;
    private final PasswordEncoder encoder;
    private final FileUploadService uploadService;

    public void process(RequestJoin form, Errors errors) {
        joinValidator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setUserId(form.getUserId());
        member.setGid(form.getGid());
        member.setTel(form.getTel());

        process(member);

        // 회원 가입시에는 일반 사용자 권한 부여(USER)
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);

        // 파일 업로드 완료 처리
        uploadService.processDone(form.getGid());

    }

    public void process(Member member) {
        memberRepository.saveAndFlush(member);
    }

    /**
     * 농장주 회원가입 처리
     */
    public void farmerJoinProcess(FarmerRequestJoin form, Errors errors) {
        farmerJoinValidator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());
        Farmer farmer = new Farmer() ;
        farmer.setEmail(form.getEmail());
        farmer.setName(form.getName());
        farmer.setPassword(hash);
        farmer.setUserId(form.getUserId());
        farmer.setGid(form.getGid());
        farmer.setFarmTitle(form.getFarmTitle());
        farmer.setFarmAddress(form.getFarmAddress());
        farmer.setTel(form.getTel());
        farmer.setCertificateNo(form.getCertificateNo());

        farmerJoinProcess(farmer);

        // 회원 가입시에는 판매자 권한 부여
        Authorities authorities = new Authorities();
        authorities.setFarmer(farmer);
        authorities.setAuthority(Authority.FARMER);
        authoritiesRepository.saveAndFlush(authorities);
    }

    public void farmerJoinProcess(Farmer farmer) {
        farmerRepository.saveAndFlush(farmer) ;
    }
}
