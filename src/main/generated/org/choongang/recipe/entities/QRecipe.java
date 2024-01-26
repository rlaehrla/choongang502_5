package org.choongang.recipe.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = 1421836407L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath category = createString("category");

    public final StringPath condiments = createString("condiments");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> estimatedT = createNumber("estimatedT", Integer.class);

    public final StringPath gid = createString("gid");

    public final ListPath<HowToCook, QHowToCook> howToCook = this.<HowToCook, QHowToCook>createList("howToCook", HowToCook.class, QHowToCook.class, PathInits.DIRECT2);

    public final StringPath keyword = createString("keyword");

    public final org.choongang.member.entities.QAbstractMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath rcpInfo = createString("rcpInfo");

    public final StringPath rcpName = createString("rcpName");

    public final StringPath requiredIng = createString("requiredIng");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath subCategory = createString("subCategory");

    public final StringPath subIng = createString("subIng");

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new org.choongang.member.entities.QAbstractMember(forProperty("member")) : null;
    }

}

