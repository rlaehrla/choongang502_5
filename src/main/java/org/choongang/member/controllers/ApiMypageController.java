package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ListData;
import org.choongang.commons.rests.JSONData;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ApiMypageController {

    private final ProductInfoService productInfoService;
    private final FileInfoService fileInfoService;

    @RequestMapping("/recentlyview")
    public JSONData<List<Product>> recentlyView(@RequestParam("seq") List<Long> seqs){
        /*
        List<Product> products = new ArrayList<>();
        for(Long seq : seqs){
            Product product = productInfoService.get(seq);
            List<FileInfo> mainImages = fileInfoService.getListDone(product.getGid(), "product_main");
            List<FileInfo> listImages = fileInfoService.getListDone(product.getGid(), "product_list");

            product.setMainImages(mainImages);
            product.setListImages(listImages);

            products.add(product);
        }


        JSONData<Object> jsonData = new JSONData<>();
        jsonData.setData(products);
        */

        ProductSearch search = new ProductSearch();
        search.setLimit(100000);
        search.setSeq(seqs);
        ListData<Product> data = productInfoService.getList(search);

        return new JSONData<>(data.getItems());
    }
}
