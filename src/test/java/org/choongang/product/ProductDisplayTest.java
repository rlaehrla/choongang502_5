package org.choongang.product;

import jakarta.validation.constraints.AssertTrue;
import org.choongang.product.service.DisplayData;
import org.choongang.product.service.ProductDisplayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductDisplayTest {

    @Autowired
    private ProductDisplayService productDisplayService;

    @Test
    void test1(){
        DisplayData data = productDisplayService.getDisplayData(1706081732704L);

        System.out.println(data);
    }

}
