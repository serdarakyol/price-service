package org.sakyol.priceservice.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.sakyol.priceservice.application.dto.PriceResponse;
import org.sakyol.priceservice.application.dto.PriceResponseMapper;
import org.sakyol.priceservice.application.usecase.GetPriceUseCase;
import org.sakyol.priceservice.domain.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetPriceUseCase getPriceUseCase;

    @MockitoBean
    private PriceResponseMapper priceResponseMapper;

    @Test
    void getApplicablePrice_shouldReturn200_whenPriceFound() throws Exception {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Price mockPrice = new Price(1, 1, now, now, 35455, 1, 35.50, "EUR");
        PriceResponse mockResponse = new PriceResponse(1, 35455, 1, now, now, 35.50);

        when(getPriceUseCase.execute(any(), eq(35455L), eq(1L))).thenReturn(Optional.of(mockPrice));
        when(priceResponseMapper.toResponse(mockPrice)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/v1/prices")
                        .param("application_date", "2020-06-14-10.00.00")
                        .param("product_id", "35455")
                        .param("brand_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    void getApplicablePrice_shouldReturn404_whenPriceNotFound() throws Exception {
        // Arrange
        when(getPriceUseCase.execute(any(), anyLong(), anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/prices")
                        .param("application_date", "2020-06-14-10.00.00")
                        .param("product_id", "999")
                        .param("brand_id", "1"))
                .andExpect(status().isNotFound());
    }
}