package org.choongang.farmer.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.service.BoardInfoService;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.farmer.blog.intro.BlogIntroPost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/farmer/blog")
@RequiredArgsConstructor
public class BlogController {

    private final Utils utils;
    private final ConfigInfoService infoService;
    private final BoardConfigInfoService boardConfigInfoService;
    private final BoardInfoService boardInfoService;

    @ModelAttribute("addCss")
    private String[] addCss() {

        return new String[] { "farmer/style" };
    }

    @GetMapping
    public String index(@RequestParam(name="tab", defaultValue = "intro") String tab,
                        @ModelAttribute BoardDataSearch search, Model model) {

        model.addAttribute("tab", tab);

        if (tab.equals("intro")) {

            String farmerId = "user01" ;  // 임시
            BlogIntroPost intro = infoService.get(farmerId, BlogIntroPost.class).orElseGet(BlogIntroPost::new);
            model.addAttribute("blogIntroPost", intro);

        } else if (tab.equals("blog")) {
            // 회원 가입 -> 농장 -> 게시판 하나 생성(blog_아이디)
            String bid = "blog_user01";
            Board board = boardConfigInfoService.get(bid);

            ListData<BoardData> data = boardInfoService.getList(bid, search);
            model.addAttribute("board", board);
            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }

        List<String> addCss = new ArrayList<>() ;
        addCss.add("blog/style");

        model.addAttribute("addCss", addCss);

        return utils.tpl("blog/index");
    }
}
