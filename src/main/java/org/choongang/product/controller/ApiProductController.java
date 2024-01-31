package org.choongang.product.controller;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.rests.JSONData;
import org.choongang.member.MemberUtil;
import org.choongang.product.service.ProductWishService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ApiProductController {

    private final ProductWishService productWishService;


    @GetMapping("/save_post/{pSeq}")
    public JSONData<Object> savePost(@PathVariable("pSeq") Long pSeq){

        productWishService.save(pSeq);

        return new JSONData<>();
    }

    @DeleteMapping("/save_post/{pSeq}")
    public JSONData<Object> deletePost(@PathVariable("pSeq") Long pSeq){
        productWishService.delete(pSeq);

        return new JSONData<>();
    }

}
