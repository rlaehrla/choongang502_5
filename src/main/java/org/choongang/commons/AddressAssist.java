package org.choongang.commons;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AddressAssist {

    @Column(length = 60)
    private String title; // 주소 명칭 ex - 우리집


    private boolean defaultAddress; // 기본배송지인지 확인

    @Column(length = 10, nullable = false)
    private String zoneCode;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 100)
    private String addressSub;
}
