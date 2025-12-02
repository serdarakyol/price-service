package org.sakyol.priceservice.domain.port;

import org.sakyol.priceservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, long productId, long brandId);
}
