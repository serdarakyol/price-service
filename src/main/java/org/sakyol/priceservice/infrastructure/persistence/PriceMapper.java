package org.sakyol.priceservice.infrastructure.persistence;

import org.sakyol.priceservice.domain.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public Price toDomain(PriceJpaEntity entity) {
        if (entity == null) return null;
        return new Price(
                entity.getPriceListId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProductId(),
                entity.getPriority() == null ? 0 : entity.getPriority(),
                entity.getPrice() == null ? 0.0 : entity.getPrice(),
                entity.getCurr()
        );
    }
}
