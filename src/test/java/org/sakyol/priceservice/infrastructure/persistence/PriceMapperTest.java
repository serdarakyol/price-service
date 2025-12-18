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

    @Test
    void toDomain_shouldHandleNullPriorityAndPrice_byDefaultingToZero() {
        // Arrange
        PriceJpaEntity entity = new PriceJpaEntity();
        // Set mandatory fields (to avoid NullPointerException on unboxing other fields)
        entity.setPriceListId(1L);
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusDays(1));
        entity.setCurrency("EUR");

        // Explicitly ensure priority and price are null
        entity.setPriority(null);
        entity.setPrice(null);

        // Act
        Price result = mapper.toDomain(entity);

        // Assert
        assertEquals(0, result.priority(), "Priority should default to 0 when null");
        assertEquals(0.0, result.price(), "Price should default to 0.0 when null");
    }
}