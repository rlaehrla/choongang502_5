package org.choongang.recipe.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class HowToCook extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="recipeSeq")
    private Recipe recipe;

    @Column(length=150, nullable = false)
    private String content;

    @Transient
    private List<FileInfo> imageFiles;
}
