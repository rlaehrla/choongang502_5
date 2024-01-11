package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.ApiConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.commons.api.BusinessPermit;
import org.choongang.commons.api.BusinessPermitData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessVerifyService {

    private final ConfigInfoService infoService ;

    /**
     * 사업자 등록증 상태 체크
     *
     * @param permitNo : 사업자 등록증 번호
     * @return
     */
    public boolean checkBusinessPermit(String permitNo) {
        if (!StringUtils.hasText(permitNo)) {
            return false;
        }

        // 사업자 등록증 번호 숫자만 제외하고 제거(숫자로 형식 통일)
        permitNo = permitNo.replaceAll("\\D", "");

        // API 설정 조회
        ApiConfig config = infoService.get("apiConfig", ApiConfig.class).orElse(null);

        // 설정이 없거나 공공 API 인증 키가 없는 경우 false
        if (config == null || !StringUtils.hasText(config.getPublicOpenApiKey())) {
            return false;
        }

        // API 요청 url
        String url = String.format("https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=%s", config.getPublicOpenApiKey());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("b_no", permitNo);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            BusinessPermit response = restTemplate.postForObject(new URI(url), request, BusinessPermit.class);

            List<BusinessPermitData> items = response.getData();
            if (items != null && !items.isEmpty()) {
                BusinessPermitData data = items.get(0);

                String bStt = data.getB_stt();
                return StringUtils.hasText(bStt) && bStt.equals("계속사업자");    // 사업자등록증의 상태가 "계속사업자"이어야 함
            }
            System.out.println(response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return false;

    }
}
