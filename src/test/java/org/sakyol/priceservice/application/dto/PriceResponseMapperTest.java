package org.sakyol.priceservice.application.dto;

import org.junit.jupiter.api.Test;
import org.sakyol.priceservice.domain.model.Price;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceResponseMapperTest {

    private final PriceResponseMapper mapper = new PriceResponseMapper();

    @Test
    void toResponse_shouldMapDomainToResponse() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Price price = new Price(1L, 1L, now, now.plusHours(5), 35455L, 0, 10.0, "EUR");

        // Act
        PriceResponse response = mapper.toResponse(price);

        // Assert
        assertNotNull(response);
        assertEquals(35455L, response.productId());
        assertEquals(10.0, response.finalPrice());
    }

    @Test
    void toResponse_shouldReturnNull_whenInputIsNull() {
        assertNull(mapper.toResponse(null));
    }
}