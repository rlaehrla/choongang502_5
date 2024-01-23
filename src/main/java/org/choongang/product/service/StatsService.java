package org.choongang.product.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderItem;
import org.choongang.order.repositories.OrderItemRepository;
import org.choongang.order.service.OrderInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final OrderInfoService orderInfoService ;
    private final OrderItemRepository orderItemRepository ;
    private final MemberUtil memberUtil ;
    private final Utils utils ;
    private final HttpServletRequest request;

    /**
     * 최근 1개월 간의 상품 당 판매량
     */
    public void salesSum() {
        ListData<OrderInfo> items = orderInfoService.getList(1) ;


    }

    /**
     * 판매자 별 판매 내역
     */
    public ListData<OrderItem> farmerSales(String farmerId) {

        /* 판매 내역 조회 */
        QOrderItem orderItem = QOrderItem.orderItem ;

        BooleanBuilder andBuilder = new BooleanBuilder();

        if (farmerId != null && !farmerId.isEmpty()) {
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
}
