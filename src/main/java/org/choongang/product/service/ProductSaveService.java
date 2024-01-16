package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.RequestCategory;
import org.choongang.admin.product.controllers.RequestProduct;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.AbstractMember;
import org.choongang.product.entities.Category;
import org.choongang.product.entities.Product;
import org.choongang.product.repositories.CategoryRepository;
import org.choongang.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSaveService {

    private final ProductRepository repository;
    private final FileUploadService fileUploadService;

    public void save(RequestProduct form){
        // 분류코드 -> 회원아이디_분류코드
        Product product = new ModelMapper().map(form, Product.class);

        repository.saveAndFlush(product);
        fileUploadService.processDone(product.getGid());
    }
}
