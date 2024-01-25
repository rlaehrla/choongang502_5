package org.choongang.recipe.services;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.BoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.BoardAuthService;
import org.choongang.board.service.BoardInfoService;
import org.choongang.file.service.FileDeleteService;
import org.choongang.recipe.entities.Recipe;
import org.choongang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeDeleteService {
    private final RecipeRepository recipeRepository;
    private final RecipeInfoService recipeInfoService;
    private final FileDeleteService fileDeleteService;

    /**
     * 레시피 삭제
     *
     * @param seq
     */
    public void delete(Long seq) {

        Recipe data = recipeInfoService.get(seq);
        String gid = data.getGid();

        recipeRepository.delete(data);
        recipeRepository.flush();

        // 업로드된 파일 삭제
        fileDeleteService.delete(gid);
    }


}
