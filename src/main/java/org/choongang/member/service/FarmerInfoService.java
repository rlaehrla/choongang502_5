package org.choongang.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Farmer;
import org.choongang.member.repositories.FarmerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class FarmerInfoService {

    private final FarmerRepository farmerRepository;
    private final HttpServletRequest request;


    public List<Farmer> topFarmer(MemberSearch search){

        Pageable pageable = PageRequest.of(0, 10000, Sort.by(desc("salePoint")));
        Page<Farmer> data = farmerRepository.findAll(pageable);

        return data.getContent();
       //
        //
        // return farmerRepository.findAllByOrderBySalePointDesc();
    }
}
