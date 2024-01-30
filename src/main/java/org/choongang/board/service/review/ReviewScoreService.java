package org.choongang.board.service.review;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewScoreService {

    private final EntityManager em ;
    private final ProductRepository productRepository ;

    /**
     * 상품에 평점 업데이트
     */
    public void update(Long productSeq) {
        /*
        QBoardData boardData = QBoardData.boardData ;

        Double avg = new JPAQueryFactory(em)
                .select(boardData.num2.coalesce(0L).as("num2").avg())
                .from(boardData)
                .where(boardData.num1.eq(productSeq))
                .fetchOne();

        float score = (float) (Math.round(avg * 10.0) / 10.0) ;    // 소수점 첫 째 자리까지 표시

        Product product = productRepository.findById(productSeq).orElse(null) ;
        if (product == null) {    // 상품이 없을 때는 메서드 종료
            return;
        }

        product.setScore(score);    // 상품에 평점 반영
        productRepository.flush();*/
    }
}
