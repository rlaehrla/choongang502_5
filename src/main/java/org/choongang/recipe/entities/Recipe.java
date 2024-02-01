package org.choongang.recipe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.choongang.board.entities.AuthCheck;
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
public class Recipe extends Base implements AuthCheck {
    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberSeq")
    private AbstractMember member;

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    @Column(length = 100, nullable = false)
    private String rcpName;

    private long rcpLike; // 최근 3개월 좋아요

    @Lob
    private String rcpInfo;

    @Column(nullable = false)
    private int estimatedT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipeCateCd")
    private RecipeCate recipeCate;


    @Column(nullable = false)
    private int amount;

    @JsonIgnore
    private String[] how;

    @JsonIgnore

    private String[] tip;


    @Lob
    private String requiredIng; // JSON

    @Lob
    private String subIng; // 부재료 JSON

    @Lob
    private String condiments; // 양념 JSON

    // 재료 검색 (재료 + 부재료)
    @Column(length=150)
    private String keyword;

    @Transient
    private List<FileInfo> mainImages; // 대표 이미지, null X

    @Transient
    private List<FileInfo> profileImage; // 작성자 프로필 이미지

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
    @JsonIgnore
    private List<CommentData> comments; // 댓글 목록

    @Transient
    private boolean authoritychk;

    private boolean active;// 노출 여부 : true -> 소비자 페이지 노출

    @Transient
    private String[] requiredIngP;
    @Transient
    private String[] requiredIngEaP;

    @Transient
    private String[] subIngP;
    @Transient
    private String[] subIngEaP;

    @Transient
    private String[] condimentsP;
    @Transient
    private String[] condimentsEaP;


}


