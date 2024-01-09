package org.choongang.board.controllers.best;

import lombok.RequiredArgsConstructor;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/best")
public class BestController {
    private final Utils utils;

    @GetMapping
    public String best(){
        return utils.tpl("board/best");
    }
}
