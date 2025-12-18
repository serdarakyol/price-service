package org.sakyol.priceservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {
    @Query(value = "SELECT * FROM PRICES " +
            "WHERE product_id = :productId " +
                "AND brand_id = :brandId " +
                "AND :applicationDate BETWEEN start_date AND end_date " +
            "ORDER BY priority DESC, price DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<PriceJpaEntity> findApplicable(LocalDateTime applicationDate, long productId, long brandId);
}
