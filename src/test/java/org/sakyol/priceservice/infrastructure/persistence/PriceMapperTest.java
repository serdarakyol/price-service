package org.sakyol.priceservice.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.sakyol.priceservice.domain.model.Price;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceMapperTest {

    private final PriceMapper mapper = new PriceMapper();

    @Test
    void toDomain_shouldMapEntityToDomain() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        PriceJpaEntity entity = new PriceJpaEntity();
        entity.setId(1L);
        entity.setPriceListId(1L);
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setStartDate(now);
        entity.setEndDate(now.plusDays(1));
        entity.setPriority(1);
        entity.setPrice(35.50);
        entity.setCurrency("EUR");

        // Act
        Price result = mapper.toDomain(entity);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.priceListId());
        assertEquals(35.50, result.price());
        assertEquals("EUR", result.currency());
    }

    @Test
    void toDomain_shouldReturnNull_whenInputIsNull() {
        assertNull(mapper.toDomain(null));
    }
}