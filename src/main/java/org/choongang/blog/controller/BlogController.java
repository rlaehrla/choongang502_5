package org.choongang.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.commons.Utils;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/farm/blog")
@RequiredArgsConstructor
public class BlogController {

    private final Utils utils;
    private final HttpServletRequest request ;
    private final MemberUtil memberUtil ;
    private final ConfigInfoService infoService;

    /**
     * 블로그 메인페이지
     */
    @GetMapping("/{farmerId}")
    public String blog(@PathVariable("farmerId") String farmerId, Model model){

        model.addAttribute("farmerId", farmerId) ;

        List<String> addCommonScript = new ArrayList<>();
        List<String> addCommonCss = new ArrayList<>();

        addCommonScript.add("tab");
        addCommonCss.add("tab");

        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCommonScript", addCommonScript);

        return utils.tpl("blog/farmerblog");
    }

    @GetMapping("/sales")
    public String sales(){

        return utils.tpl("blog/_farmSales");
    }

    /**
     * 소개글 탭
     */
    @GetMapping("/intro/{farmerId}")
    public String intro(@PathVariable("farmerId") String farmerId, Model model){
        System.out.println("farmerId : " + farmerId);

        BlogIntroPost intro = infoService.get(farmerId, BlogIntroPost.class).orElseGet(BlogIntroPost::new);
        model.addAttribute("blogIntroPost", intro);

        return utils.tpl("blog/_farmIntroduction");
    }
    @GetMapping("/review")
    public String review(){

        return utils.tpl("blog/_farmReview");
    }
    @GetMapping("/sns")
    public String sns(){

        return utils.tpl("blog/_farmSns");
    }
}
