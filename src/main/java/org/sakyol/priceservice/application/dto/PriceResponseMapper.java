package org.sakyol.priceservice.application.dto;

import org.sakyol.priceservice.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceResponseMapper {
    public PriceResponse toResponse(Price price) {
        if (price == null) return null;
        return new PriceResponse(
                price.priceListId(),
                price.productId(),
                price.brandId(),
                price.startDate(),
                price.endDate(),
                price.price()
        );
    }
}
