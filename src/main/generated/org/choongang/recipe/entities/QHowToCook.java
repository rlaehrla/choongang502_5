package org.choongang.recipe.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHowToCook is a Querydsl query type for HowToCook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHowToCook extends EntityPathBase<HowToCook> {

    private static final long serialVersionUID = 2071003466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHowToCook howToCook = new QHowToCook("howToCook");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gid = createString("gid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QRecipe recipe;

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QHowToCook(String variable) {
        this(HowToCook.class, forVariable(variable), INITS);
    }

    public QHowToCook(Path<? extends HowToCook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHowToCook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHowToCook(PathMetadata metadata, PathInits inits) {
        this(HowToCook.class, metadata, inits);
    }

    public QHowToCook(Class<? extends HowToCook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

