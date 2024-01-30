package org.choongang.order.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.order.entities.OrderInfo;
import org.choongang.order.entities.OrderItem;
import org.choongang.order.entities.QOrderInfo;
import org.choongang.order.repositories.OrderInfoRepository;
import org.choongang.product.entities.Product;
import org.choongang.product.service.ProductInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;
    private final ProductInfoService productInfoService;
    private final MemberUtil memberUtil;
    private final Utils utils;
    private final HttpServletRequest request;

    /**
     *  주문서 조회
     *
     * @Param seq
     * @return
     */
    public OrderInfo get(Long seq){

        OrderInfo orderInfo = orderInfoRepository.findById(seq).orElseThrow(OrderNotFoundException::new);

        List<OrderItem> items = orderInfo.getOrderItems();
        for(OrderItem item : items){
            Product product = productInfoService.get(item.getProduct().getSeq());

            item.setProduct(product);
        }

        orderInfo.setOrderItems(items);

        return orderInfo;
    }

    /**
     * 주문내역 조회
     *
     * @Param month : month개월 이내 내역 조회
     * @return
     */
    public ListData<OrderInfo> getList(int month){

        /* 3개월 이내 내역 조회 */
        QOrderInfo orderInfo = QOrderInfo.orderInfo;

        BooleanBuilder andBuilder = new BooleanBuilder();


        Long memberSeq = memberUtil.getMember().getSeq();
        LocalDateTime refDay = LocalDateTime.now().minusMonths(month);

        if(month == 0){
            refDay = memberUtil.getMember().getCreatedAt();
        }

        andBuilder.and(orderInfo.member.seq.eq(memberSeq));

        andBuilder.and(orderInfo.createdAt.goe(refDay));

        /* 페이징 처리 */
        String pageStr = StringUtils.hasText(request.getParameter("page")) ?request.getParameter("page") : "1" ;
        String limitStr = StringUtils.hasText(request.getParameter("limit")) ? request.getParameter("limit") : "10";

        int page = Utils.onlyPositiveNumber(Integer.parseInt(pageStr), 1);
        int limit = Utils.onlyPositiveNumber(Integer.parseInt(limitStr), 10);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<OrderInfo> data = orderInfoRepository.findAll(andBuilder, pageable);

        for(OrderInfo info : data){
            List<OrderItem> items = info.getOrderItems();
            for(OrderItem item : items){
                Product product = productInfoService.get(item.getProduct().getSeq());
                item.setProduct(product);
            }
        }

        Pagination pagination = new Pagination(page, (int) data.getTotalElements(), 10, limit, request);

        List<OrderInfo> items = data.getContent();

        return new ListData<>(items, pagination);
    }

    /**
     * 회원가입 이후 구매내역 조회
     * @return
     */
    public ListData<OrderInfo> getList(){
        return getList(0);
    }


    /**
     * 비회원 주문조회
     * @param orderNo
     * @param orderCellPhone
     * @return
     */
    public OrderInfo getNonmember(Long orderNo, String orderCellPhone){
        QOrderInfo orderInfo = QOrderInfo.orderInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(orderInfo.orderNo.eq(orderNo));
        builder.and(orderInfo.orderCellphone.eq(orderCellPhone));

        return orderInfoRepository.findOne(builder).orElse(null);
    }

}
