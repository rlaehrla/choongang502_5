package org.choongang.farmer.management.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/farmer")
@RequiredArgsConstructor
public class MngMainController {

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "dashboard";
    }

    @GetMapping
    public String goIndex(Model model) {
        model.addAttribute("pageTitle", "관리페이지") ;

        return "admin/farmer/main/dashboard" ;
    }
}
