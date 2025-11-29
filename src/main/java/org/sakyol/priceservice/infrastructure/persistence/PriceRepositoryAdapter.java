package org.sakyol.priceservice.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.sakyol.priceservice.domain.model.Price;
import org.sakyol.priceservice.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceJpaRepository priceJpaRepository;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, long productId, long brandId) {
        return priceJpaRepository.findApplicable(applicationDate, productId, brandId).stream()
                .map(this::toDomain)
                .toList();
    }

    private Price toDomain(PriceJpaEntity entity) {
        // Mapping from Persistence Entity to Domain Model
        return new Price(
                entity.getPriceListId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurr()
        );
    }
}
