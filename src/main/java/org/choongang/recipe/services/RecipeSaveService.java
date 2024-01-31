package org.choongang.recipe.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.product.entities.Product;
import org.choongang.recipe.controllers.RequestRecipe;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.repositories.RecipeCateRepository;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSaveService {
    private final MemberUtil memberUtil;
    private final RecipeRepository recipeRepository;
    private final FileUploadService fileUploadService;
    private final Utils utils;
    private final RecipeCateRepository recipeCateRepository;

    public void save(RequestRecipe form) {
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

        RecipeCate recipeCate = recipeCateRepository.findById(form.getCateCd()).orElseThrow(RecipeCateNotFoundException::new);

        data.setRcpName(form.getRcpName());
        data.setRcpInfo(form.getRcpInfo());
        data.setEstimatedT(form.getEstimatedT());
        data.setRecipeCate(recipeCate);
        data.setAmount(form.getAmount());
        data.setRequiredIng(form.getRequiredIngJSON());
        data.setSubIng(form.getSubIngJSON());
        data.setCondiments(form.getCondimentsJSON());
        data.setKeyword(getKeyword(form));
        data.setHow(form.getHow());
        data.setTip(form.getTip());
        data.setActive(true);

        recipeRepository.saveAndFlush(data);

        fileUploadService.processDone(data.getGid());
    }

    // 재료로 검색
    private String getKeyword(RequestRecipe form) {
        String[] requiredIng = form.getRequiredIng();
        //String[] subIng = form.getSubIng();

        List<String> keywords = new ArrayList<>();

        if(requiredIng != null) {
            Arrays.stream(requiredIng).map(s -> "__" + s.trim() + "__")
                    .forEach(keywords::add);
        }

        return keywords.stream().distinct().collect(Collectors.joining()); // 공백 없이 문자열로 키워드 저장

    }
    
    /**
     * 관리자 페이지에서 수정
     * @param chks
     */
    public void saveList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 상품을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            Long seq = Long.valueOf(utils.getParam("seq_" + chk));

            Recipe recipe = recipeRepository.findById(seq).orElse(null);
            if (recipe == null) continue;

            boolean active = Boolean.valueOf(utils.getParam("active_" + chk));

            recipe.setActive(active);
            recipeRepository.save(recipe);
        }

        recipeRepository.flush();
    }

}
