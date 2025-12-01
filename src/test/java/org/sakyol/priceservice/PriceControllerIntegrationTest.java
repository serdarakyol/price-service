package org.sakyol.priceservice;

import org.junit.jupiter.api.Test;
import org.sakyol.priceservice.infrastructure.persistence.PriceJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PriceControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceJpaRepository priceRepository;

    private final Long productId = 35455L;
    private final Long brandId = 1L;

    private final String ENDPOINT_URL = "/api/v1/prices";

    /**
     * Test 1: 10:00 del día 14, producto 35455, brand 1.
     * Solo aplica la Tarifa 1.
     */
    @Test
    void test1_findPriceAt1000On14th() throws Exception {
        String date = "2020-06-14T10:00:00";
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceListId").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }


    /**
     * Test 2: Petición a las 16:00 del día 14. (Tarifa 1 vs Tarifa 2. Winner: Tarifa 2 (Prio 1, Price: 25.45))
     */
    @Test
    void test2_findPriceAt1600On14th() throws Exception {
        String date = "2020-06-14-16.00.00";
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceListId").value(2))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    /**
     * Test 3: Petición a las 21:00 del día 14. (Solo Tarifa 1: Prio 0, Price: 35.50)
     */
    @Test
    void test3_findPriceAt2100On14th() throws Exception {
        String date = "2020-06-14-21.00.00";
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceListId").value(1))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    /**
     * Test 4: Petición a las 10:00 del día 15. (Tarifa 1 vs Tarifa 3. Winner: Tarifa 3 (Prio 1, Price: 30.50))
     */
    @Test
    void test4_findPriceAt1000On15th() throws Exception {
        String date = "2020-06-15-10.00.00";
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T15:00:00"))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceListId").value(3))
                .andExpect(jsonPath("$.finalPrice").value(30.50));
    }

    /**
     * Test 5: Petición a las 21:00 del día 16. (Tarifa 1 vs Tarifa 4. Winner: Tarifa 4 (Prio 1, Price: 38.95))
     */
    @Test
    void test5_findPriceAt2100On16th() throws Exception {
        String date = "2020-06-16-21.00.00";
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceListId").value(4))
                .andExpect(jsonPath("$.finalPrice").value(38.95));
    }

    /**
     * Test 6 (Additional check): No price found.
     */
    @Test
    void test6_findNoPrice() throws Exception {
        String date = "2021-01-01-00.00.00"; // Date after all END_DATEs
        mockMvc.perform(get(ENDPOINT_URL)
                        .param("application_date", date)
                        .param("product_id", String.valueOf(productId))
                        .param("brand_id", String.valueOf(brandId)))
                .andExpect(status().isNotFound()); // Should return 404
    }


}
