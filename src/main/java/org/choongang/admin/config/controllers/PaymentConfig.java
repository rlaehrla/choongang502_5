package org.choongang.admin.config.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfig {
    private String paymentProvider; // 결제 공급자 (예: "KakaoPay", "NaverPay" 등)
    private String apiKey; // 결제 API 키
    private String apiSecret; // 결제 API 비밀키


    private String personalInfoEntrust; // 결제 정보 3자 제공
}
