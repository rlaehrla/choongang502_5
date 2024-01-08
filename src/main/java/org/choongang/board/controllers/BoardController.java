package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.commons.Utils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final Utils utils;

    @GetMapping("/")
    public String main(){
        return utils.tpl("board/index");
    }

    @GetMapping("/farm/blog")
    public String blog(Model model){
        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        addScript.add("board/blog");
        addCommonScript.add("common");
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        return utils.tpl("blog/farmerblog");
    }

}
