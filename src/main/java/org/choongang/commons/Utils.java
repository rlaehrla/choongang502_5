package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.ApiConfig;
import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.commons.api.BusinessPermit;
import org.choongang.commons.api.BusinessPermitData;
import org.choongang.file.service.FileInfoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;
    private final FileInfoService fileInfoService;
    private final ConfigInfoService infoService ;

    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }

    public boolean isMobile() {
        // 모바일 수동 전환 모드 체크
        String device = (String)session.getAttribute("device");
        if (StringUtils.hasText(device)) {
            return device.equals("MOBILE");
        }

        // 요청 헤더 : User-Agent
        String ua = request.getHeader("User-Agent");

        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

       return ua.matches(pattern);
    }

    public String tpl(String path) {
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }

    public static String getMessage(String code, String type) {
        type = StringUtils.hasText(type) ? type : "validations";

        ResourceBundle bundle = null;
        if (type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        return bundle.getString(code);
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * \n 또는 \r\n -> <br>
     * @param str
     * @return
     */
    public String nl2br(String str) {
        str = Objects.requireNonNullElse(str, "");

        str = str.replaceAll("\\n", "<br>")
                .replaceAll("\\r", "");

        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     * @return
     */
    public List<int[]> getThumbSize() {
        BasicConfig config = (BasicConfig) request.getAttribute("siteConfig");
        String thumbSize = config.getThumbSize(); // \r\n
        String[] thumbsSize = thumbSize.split("\\n");

        List<int[]> data = Arrays.stream(thumbsSize)
                .filter(StringUtils::hasText)
                .map(s -> s.replaceAll("\\s+","")) // 공백제거
                .map(this::toConvert).toList();

        return data;
    }

    private int[] toConvert(String size) {
        size = size.trim();
        int[] data = Arrays.stream(size.replaceAll("\\r", "").toUpperCase().split("X")).mapToInt(Integer::parseInt).toArray();
        // 쪼개서 대소문자 구분없이 만들고 정수로 만들어서 배열했다.

        return data;
    }

    public String printThumb(long seq, int width, int height, String className) {
        String[] data = fileInfoService.getThumb(seq, width, height);
        if (data != null) {
            String  cls = StringUtils.hasText(className) ? " class ='" + className + "'" : "";
            String image = String.format("<img src='%s'%s>", data[1], cls);
            return image;
        }
        return "";
    }

    public String printThumb(long seq, int width, int height) {
        return  printThumb(seq, width, height, null);
    }

    /**
     * 알파벳, 숫자, 특수문자 조합 랜덤 문자열 생성
     */
    public String randomChars() {
        return randomChars(8);
    }

    public String randomChars(int length) {
        // 알파벳 생성
        Stream<String> alphas = IntStream.concat(IntStream.rangeClosed((int)'a', (int)'z'), IntStream.rangeClosed((int)'A', (int)'Z')).mapToObj(s -> String.valueOf((char)s));

        // 숫자 생성
        Stream<String> nums = IntStream.range(0, 10).mapToObj(String::valueOf);

        // 특수문자 생성
        Stream<String> specials = Stream.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "-", "=", "[", "{", "}", "]", ";", ":");

        List<String> chars = Stream.concat(Stream.concat(alphas, nums), specials).collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chars);

        return chars.stream().limit(length).collect(Collectors.joining());
    }

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
                return StringUtils.hasText(bStt) && bStt.equals("계속사업자");
            }
            System.out.println(response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return false;

    }

}
