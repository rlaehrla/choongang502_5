package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.member.controllers.MemberForm;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.Authorities;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberEditService {
    private final MemberRepository memberRepository;
    private final FarmerRepository farmerRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final AuthoritiesDeleteService authoritiesDeleteService;


    public void editMember(MemberForm form){
        Long seq = form.getSeq();
        AbstractMember member = memberRepository.findById(seq).orElseThrow(MemberNotFoundException::new);

        String userId = form.getUserId();
        String email = form.getEmail();
        String username = form.getUsername();
        String nickname = form.getNickname();
        String tel = form.getTel();

        /* 회원 권한 설정 S */
        List<String> strAuthorities = form.getAuthorities(); // 폼에서 설정한 권한
        authoritiesDeleteService.deleteList(seq); // 해당 멤버가 가지고있는 모든 권한 삭제
        List<Authorities> authoList = new ArrayList<>();
        for(String autho : strAuthorities){
            Authorities authorities = new Authorities();
            authorities.setMember(member);
            authorities.setAuthority(Authority.valueOf(autho));

            authoList.add(authorities);
            authoritiesRepository.saveAndFlush(authorities);
        }

        /* 회원 권한 설정 E */

        member.setUserId(userId);
        member.setEmail(email);
        member.setUsername(username);
        member.setNickname(nickname);
        member.setTel(tel);
        member.setAuthorities(authoList);

        memberRepository.saveAndFlush(member);
    }
}
