package org.sakyol.priceservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "PRICES")
public class PriceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long priceListId;
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long productId;
    private Integer priority;
    private Double price;
    private String curr;
}
