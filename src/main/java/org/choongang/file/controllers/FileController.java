package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.file.service.FileDeleteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements ExceptionProcessor {

    private final FileDeleteService deleteService;

    @GetMapping("/upload")
    public String upload() {

        return "upload";
    }

    // 주소를 가지고 삭제할때 알림
    @GetMapping("/delete/{seq}")
    public void delete(@PathVariable("seq") Long seq) {
        deleteService.delete(seq);
    }
}
