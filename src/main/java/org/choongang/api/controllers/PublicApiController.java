package org.choongang.api.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.commons.rests.JSONData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicApiController {
    private final Utils utils;

    @GetMapping("/business_permit/{permitNo}")
    public JSONData<Object> checkBusinessPermit(@PathVariable("permitNo") String permitNo) {
        boolean result = utils.checkBusinessPermit(permitNo);

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(result); // true이면 유효 사업자

        return data;
    }
}