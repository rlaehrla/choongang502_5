package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Authorities;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthoritiesDeleteService {
    private final AuthoritiesRepository repository;

    /**
     * 특정 authorities 삭제
     * @param seq : authorities의 시퀀스
     */
    public void delete(Long seq){
        Authorities authorities = repository.findById(seq).orElse(null);
        if(authorities != null){
            repository.delete(authorities);
            repository.flush();
        }

    }


    /**
     * 특정 멤버의 authorities 전체 삭제
     * @param seq : 멤버 시퀀스
     */
    public void deleteList(Long seq){
        List<Authorities> authorities = repository.findByMemberSeq(seq).orElse(null);
        for(Authorities autho : authorities){
            delete(autho.getSeq());
        }
    }


}
