package org.choongang.recipe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

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

    // 임시
    @Column
    private Long id;

    @Column(length = 65)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Column(length = 20, nullable = false)
    private String rcpName;

    //@Lob
    @Column(length = 240)
    private String rcpInfo;

    @Column(length = 10, nullable = false)
    private String EstimatedT;

    @Column(length = 20,nullable = false)
    private String rcpCate;

    @Column(length = 20)
    private List<String> tag;

    @Column(nullable = false)
    private int amount;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requiredIng")*/
    @Column(length = 20, nullable = false)
    //private List<Ingredient> requiredIng; // 필수재료
    private List<String> requiredIng; // 필수재료

    @Column(length = 150, nullable = false)
    private String howTo; // 만드는 방법

    @Transient
    private FileInfo mainImage; // 대표 이미지

    @Transient
    private List<FileInfo> howToImages; // 목록 이미지

}



/*@Data
@NoArgsConstructor
//@Entity
public class Recipe {

    private Long id;
    private String userName;
    private String rcpName;
    private String gid;
    @Transient
    private FileInfo mainImage; // 파일path, 파일 url 포함됨

    public Recipe(String userName, String rcpName, String gid) {
        this.userName = userName;
        this.rcpName = rcpName;
        this.gid = gid;
    }
}*/
