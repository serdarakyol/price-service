package org.sakyol.priceservice.domain.port;

import org.sakyol.priceservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(LocalDateTime applicationDate, long productId, long brandId);
}
