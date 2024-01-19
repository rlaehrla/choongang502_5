package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.AddressAssist;
import org.choongang.member.entities.Address;
import org.choongang.member.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressSaveService {
    private final AddressRepository repository;
    /**
     * 최초등록시 defaultAddress true, 이후 등록시는 false
     *
     *
     * @param seq : memberSeq, 해당 주소를 가지는 멤버 시퀀스
     * @param addressAssist
     */
    public void save(Long seq, AddressAssist addressAssist){
        boolean defaultAddress = !repository.existByMemberSeq(seq);

        Address address = Address.builder()
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
        Address address = repository.findById(seq).orElse(null);
        List<Address> list = new ArrayList<>();
        if(address != null){
            Address _address = null;
            if(addressAssist.isDefaultAddress()){
                _address = repository.findByDefaultAddress(true);
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
