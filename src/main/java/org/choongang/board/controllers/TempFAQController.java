package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * FAQ 뷰 페이지를 호출하는 컨트롤러(임시)
 */
@Controller
@RequestMapping("/board/faq")
@RequiredArgsConstructor
public class TempFAQController {

    private final Utils utils ;

    @GetMapping
    public String faq() {
        return utils.tpl("board/faq");
    }
}
