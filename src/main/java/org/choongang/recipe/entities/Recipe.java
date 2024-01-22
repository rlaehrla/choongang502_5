package org.choongang.recipe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.board.entities.CommentData;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.entities.AbstractMember;

import java.util.List;
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

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Column(length = 20, nullable = false)
    private String rcpName;

    @Column(length = 240)
    private String rcpInfo;

    @Column(nullable = false)
    private int EstimatedT;

    @Column(length = 20)//,nullable = false)
    private String category;
    @Column(length = 20) //, nullable = false)
    private String subCategory;


    //@ManyToMany
    //private List<String> tags = new ArrayList<>();

    @Column(nullable = false)
    private int amount;

    @Lob
    private String requiredIng; // JSON

    @Lob
    private String subIng; // 부재료 JSON

    @Lob
    private String condiments; // 양념 JSON

    @Transient
    private List<FileInfo> mainImages; // 대표 이미지, null X

    @Transient
    private boolean editable; // 수정 가능 여부

    @Transient
    private boolean deletable; // 삭제 가능 여부

    @Transient
    private boolean mine; // 게시글 소유자

    @Transient
    private boolean showEditButton; // 수정 버튼 노출 여부

    @Transient
    private boolean showDeleteButton; // 삭제 버튼 노출 여부

    @Transient
    private List<CommentData> comments; // 댓글 목록


}


