package org.sakyol.priceservice.infrastructure.persistence;

import lombok.AllArgsConstructor;

import org.sakyol.priceservice.domain.model.Price;
import org.sakyol.priceservice.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, long productId, long brandId) {
        return priceJpaRepository.findApplicable(applicationDate, productId, brandId).map(priceMapper::toDomain);
    }
}
