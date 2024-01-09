package org.choongang.board.controllers.blog;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/farm/blog")
@RequiredArgsConstructor
public class BlogController {

    private final Utils utils;

    @GetMapping
    public String blog(Model model){
        model.addAttribute("addCommonScript", new String[] {"tab"});
        return utils.tpl("blog/farmerblog");
    }

    @GetMapping("/intro")
    public String intro(){

        return utils.tpl("blog/_farmIntroduction");
    }
    @GetMapping("/sales")
    public String sales(){

        return utils.tpl("blog/_farmSales");
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
