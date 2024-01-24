package org.choongang.order.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderItem;
import org.choongang.order.repositories.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemInfoService {

    private final OrderItemRepository orderItemRepository ;
    private final Utils utils ;
    private final HttpServletRequest request;

    /**
     * 상품당 month개월 이내 판매 내역
     *
     * @param month
     * @param productSeq
     * @return
     */
    public ListData<OrderItem> getListMonth(int month, Long productSeq){
        QOrderItem orderItem = QOrderItem.orderItem;

        BooleanBuilder builder = new BooleanBuilder();

        LocalDateTime refDay = LocalDateTime.now().minusMonths(month);
        builder.and(orderItem.product.seq.eq(productSeq));
        builder.and(orderItem.createdAt.goe(refDay));

        /* 페이징 처리 */
        int page = 1;
        int limit = utils.isMobile()? 5 : 10;

        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<OrderItem> data = orderItemRepository.findAll(builder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<OrderItem> items = data.getContent();

        return new ListData<>(items, pagination);
    }

    /**
     * 판매자 별 판매 내역
     */
    public ListData<OrderItem> farmerSales(String farmerId) {

        /* 판매 내역 조회 */
        QOrderItem orderItem = QOrderItem.orderItem ;

        BooleanBuilder andBuilder = new BooleanBuilder();

        if (StringUtils.hasText(farmerId)) {
            andBuilder.and(orderItem.product.farmer.userId.eq(farmerId)) ;
        }


        /* 페이징 처리 */
        int page = 1;
        int limit = 10;

        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<OrderItem> data = orderItemRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<OrderItem> items = data.getContent();

        return new ListData<>(items, pagination);
    }

    /**
     * 전체 판매 내역
     * - 관리자용
     * @return
     */
    public ListData<OrderItem> farmerSales(){
        return farmerSales(null);
    }



}
