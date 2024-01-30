package org.choongang.board.controllers.best;

import lombok.RequiredArgsConstructor;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Farmer;
import org.choongang.member.service.FarmerInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/best")
public class BestController {
    private final Utils utils;
    private final FarmerInfoService farmerInfoService;
    private final FileInfoService fileInfoService;

    @GetMapping
    public String best(Model model){

        /* 농장 랭킹 S */
        MemberSearch search = new MemberSearch();
        search.setPage(1);
        search.setLimit(20);
        ListData<Farmer> farmerData = farmerInfoService.topFarmer(search);
        List<Farmer> farmers = farmerData.getItems().stream().limit(20).toList();

        for(Farmer farmer : farmers){
            List<FileInfo> profileImage =  fileInfoService.getListDone(farmer.getGid());

            if(!profileImage.isEmpty() && profileImage != null){
                farmer.setProfileImage(profileImage.get(0));
            }
        }

        /* 농장 랭킹 E */

        model.addAttribute("farmers", farmers);

        return utils.tpl("board/best");
    }
}
