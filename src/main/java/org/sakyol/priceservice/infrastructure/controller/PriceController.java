package org.sakyol.priceservice.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.sakyol.priceservice.application.dto.PriceResponse;
import org.sakyol.priceservice.application.usecase.GetPriceUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class PriceController {
    private final GetPriceUseCase getPriceUseCase;

    @GetMapping("/api/v1/prices")
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam("application_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
            LocalDateTime applicationDate,
            @RequestParam("product_id") long productId,
            @RequestParam("brand_id") long brandId) {

        return getPriceUseCase.execute(applicationDate, productId, brandId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
