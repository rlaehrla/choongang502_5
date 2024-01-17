package org.choongang.main.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] { "main/style" };
    }

    @GetMapping("/")
    public String index() {

        return utils.tpl("main/index");
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
