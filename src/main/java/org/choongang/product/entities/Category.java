package org.choongang.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;
import org.choongang.product.constants.MainCategory;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_category_listOrder", columnList = "listOrder DESC, createdAt DESC"))
public class Category extends BaseMember {
    @Id
    @Column(length = 30)
    private String cateCd; // 분류코드

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private MainCategory mainCategory; // 대분류코드 - 곡물, 채소, 과일

    @Column(length = 60, nullable = false)
    private String cateNm; // 분류명

    private int listOrder; // 진열 가중치

    private boolean active; // 사용 여부
}