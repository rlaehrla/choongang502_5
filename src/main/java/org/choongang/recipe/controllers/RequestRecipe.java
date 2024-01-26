package org.choongang.recipe.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.choongang.file.entities.FileInfo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RequestRecipe {
    private ObjectMapper om;

    private String mode = "add";
    private Long seq; // 게시글 번호
    private String gid = UUID.randomUUID().toString();

    //@NotBlank
    private String poster; // 글 작성자

    @NotBlank
    private String rcpName; // 글 제목
    private String rcpInfo; // 글 소개

    //@NotBlank
    private int estimatedT;

    private String category;
    private String subCategory;
    private int amount;

    private String[] requiredIng; // 필수재료
    private String[] requiredIngEa; // 필수재료 수량

    private String[] subIng; // 부재료
    private String[] subIngEa; // 부재료 수량

    private String[] condiments; // 양념
    private String[] condimentsEa; // 양념 수량

    private List<FileInfo> mainImages;

    private List<String> how;
    private List<String> tip;

    public RequestRecipe() {
        om = new ObjectMapper();
    }

    public String getRequiredIngJSON() { // 필수재료 JSON 데이터 변환
        List<String[]> data = new ArrayList<>();
        if (requiredIng != null && requiredIng.length > 0) {
            for (int i = 0; i < requiredIng.length; i++) {
                String content = requiredIng[i];
                if (!StringUtils.hasText(content)) continue;

                String ea = requiredIngEa[i];
                ea = StringUtils.hasText(ea) ? ea : "";

                data.add(new String[] { content.trim(),  ea.trim()});
            }
        }
        String json =  null;
        try {
            json = om.writeValueAsString(data);
        } catch (JsonProcessingException e) {}

        return json;
    }

    public String getSubIngJSON() { // 부재료 JSON 데이터 변환
        List<String[]> data = new ArrayList<>();
        if (subIng != null && subIng.length > 0) {
            for (int i = 0; i < subIng.length; i++) {
                String content = subIng[i];
                if (!StringUtils.hasText(content)) continue;

                String ea = subIngEa[i];
                ea = StringUtils.hasText(ea) ? ea : "";

                data.add(new String[] { content.trim(),  ea.trim()}); // 0: 재료 / 1: 수량
            }
        }
        String json =  null;
        try {
            json = om.writeValueAsString(data);
        } catch (JsonProcessingException e) {}

        return json;
    }

    public String getCondimentsJSON() { // 양념 JSON 데이터 변환
        List<String[]> data = new ArrayList<>();
        if (condiments != null && condiments.length > 0) {
            for (int i = 0; i < condiments.length; i++) {
                String content = condiments[i];
                if (!StringUtils.hasText(content)) continue;

                String ea = condimentsEa[i];
                ea = StringUtils.hasText(ea) ? ea : "";

                data.add(new String[] { content.trim(),  ea.trim()});
            }
        }
        String json =  null;
        try {
            json = om.writeValueAsString(data);
        } catch (JsonProcessingException e) {}

        return json;
    }
}
