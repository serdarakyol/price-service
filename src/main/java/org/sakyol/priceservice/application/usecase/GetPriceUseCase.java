package org.sakyol.priceservice.application.usecase;

import lombok.AllArgsConstructor;
import org.sakyol.priceservice.application.dto.PriceResponse;
import org.sakyol.priceservice.domain.model.Price;
import org.sakyol.priceservice.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPriceUseCase {
    private final PriceRepositoryPort priceRepository;

    public Optional<PriceResponse> execute(LocalDateTime applicationDate, long productId, long brandId) {

        // 1. Efficient data extraction: The repository query only returns records applicable by date/product/brand.
        List<Price> applicablePrices = priceRepository.findApplicablePrices(applicationDate, productId, brandId);

        // 2. Apply business rule (Priority): Select the one with the maximum priority.
        return applicablePrices.stream()
                .max(Comparator.comparingInt(Price::priority))
                .map(this::toResponse); // 3. Devolver único resultado
    }

    private PriceResponse toResponse(Price price) {
        // Mapping from Domain Entity to Application DTO (Implements the Liskov Substitution Principle if Price/PriceResponse were in a hierarchy, or Dependency Inversion by isolating mapping)
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
