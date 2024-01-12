package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.model.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, Long> {
    Optional<FxDeal> findByDealUniqueId(String uniqueDealId);
}
