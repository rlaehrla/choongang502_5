package org.choongang.commons.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final Utils utils;


    @GetMapping("/") // 메인 페이지 이동
    public String main(){
        return utils.tpl("board/index");
    }

    @GetMapping("/policy/terms_of_service") // 서비스 이용약관 이동
    public String service() {
        return utils.tpl("outlines/terms_of_service");
    }

    @GetMapping("/policy/privacy") // 개인정보 처리방침 이동
    public String privacy() {
        return utils.tpl("outlines/privacy");
    }
}
