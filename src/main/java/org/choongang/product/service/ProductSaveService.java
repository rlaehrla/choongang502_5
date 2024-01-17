package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.admin.product.controllers.RequestProduct;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.product.repositories.CategoryRepository;
import org.choongang.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductSaveService {

    private final ProductRepository repository;
    private final FileUploadService fileUploadService;
    private final MemberUtil memberUtil;
    private final FarmerRepository farmerRepository;
    private final CategoryRepository categoryRepository;

    public void save(RequestProduct form){
        // 분류코드 -> 회원아이디_분류코드
        Product product = new ModelMapper().map(form, Product.class);

        Farmer farmer = null;
        if (memberUtil.isAdmin()) {
            String farmerSeq = form.getFarmer();
            farmer = farmerRepository.findByUserId(farmerSeq).orElse(null);
        } else if (memberUtil.isFarmer()) {
            farmer = (Farmer)memberUtil.getMember();
        }

        product.setFarmer(farmer);

        String cateCd = form.getCateCd();
        if (StringUtils.hasText(cateCd)) {
            Category category = categoryRepository.findById(cateCd).orElse(null);
            if (category != null) product.setCategory(category);
        }

        repository.saveAndFlush(product);
        fileUploadService.processDone(product.getGid());
    }
}
