package org.sakyol.priceservice.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sakyol.priceservice.domain.model.Price;
import org.sakyol.priceservice.domain.port.PriceRepositoryPort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private GetPriceUseCase getPriceUseCase;

    @Test
    void execute_shouldReturnPrice_whenFound() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Price expectedPrice = new Price(1L, 1L, now, now.plusHours(1), 35455L, 1, 35.50, "EUR");

        when(priceRepositoryPort.findApplicablePrice(any(), eq(35455L), eq(1L)))
                .thenReturn(Optional.of(expectedPrice));

        // Act
        Optional<Price> result = getPriceUseCase.execute(now, 35455L, 1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(35.50, result.get().price());
        verify(priceRepositoryPort, times(1)).findApplicablePrice(any(), eq(35455L), eq(1L));
    }

    @Test
    void execute_shouldReturnEmpty_whenNotFound() {
        // Arrange
        when(priceRepositoryPort.findApplicablePrice(any(), anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        // Act
        Optional<Price> result = getPriceUseCase.execute(LocalDateTime.now(), 35455L, 1L);

        // Assert
        assertTrue(result.isEmpty());
    }

}