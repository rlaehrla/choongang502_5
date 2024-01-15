package org.choongang.product.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.service.MemberInfoService;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductRepository productRepository;
    private final MemberUtil memberUtil;
    private final EntityManager em ;
    private final HttpServletRequest request;
    private final MemberInfoService memberInfoService;


    public Product get(Long seq) {
        String farmer = memberUtil.getMember().getUserId();
        Product product = productRepository.findById(seq)
                .orElseThrow(ProductNotFoundException::new);


        if(!StringUtils.hasText(farmer) || (StringUtils.hasText(farmer) && !farmer.equals(product.getFarmer()))){
            throw new UnAuthorizedException(Utils.getMessage("UnAuthorized", "errors"));
        }

        return product;

    }

    /**
     * 상품 목록
     *     SALE - 판매중
     *     OUT_OF_STOCK - 품절
     *     PREPARE -상품 준비중
     * @param
     * @return
     */
    public List<Product> getList(ProductStatus status){
        String userId = null;
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null){
            userId = session.getAttribute("userId").toString();
        }else {
            throw new UnAuthorizedException();
        }

        QProduct product = QProduct.product;
        PathBuilder<Product> pathBuilder = new PathBuilder<>(Product.class, "product");
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(product.farmer.userId.eq(userId));

        if(status != null){
            andBuilder.and(product.status.eq(status));
        }

        List<Product> items = new JPAQueryFactory(em)
                .selectFrom(product)
                .where(andBuilder)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

        return items;

    }

    public List<Product> getList() {
        return getList(null);
    }
}
