package org.choongang.product.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.admin.product.controllers.ProductSearch;
import org.choongang.admin.product.controllers.RequestProduct;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.service.MemberInfoService;
import org.choongang.product.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Query;
import com.querydsl.core.QueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Board;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.UnAuthorizedException;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.product.constants.ProductStatus;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.QProduct;
import org.choongang.product.repositories.ProductRepository;
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
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductRepository productRepository;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final CategoryInfoService categoryInfoService;


    public Product get(Long seq) {
        Product product = productRepository.findById(seq)
                .orElseThrow(ProductNotFoundException::new);

        addProductInfo(product);

        return product;

    }

    /**
     * 상품 목록 조회
     *
     * @param search
     * @param isAll : true - 미노출 상품도 모두 보이게
     *
     * @return
     */

    public ListData<Product> getList(ProductSearch search, boolean isAll){

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QProduct product = QProduct.product;
        BooleanBuilder andBuilder = new BooleanBuilder();


        /* 검색 조건 처리 S */

        // 농부는 본인의 상품만 볼 수 있도록
        if(memberUtil.isFarmer() && !memberUtil.isAdmin()){
            String userId = memberUtil.getMember().getUserId();
            andBuilder.and(product.farmer.userId.eq(userId));
        }

        List<String> cateCd = search.getCateCd();
        List<Long> seq = search.getSeq();
        List<String> status = search.getStatuses();
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();
        String name = search.getName();

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

        if(!isAll){

            andBuilder.and(product.active.eq(true));
        }

        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Product> data = productRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<Product> items = data.getContent();
        items.forEach(this::addProductInfo);


        return new ListData<>(items, pagination);

    }

    /**
     * isAll 입력 안하면 모든 상품 보이게
     * @param search
     * @return
     */
    public ListData<Product> getList(ProductSearch search){
        return getList(search, true);
    }

    /**
     * file 저장
     * @param product
     */

    public void addProductInfo(Product product){
       String gid = product.getGid();

       List<FileInfo> editorImages = fileInfoService.getListDone(gid, "description");
       List<FileInfo> mainImages = fileInfoService.getListDone(gid, "product_main");
       List<FileInfo> listImages = fileInfoService.getListDone(gid, "product_list");

       product.setEditorImages(editorImages);
       product.setMainImages(mainImages);
       product.setListImages(listImages);


    }

    public RequestProduct getForm(Long seq){

        Product product = get(seq);
        RequestProduct form = new ModelMapper().map(product, RequestProduct.class);

        form.setFarmer(product.getFarmer().getUserId());
        form.setCateCd(product.getCategory().getCateCd());

        form.setMode("edit");


        return form;
    }


}
