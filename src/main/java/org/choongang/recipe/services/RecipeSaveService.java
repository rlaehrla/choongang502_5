package org.choongang.recipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.recipe.controllers.RequestRecipe;
import org.choongang.recipe.entities.HowToCook;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.HowToCookRepository;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeSaveService {
    private final MemberUtil memberUtil;
    private final RecipeRepository recipeRepository;
    private final HowToCookRepository howToCookRepository;
    private final FileUploadService fileUploadService;
    private ObjectMapper om;

    public Recipe save(RequestRecipe form) {
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Long seq = form.getSeq();

        Recipe data = null;
        HowToCook howToCook = null;
        if(seq != null && mode.equals("edit")) { // 수정
            data = recipeRepository.findById(seq).orElseThrow(RecipeNotFoundException::new);
            howToCook = howToCookRepository.findById(seq).orElseThrow(null);
        } else { // 작성
            data = new Recipe();
            data.setGid(form.getGid());
            data.setMember(memberUtil.getMember());

            howToCook = new HowToCook();
            howToCook.setGid(form.getGid());


        }
        data.setRcpName(form.getRcpName());
        data.setRcpInfo(form.getRcpInfo());
        data.setEstimatedT(form.getEstimatedT());
        data.setCategory(form.getCategory());
        data.setSubCategory(form.getSubCategory());
        data.setAmount(form.getAmount());
        //JSON
        ObjectMapper om = new ObjectMapper();

        List<String> _requiredIng = new ArrayList<>();
        String[] requiredIng = form.getRequiredIng();
        String[] requiredIngEa = form.getRequiredIngEa();
        if (requiredIng != null) {
            for (int i = 0; i < requiredIng.length; i++) {
                _requiredIng.add(String.format("%s_%s", requiredIng[i], requiredIngEa[i]));
            }
        }

        List<String> _subIng = new ArrayList<>();
        String[] subIng = form.getSubIng();
        String[] subIngEa = form.getSubIngEa();
        if (subIng != null) {
            for (int i = 0; i < subIng.length; i++) {
                _requiredIng.add(String.format("%s_%s", subIng[i], subIngEa[i]));
            }
        }

        List<String> _condiments = new ArrayList<>();
        String[] condiments = form.getCondiments();
        String[] condimentsEa = form.getCondimentsEa();
        if (subIng != null) {
            for (int i = 0; i < condiments.length; i++) {
                _requiredIng.add(String.format("%s_%s", condiments[i], condimentsEa[i]));
            }
        }

        try {
            data.setRequiredIng(om.writeValueAsString(_requiredIng));
            data.setSubIng(om.writeValueAsString(_subIng));
            data.setCondiments(om.writeValueAsString(_condiments));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // ---

        howToCook.setContent(form.getContent().toString());
        howToCook.setRecipe(data);


        recipeRepository.saveAndFlush(data);
        howToCookRepository.saveAndFlush(howToCook);

        fileUploadService.processDone(data.getGid());

        return data;
    }
}
