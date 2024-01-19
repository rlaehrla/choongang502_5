package org.choongang.member.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.QAddress;
import org.choongang.member.entities.QFarmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address> {

    Address findByDefaultAddress(boolean defaultAddress);
    Optional<List<Address>> findByMemberSeq(Long seq);

    default Optional<Address> findDefaultAddress(Long memberSeq, boolean defaultAddress){

        QAddress address = QAddress.address1;

        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(address.member.seq.eq(memberSeq));
        andBuilder.and(address.defaultAddress.eq(true));

        return findOne(andBuilder);
    }
}
