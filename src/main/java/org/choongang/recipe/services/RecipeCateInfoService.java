package org.choongang.recipe.services;

import lombok.RequiredArgsConstructor;
import org.choongang.recipe.entities.RecipeCate;
import org.choongang.recipe.repositories.RecipeCateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeCateInfoService {

    private final RecipeCateRepository recipeCateRepository;


    public List<RecipeCate> getList(){

        return recipeCateRepository.findAll();
    }


}
