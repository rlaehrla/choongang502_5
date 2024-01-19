package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.AddressAssist;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Address;
import org.choongang.member.entities.QAddress;
import org.choongang.member.repositories.AddressRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressSaveService {
    private final AddressRepository repository;
    private final MemberRepository memberRepository;

    /**
     * 최초등록시 defaultAddress true, 이후 등록시는 false
     *
     *
     * @param seq : memberSeq, 해당 주소를 가지는 멤버 시퀀스
     * @param addressAssist
     */
    public void save(Long seq, AddressAssist addressAssist){
        List<Address> addresses = repository.findByMemberSeq(seq).orElse(null);
        boolean defaultAddress = true;

        for(Address add : addresses){
            if(add.isDefaultAddress()){
                defaultAddress = false;
                break;
            }
        }

        AbstractMember member = memberRepository.findById(seq).orElseThrow(MemberNotFoundException::new);

        Address address = Address.builder()
                .member(member)
                .address(addressAssist.getAddress())
                .addressSub(addressAssist.getAddressSub())
                .title(addressAssist.getTitle())
                .zoneCode(addressAssist.getZoneCode())
                .defaultAddress(defaultAddress)
                .build();

        repository.saveAndFlush(address);
    }


    /**
     * 기존 주소 변경시 사용
     *
     * @param seq : 주소의 seq
     * @param addressAssist
     */

    public void edit(Long seq, AddressAssist addressAssist){
        Address address = repository.findById(seq).orElse(null); // 변경할 주소
        List<Address> list = new ArrayList<>();

        if(address != null){
            /*
             * 원래 defaultAddress= true로 설정되어있던 주소 -> defaultAddress false로 변경
             */
            Address _address = repository.findDefaultAddress(address.getMember().getSeq()).orElse(null);

            if(_address != null && addressAssist.isDefaultAddress()){
                _address.setDefaultAddress(false);
                list.add(_address);
            }

            address.setTitle(addressAssist.getTitle());
            address.setZoneCode(addressAssist.getZoneCode());
            address.setAddress(addressAssist.getAddress());
            address.setAddressSub(addressAssist.getAddressSub());
            address.setDefaultAddress(addressAssist.isDefaultAddress());

            list.add(address);
        }

        repository.saveAllAndFlush(list);

    }
}
