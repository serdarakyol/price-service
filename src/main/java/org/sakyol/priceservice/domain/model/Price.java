package org.sakyol.priceservice.domain.model;

import java.time.LocalDateTime;

public record Price(
        long priceListId,
        long brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        long productId,
        int priority,
        double price,
        String currency
) {
}
