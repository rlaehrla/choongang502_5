package org.choongang.recipe.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.recipe.controllers.RequestRecipe;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeRepository;
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
    private ObjectMapper om;

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
        data.setRcpName(form.getRcpName());
        data.setRcpInfo(form.getRcpInfo());
        data.setEstimatedT(form.getEstimatedT());
        data.setCategory(form.getCategory());
        data.setSubCategory(form.getSubCategory());
        data.setAmount(form.getAmount());
        data.setRequiredIng(form.getRequiredIngJSON());
        data.setSubIng(form.getSubIngJSON());
        data.setCondiments(form.getCondimentsJSON());
        data.setKeyword(getKeyword(form));
        data.setHow(form.getHow());
        data.setTip(form.getTip());

        recipeRepository.saveAndFlush(data);

        fileUploadService.processDone(data.getGid());
    }

    // 재료로 검색
    private String getKeyword(RequestRecipe form) {
        String[] requiredIng = form.getRequiredIng();
        String[] subIng = form.getSubIng();
        String[] condiments = form.getCondiments();

        List<String> keywords = new ArrayList<>();

        if(requiredIng != null) {
            Arrays.stream(requiredIng).map(s -> "__" + s.trim() + "__")
                    .forEach(keywords::add);
        }

        if(subIng != null) {
            Arrays.stream(subIng).map(s -> "__" + s.trim() + "__")
                    .forEach(keywords::add);
        }

/*        if(condiments != null) {
            Arrays.stream(condiments).map(s -> "__" + s.trim() + "__")
                    .forEach(keywords::add);
        }*/
        return keywords.stream().distinct().collect(Collectors.joining()); // 공백 없이 문자열로 키워드 저장

    }

}
