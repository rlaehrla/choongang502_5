package org.choongang.recipe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HowToCook extends Base {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="recipeSeq")
    private Recipe recipe;

    @Column(length=150) //, nullable = false)
    private List<String> how;

    @Column(length=150)
    private List<String> tip;

}
