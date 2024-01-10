package org.choongang.member.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.member.entities.Farmer;
import org.choongang.member.entities.QFarmer;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long>, QuerydslPredicateExecutor<Farmer> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Farmer> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Farmer> findByUserId(String userId);

    default boolean existsByEmail(String email) {
        QFarmer farmer = QFarmer.farmer ;

        return exists(farmer.email.eq(email));
    }

    default boolean existsByUserId(String userId) {
        QFarmer farmer = QFarmer.farmer ;

        return exists(farmer.userId.eq(userId));
    }

    /**
     * 이메일과 회원명으로 조회되는지 체크
     */
    default boolean existsByEmailAndName(String email, String name) {
        QFarmer farmer = QFarmer.farmer ;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(farmer.email.eq(email))
                .and(farmer.username.eq(name));

        return exists(builder);
    }
}
