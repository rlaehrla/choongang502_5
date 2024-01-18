package org.choongang.member.repositories;

import org.choongang.member.entities.Address;
import org.choongang.member.entities.QAddress;
import org.choongang.member.entities.QFarmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepository extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address> {

    /*Address findByDefaultAddress(boolean defaultAddress);
    default boolean existByMemberSeq(Long seq){
        QAddress address = QAddress.address1;

        return exists(address.member.seq.eq(seq));
    }*/
}
