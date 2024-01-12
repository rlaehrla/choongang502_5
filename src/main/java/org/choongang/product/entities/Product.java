package org.choongang.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.Farmer;
import org.choongang.product.constants.DiscountType;
import org.choongang.product.constants.ProductStatus;


import java.util.List;
import java.util.UUID;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Product extends Base {

    @Id @GeneratedValue
    private Long seq; // 상품 번호

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateCd")
    private Category category; // 상품 분류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmerSeq")
    private Farmer farmer; // 판매 농장주

    @Column(length = 100, nullable = false)
    private String name; // 상품명

    private int consumerPrice; // 소비자가(보이는 금액)
    private int salePrice; // 판매가(결제 기준 금액)

    private boolean useStock; // 재고 사용 여부 - true : 재고 차감
    private int stock; // 옵션을 사용하지 않는 경우 단일 상품 재고, 0 - 무제한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private DiscountType discountType = DiscountType.PERCENT;
    private int discount; // 할인 금액

    @Lob
    private String extraInfo; // 상품 추가 정보 : JSON 문자열로 저장

    private boolean packageDelivery; // 같은 판매자별 묶음 배송 여부
    private int deliveryPrice; // 배송비, 0이면 무료 배송

    @Lob
    private String description; // 상품 상세 설명

    private float score; // 평점

    private boolean active; // 노출 여부 : true -> 소비자 페이지 노출

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private ProductStatus status = ProductStatus.PREPARE; // 상품 상태

    private boolean useOption; // 옵션 사용 여부, true : 옵션 사용, 재고는 옵션쪽 재고 사용

    @Column(length = 60)
    private String optionName; // 옵션명

    @Transient
    private List<FileInfo> mainImages; // 메인 이미지

    @Transient
    private List<FileInfo> listImages; // 목록 이미지

    @Transient
    private List<FileInfo> editorImages; // 에디터에 첨부한 이미지
}
