package org.choongang.member.repositories;

import com.querydsl.core.BooleanBuilder;
import org.choongang.commons.AddressAssist;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.QAddress;
import org.choongang.member.entities.QFarmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address> {

    Address findByDefaultAddress(boolean defaultAddress);
    Optional<List<Address>> findByMemberSeq(Long seq);

    default Optional<Address> findDefaultAddress(Long memberSeq){

        QAddress address = QAddress.address1;

        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(address.member.seq.eq(memberSeq));
        andBuilder.and(address.defaultAddress.eq(true));

        return findOne(andBuilder);
    }

    default Optional<Address> findExists(Long memberSeq, AddressAssist addr){
        QAddress address = QAddress.address1;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(address.member.seq.eq(memberSeq));
        builder.and(address.zoneCode.eq(addr.getZoneCode()));
        builder.and(address.address.eq(addr.getAddressSub()));

        if(StringUtils.hasText(addr.getAddressSub())){
            builder.and(address.addressSub.eq(addr.getAddressSub()));
        }

        return findOne(builder);
    }
}
