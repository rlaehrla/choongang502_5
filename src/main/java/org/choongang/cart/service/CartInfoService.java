package org.choongang.cart.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.cart.constants.CartType;
import org.choongang.cart.entities.CartInfo;
import org.choongang.cart.entities.QCartInfo;
import org.choongang.cart.repositories.CartInfoRepository;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.product.constants.DiscountType;
import org.choongang.product.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
public class CartInfoService {

    private final CartInfoRepository cartInfoRepository;
    private final FileInfoService fileInfoService;
    private final MemberUtil memberUtil;
    private final Utils utils;

    /**
     * 장바구니에 담겨 있는 상품 목록
     *
     * @param mode : CART : 장바구니 노출 상품
     *               DIRECT : 바로구매 상품
     * @return
     */
    public List<CartInfo> getList(CartType mode) {
        QCartInfo cartInfo = QCartInfo.cartInfo;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(cartInfo.mode.eq(mode));

        if (memberUtil.isLogin()) { // 회원
            andBuilder.and(cartInfo.member.eq((Member)memberUtil.getMember()));
        } else { // 비회원 -> uid,
            andBuilder.and(cartInfo.uid.eq(utils.cartUid()));
        }

        List<CartInfo> items = (List<CartInfo>)cartInfoRepository.findAll(andBuilder, Sort.by(asc("createdAt")));

        items.forEach(this::addItemInfo);

        return items;
    }

    /**
     * 장바구니 상품별 추가 정보
     * @param item
     */
    public void addItemInfo(CartInfo item) {
        Product product = item.getProduct();
        int salePrice = product.getSalePrice();
        int ea = item.getEa();

        DiscountType type = product.getDiscountType();
        int discount = product.getDiscount();

        int totalPrice = salePrice * ea;
        int totalDiscount = 0;
        if (type == DiscountType.PERCENT) { // % 할인
            totalDiscount = (int)Math.round(totalPrice * discount / 100.0);
        } else { // 고정 금액 할인
            totalDiscount = discount;
        }

        item.setTotalPrice(totalPrice);
        item.setTotalDiscount(totalDiscount);

        /* 메인, 리스트 이미지 정보 처리 S */
        String gid = product.getGid();
        List<FileInfo> mainImages = fileInfoService.getListDone(gid, "main");
        List<FileInfo> listImages = fileInfoService.getListDone(gid, "list");
        if (mainImages != null && !mainImages.isEmpty()) {
            item.setMainImage(mainImages.get(0));
        }

        if (listImages != null && !listImages.isEmpty()) {
            item.setListImage(listImages.get(0));
        }
        /* 메인, 리스트 이미지 정보 처리 E */
    }

    /**
     * 장바구니 종합 데이터
     *
     * @param mode
     * @return
     */
    public CartData getCartInfo(CartType mode) {

        int totalPrice = 0, totalDiscount = 0, totalDeliveryPrice = 0, payPrice = 0;
        int totalPackageDeliveryPrice = 0; // 묶음 배송 비용  - 가장 비싼 배송비
        int totalEachDeliveryPrice = 0; // 개별 배송 비용

        List<CartInfo> items = getList(mode);
        for (CartInfo item : items) {
            Product product = item.getProduct();
            int salePrice = product.getSalePrice(); // 상품 판매가
            int ea = item.getEa(); // 구매 수량

            totalPrice += salePrice * ea;

            // 할인
            DiscountType type = product.getDiscountType();
            int discount = product.getDiscount();
            if (type == DiscountType.PERCENT) { // % 할인
                totalDiscount += Math.round(salePrice * ea * discount / 100.0);
            } else { // 고정금액 할인
                totalDiscount += discount;
            }

            // 배송비
            int deliveryPrice = product.getDeliveryPrice();
            if (product.isPackageDelivery()) { // 묶음 배송
                totalPackageDeliveryPrice = totalPackageDeliveryPrice > deliveryPrice ? totalPackageDeliveryPrice : deliveryPrice;
            } else {
                totalEachDeliveryPrice += deliveryPrice; // 개별 배송
            }
        } // endfor

        totalDeliveryPrice = totalPackageDeliveryPrice + totalEachDeliveryPrice; // 배송비

        payPrice = totalPrice + totalDeliveryPrice - totalDiscount; // 결제 금액

        CartData data = CartData.builder()
                .items(items)
                .totalPrice(totalPrice)
                .totalDiscount(totalDiscount)
                .totalDeliveryPrice(totalDeliveryPrice)
                .payPrice(payPrice)
                .build();

        return data;
    }
}
