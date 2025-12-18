package org.sakyol.priceservice;

import org.junit.jupiter.api.Test;
import org.sakyol.priceservice.infrastructure.controller.PriceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PriceServiceApplicationTests {

    @Autowired
    private PriceController priceController;

    @Test
    void contextLoads() {
        assertThat(priceController).isNotNull();
    }

}
