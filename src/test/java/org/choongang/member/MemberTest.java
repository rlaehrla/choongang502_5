package org.choongang.member;

import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    private MemberRepository repository;

    @Test
    void test1() {
        AbstractMember member = repository.findByUserId("2sujg97").orElseGet(null);
        System.out.println(member.getAddress());
    }
}
