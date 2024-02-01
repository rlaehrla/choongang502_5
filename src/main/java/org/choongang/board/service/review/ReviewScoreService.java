package org.choongang.board.service.review;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.board.entities.BoardData;
import org.choongang.board.entities.QBoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.BoardInfoService;
import org.choongang.product.entities.Product;
import org.choongang.product.entities.QProduct;
import org.choongang.product.repositories.ProductRepository;
import org.choongang.product.service.ProductInfoService;
import org.choongang.product.service.ProductNotFoundException;
import org.choongang.recipe.entities.QRecipe;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ReviewScoreService {

    private final ProductRepository productRepository ;
    private final BoardDataRepository boardDataRepository;


    /**
     * 평점 업데이트
     * @param productSeq : 상품 번호
     * @param star : 별점
     * @param seq : 보드 번호
     */
    public void update(Long productSeq, Long star, Long seq){
        Product product = productRepository.findById(productSeq).orElseThrow(ProductNotFoundException::new);

        QBoardData boardData = QBoardData.boardData;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(boardData.board.bid.eq("review"));
        builder.and(boardData.num1.eq(productSeq));

        long sum = 0;
        int count = 0;

        Iterator<BoardData> iterator = boardDataRepository.findAll(builder).iterator();
        while(iterator.hasNext()){
            BoardData boardData1 = iterator.next();
            if(boardData1.getSeq() != seq && boardData1.getNum2() != 0){
                sum += boardData1.getNum2();
                count++;
            }else{
                if(star != 0){
                    sum += star;
                    count++;
                }
            }
        }

        product.setScore(sum/(float)count);
        productRepository.saveAndFlush(product);
    }
}
