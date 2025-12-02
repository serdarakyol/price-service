package org.sakyol.priceservice.application.usecase;

import lombok.AllArgsConstructor;
import org.sakyol.priceservice.domain.model.Price;
import org.sakyol.priceservice.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPriceUseCase {
    private final PriceRepositoryPort priceRepository;

    public Optional<Price> execute(LocalDateTime applicationDate, long productId, long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId);
    }

}
