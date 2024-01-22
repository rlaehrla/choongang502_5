package org.choongang.recipe.services;

import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.recipe.controllers.RequestRecipe;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RecipeSaveService {
    private final MemberUtil memberUtil;
    private final RecipeRepository recipeRepository;
    private final FileUploadService fileUploadService;

    public Recipe save(RequestRecipe form) {
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Long seq = form.getSeq();

        Recipe data = null;
        if(seq != null && mode.equals("edit")) { // 수정
            data = recipeRepository.findById(seq).orElseThrow(RecipeNotFoundException::new);
        } else { // 작성
            data = new Recipe();
            data.setGid(form.getGid());
            data.setMember(memberUtil.getMember());

        }
        data.setRcpName(form.getRcpName());
        data.setRcpInfo(form.getRcpInfo());
        data.setEstimatedT(form.getEstimatedT());
        data.setCategory(form.getCategory());
        data.setSubCategory(form.getSubCategory());
        data.setAmount(form.getAmount());
        //JSON
        data.setRequiredIng(form.getRequiredIngJSON());
        data.setSubIng(form.getSubIngJSON());
        data.setCondiments(form.getCondimentsJSON());
        // ---

        recipeRepository.saveAndFlush(data);

        fileUploadService.processDone(data.getGid());

        return data;
    }
}
