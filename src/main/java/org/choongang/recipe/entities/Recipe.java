package org.choongang.recipe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.AbstractMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 레시피 모두 보기 test
 * 모든 레시피
 *
 */

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Recipe extends Base {
    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private AbstractMember member;

    private String rid; // ?

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Column(length = 20, nullable = false)
    private String rcpName;

    //@Lob
    @Column(length = 240)
    private String rcpInfo;

    @Column(nullable = false)
    private int EstimatedT;

    @Column(length = 20,nullable = false)
    private String category;
    private String subCategory;


    @ManyToMany
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private int amount;

    //@Column(length = 20, nullable = false)
    //private List<Ingredient> requiredIng; // 필수재료
    //private List<String> requiredIng; // 필수재료

    @Lob
    private String requiredIng; // JSON

    @Lob
    private String subIng; // 부재료 JSON

    @Lob
    private String condiments; // 양념 JSON

    @Transient
    private FileInfo mainImage; // 대표 이미지, null X


}


