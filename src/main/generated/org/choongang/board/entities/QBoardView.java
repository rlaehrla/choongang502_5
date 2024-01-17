package org.choongang.board.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardView is a Querydsl query type for BoardView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardView extends EntityPathBase<BoardView> {

    private static final long serialVersionUID = 1653137922L;

    public static final QBoardView boardView = new QBoardView("boardView");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final NumberPath<Integer> uid = createNumber("uid", Integer.class);

    public QBoardView(String variable) {
        super(BoardView.class, forVariable(variable));
    }

    public QBoardView(Path<? extends BoardView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardView(PathMetadata metadata) {
        super(BoardView.class, metadata);
    }

}

