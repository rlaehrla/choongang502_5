package org.choongang.blog.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FarmerBlogController {

    private final Utils utils;

    @GetMapping("/blog/farmerblog")
    public String blog(Model model) {
        model.addAttribute("farmName", "(농장이름)");

        return utils.tpl("blog/farmerblog");
    }
}