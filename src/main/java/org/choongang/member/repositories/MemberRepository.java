package org.choongang.member.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.QAbstractMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<AbstractMember, Long>, QuerydslPredicateExecutor<AbstractMember> {

    @EntityGraph(attributePaths = "authorities")
    Optional<AbstractMember> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<AbstractMember> findByUserId(String userId);

    default boolean existsByEmail(String email) {
        QAbstractMember member = QAbstractMember.abstractMember;

        return exists(member.email.eq(email));
    }

    default boolean existsByUserId(String userId) {
        QAbstractMember member = QAbstractMember.abstractMember;

        return exists(member.userId.eq(userId));
    }

    /**
     * 이메일과 회원명으로 조회되는지 체크
     */
    default boolean existsByEmailAndName(String email, String name) {
        QAbstractMember member = QAbstractMember.abstractMember;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.eq(email))
                .and(member.username.eq(name));

        return exists(builder);
    }
}
