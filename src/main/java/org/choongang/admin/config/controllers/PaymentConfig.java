package org.choongang.admin.config.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfig {
    private String paymentProvider; // 결제 공급자 (예: "KakaoPay", "NaverPa위원회y", 등)
    private String apiKey; // 결제 API 키
    private String apiSecret; // 결제 API 비밀키

    private boolean LBTEnabled; // 무통장입금 사용 여부
    private boolean vaccountEnabled; // 가상계좌 사용 여부
    private boolean creditCardEnabled; // 신용카드 사용 여부
    private boolean bankTransferEnabled; // 계좌이체 사용 여부
    // 추가적인 결제 설정 필드들을 필요에 따라 정의

    private String paymentAgree; // 결제 약관

    private String personalInfoEntrust; // 결제 정보 3자 제공
}
