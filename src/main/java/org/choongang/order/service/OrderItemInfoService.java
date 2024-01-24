package org.choongang.order.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.order.constants.OrderStatus;
import org.choongang.order.controllers.OrderSearch;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderItem;
import org.choongang.order.repositories.OrderItemRepository;
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

@Service
@RequiredArgsConstructor
public class OrderItemInfoService {

    private final OrderItemRepository orderItemRepository;
    private final HttpServletRequest request;


    /**
     * 모든 주문 내역 조회
     */
    public ListData<OrderItem> getAll(OrderSearch search) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QOrderItem orderItem = QOrderItem.orderItem ;
        BooleanBuilder andBuilder = new BooleanBuilder();

        /* 검색 조건 처리 S */
        String productNm = search.getProductNm();
        List<String> orderStatus = search.getOrderStatus() ;
        LocalDate sdate = search.getSdate();
        LocalDate edate = search.getEdate();

        String sopt = search.getSopt();
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL";
        String skey = search.getSkey(); // 키워드

        if (StringUtils.hasText(productNm)) {
            andBuilder.and(orderItem.productName.contains(productNm.trim())) ;
        }

        if (orderStatus != null && !orderStatus.isEmpty()) {
            List<OrderStatus> statuses = orderStatus.stream().map(OrderStatus::valueOf).toList();
            andBuilder.and(orderItem.status.in(statuses));
        }

        if(sdate != null){
            andBuilder.and(orderItem.createdAt.goe(LocalDateTime.of(sdate, LocalTime.of(0, 0, 0))));
        }

        if (edate != null){
            andBuilder.and(orderItem.createdAt.loe(LocalDateTime.of(edate, LocalTime.of(23, 59, 59))));
        }

        // 조건 별 키워드 검색
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression cond = orderItem.productName.contains(skey) ;

            if (sopt.equals("productNm")) {
                andBuilder.and(cond);
            }
        }
        /* 검색 조건 처리 E */

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<OrderItem> data = orderItemRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), limit, 10, request);

        return new ListData<>(data.getContent(), pagination);
    }
}
