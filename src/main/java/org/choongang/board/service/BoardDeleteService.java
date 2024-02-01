package org.choongang.board.service;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.review.ReviewScoreService;
import org.choongang.file.service.FileDeleteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDeleteService {
    private final BoardAuthService boardAuthService;
    private final BoardDataRepository boardDataRepository;
    private final BoardInfoService boardInfoService;
    private final FileDeleteService fileDeleteService;
    private final ReviewScoreService reviewScoreService ;

    /**
     * 게시글 삭제
     *
     * @param seq
     */
    public void delete(Long seq) {

        // 삭제 권한 체크
        boardAuthService.check("delete", seq);

        BoardData data = boardInfoService.get(seq);

        String gid = data.getGid();

        String bid = data.getBoard().getBid();
        Long num1 = data.getNum1();

        boardDataRepository.delete(data);
        boardDataRepository.flush();

        // 업로드된 파일 삭제
        fileDeleteService.delete(gid);

        // 상품 평점 업데이트
        if (bid.equals("review") && num1 != null) {
            reviewScoreService.update(data.getNum1(), 0L, data.getSeq());
        }
    }
}
