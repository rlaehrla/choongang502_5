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

    public static final QRecipe recipe = new QRecipe("recipe");

    public final org.choongang.commons.entities.QBase _super = new org.choongang.commons.entities.QBase(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath EstimatedT = createString("EstimatedT");

    public final StringPath gid = createString("gid");

    public final StringPath howTo = createString("howTo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath rcpCate = createString("rcpCate");

    public final StringPath rcpInfo = createString("rcpInfo");

    public final StringPath rcpName = createString("rcpName");

    public final ListPath<String, StringPath> requiredIng = this.<String, StringPath>createList("requiredIng", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final ListPath<String, StringPath> tag = this.<String, StringPath>createList("tag", String.class, StringPath.class, PathInits.DIRECT2);

    public QRecipe(String variable) {
        super(Recipe.class, forVariable(variable));
    }

    public QRecipe(Path<? extends Recipe> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecipe(PathMetadata metadata) {
        super(Recipe.class, metadata);
    }

}

