package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.AddressAssist;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.QAddress;
import org.choongang.member.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressInfoService {
    private final AddressRepository addressRepository;
    private final MemberUtil memberUtil;


    public boolean exist(AddressAssist addr){
        QAddress address = QAddress.address1;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(address.member.eq(memberUtil.getMember()));
        builder.and(address.address.eq(addr.getAddress()));
        builder.and(address.addressSub.eq(addr.getAddressSub()));
        builder.and(address.zoneCode.eq(addr.getZoneCode()));

        return addressRepository.exists(builder);
    }


}
