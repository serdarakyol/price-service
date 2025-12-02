package org.sakyol.priceservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {
    @Query("SELECT p FROM PriceJpaEntity p " +
            "WHERE p.productId = :productId " +
                "AND p.brandId = :brandId " +
                "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC " +
            "LIMIT 1")
    Optional<PriceJpaEntity> findApplicable(LocalDateTime applicationDate, long productId, long brandId);
}
