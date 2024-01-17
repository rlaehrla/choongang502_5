package org.choongang.product.service;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.file.service.FileDeleteService;
import org.choongang.product.entities.Product;
import org.choongang.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDeleteService {
    private final ProductInfoService productInfoService;
    private final FileDeleteService fileDeleteService;
    private final ProductRepository productRepository;
    private final Utils utils;

    public void delete(Long seq) {
        Product product = productInfoService.get(seq);

        String gid = product.getGid();
        fileDeleteService.delete(gid);

        productRepository.delete(product);
        productRepository.flush();
    }

    /**
     * 상품 목록에서 삭제
     *
     * @param chks
     */
    public void deleteList(List<Integer> chks) {
        for (int chk : chks) {
            Long seq = Long.parseLong(utils.getParam("seq_" + chk));
            delete(seq);
        }
    }
}
