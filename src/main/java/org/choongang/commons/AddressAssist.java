package org.choongang.commons;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressAssist {

    String title;

    boolean defaultAddress; // 기본배송지인지 확인

    @NotBlank
    String zoneCode;
    @NotBlank
    String address;
    @NotBlank
    String addressSub;

}
