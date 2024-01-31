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
    public void savePost(@PathVariable("pSeq") Long pSeq){

        productWishService.save(pSeq);
    }

    @DeleteMapping("/save_post/{pSeq}")
    public void deletePost(@PathVariable("pSeq") Long pSeq){
        productWishService.delete(pSeq);

    }

}
