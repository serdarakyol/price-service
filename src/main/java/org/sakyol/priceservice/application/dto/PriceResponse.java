package org.sakyol.priceservice.application.dto;

import java.time.LocalDateTime;

public record PriceResponse(
        long productId,
        long brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double finalPrice
) {
}
