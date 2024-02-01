package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.board.entities.SaveBoardDataId;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.product.entities.*;
import org.choongang.member.repositories.ProductWishRepository;
import org.choongang.product.entities.QProductWish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ProductWishService {
    private final ProductWishRepository productWishRepository;
    private final ProductInfoService productInfoService;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;


    /**
     * 찜기능
     * @param productSeq
     */
    public void save(Long productSeq){

        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");

        }

        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }


        Member member = (Member) memberUtil.getMember();
        Product product = productInfoService.get(productSeq);

        ProductWish productWish = ProductWish.builder()
                .member(member)
                .product(product)
                .build();
        productWishRepository.saveAndFlush(productWish);
    }


    public void delete(Long productSeq){
        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");
        }
        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }
        QProductWish productWish = QProductWish.productWish;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(productWish.product.seq.eq(productSeq));
        builder.and(productWish.member.seq.eq(memberUtil.getMember().getSeq()));

        ProductWish productWish1 = productWishRepository.findOne(builder).orElseThrow(ProductWishNotFoundException::new);

        if(productWish1 != null){
            productWishRepository.delete(productWish1);
            productWishRepository.flush();

        }

    }

    /**
     * 찜한 상품인지 확인
     * @param pSeq
     * @return
     */
    public boolean saved(Long pSeq){
        if(memberUtil.isFarmer()){
            return false;
        }
        if (memberUtil.isLogin()) {
            Product product = productInfoService.get(pSeq);
            Member member = (Member) memberUtil.getMember();
            QProductWish productWish = QProductWish.productWish;

            BooleanBuilder builder = new BooleanBuilder();
            builder.and(productWish.product.eq(product));
            builder.and(productWish.member.seq.eq(member.getSeq()));


            return productWishRepository.exists(builder);
        }
        return false;
    }

    public ListData<ProductWish> getWishProducts(ProductSearch search){
        if(!memberUtil.isLogin()){
            throw new UnAuthorizedException("로그인이 필요한 서비스입니다.");

        }
        if(memberUtil.isFarmer()){
            throw new UnAuthorizedException(Utils.getMessage("NotFarmer", "errors"));
        }
        Member member = (Member) memberUtil.getMember();

        QProductWish productWish = QProductWish.productWish;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(productWish.member.seq.eq(member.getSeq()));

        int page = search.getPage();
        int limit = search.getLimit();

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<ProductWish> data = productWishRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<ProductWish> items = data.getContent();

        for(ProductWish item : items){
            Product product = item.getProduct();
            productInfoService.addProductInfo(product);

            item.setProduct(product);
        }

        return new ListData<>(items, pagination);
    }
}
