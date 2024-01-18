package org.choongang.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.controllers.InfoSaveValidator;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.controllers.RequestMemberInfo;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
@Transactional
public class InfoSaveService {

    private final MemberRepository memberRepository;
    private final FarmerRepository farmerRepository;
    private final InfoSaveValidator infoSaveValidator;
    private final PasswordEncoder encoder;
    private final FileUploadService uploadService;
    private final AddressRepository addressRepository;
    private final HttpSession session ;

    public void saveInfo(RequestMemberInfo form, Errors errors) {

        infoSaveValidator.validate(form, errors);
        if (errors.hasErrors()) {
            System.out.println("validator X");
            return;
        }

        // 비밀번호 BCrypt로 해시화


        AbstractMember user = (AbstractMember) session.getAttribute("member");
        user.setUsername(form.getUsername());
        String password = form.getPassword();
        if (StringUtils.hasText(password)) {
            String hash = encoder.encode(password.trim());
            user.setPassword(hash);
        }
        user.setTel(form.getTel());

        if (user instanceof Member) {
            Member member = (Member) user ;
            memberRepository.saveAndFlush(member) ;

        } else if (user instanceof Farmer) {
            Farmer farmer = (Farmer) user ;
            farmer.setFarmTitle(form.getFarmTitle());

            farmerRepository.saveAndFlush(farmer) ;
        }

        // 주소 처리
        Address address = Address.builder()
                .zoneCode(form.getZoneCode())
                .address(form.getAddress())
                .addressSub(form.getAddressSub())
                .member(user)
                .defaultAddress(true)
                .build();

        addressRepository.saveAndFlush(address);


        // 파일 업로드 완료 처리
        uploadService.processDone(form.getGid());
    }
}
