package org.choongang.chatting.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.chatting.service.ChatHistoryInfoService;
import org.choongang.chatting.service.ChatHistorySaveService;
import org.choongang.chatting.service.ChatRoomInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChatController implements ExceptionProcessor {

    private final Utils utils;
    private final ChatHistoryInfoService chatHistoryInfoService;
    private final ChatHistorySaveService chatHistorySaveService;
    private final ChatRoomInfoService chatRoomInfoService;

    @GetMapping
    public String index(Model model) {
        commonProcess("main", model);

        return utils.tpl("chat/index");
    }

    @GetMapping("/{userId}")
    public String room(@PathVariable("userId") String userId, Model model){

        //chatRoomInfoService.getList();


        return utils.tpl("chat/room");
    }

    private void commonProcess(String mode, Model model) {
        List<String> addCommonScript = new ArrayList<>();
        addCommonScript.add("chat");

        model.addAttribute("addCommonScript", addCommonScript);
    }
}
