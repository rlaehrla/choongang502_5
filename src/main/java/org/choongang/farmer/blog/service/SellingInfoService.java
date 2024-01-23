package org.choongang.farmer.blog.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.product.constants.ProductStatus;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.QProduct;
import org.choongang.product.repositories.ProductRepository;
import org.choongang.product.service.ProductInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

/**
 * 판매자 1명의 판매 중인 상품 목록
 */
@Service
@RequiredArgsConstructor
public class SellingInfoService {

    private final ProductRepository productRepository ;
    private final HttpServletRequest request ;
    private final ProductInfoService productInfoService ;

    public ListData<Product> getSellingList(String farmerId, ProductSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QProduct product = QProduct.product;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /* 검색 조건 처리 S */

        List<String> cateCd = search.getCateCd();
        List<Long> seq = search.getSeq();
        List<String> status = search.getStatuses();
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();
        String name = search.getName();

        if (farmerId != null) {
            andBuilder.and(product.farmer.userId.eq(farmerId)) ;
        }

        if(cateCd != null && !cateCd.isEmpty()){
            andBuilder.and(product.category.cateCd.in(cateCd));
        }

        if (seq != null && !seq.isEmpty()){
            andBuilder.and(product.seq.in(seq));
        }

        if(status != null && !status.isEmpty()){
            List<ProductStatus> _statuses = status.stream().map(ProductStatus::valueOf).toList();
            andBuilder.and(product.status.in(_statuses));
        }

        if(sdate != null){
            andBuilder.and(product.createdAt.goe(LocalDateTime.of(sdate, LocalTime.of(0, 0, 0))));
        }

        if (edate != null){
            andBuilder.and(product.createdAt.loe(LocalDateTime.of(edate, LocalTime.of(23, 59, 59))));
        }

        if(StringUtils.hasText(name)){

            andBuilder.and(product.name.contains(name.trim()));
        }

        if(true){

            andBuilder.and(product.active.eq(true));
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Product> data = productRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Product> items = data.getContent();
        items.forEach(p -> productInfoService.addProductInfo(p));


        return new ListData<>(items, pagination);
    }
}
