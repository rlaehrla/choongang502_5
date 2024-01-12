package org.choongang.recipe.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.data.annotation.Transient;


/**
 * 레시피 모두 보기 test
 * 모든 레시피
 *
 */

@Data
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
}
