package com.progressSoft.clusteredDataWarehouse.repository;

import com.progressSoft.clusteredDataWarehouse.entity.ForexDeals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForexDealRepository extends JpaRepository<ForexDeals, Long> {
    Optional<ForexDeals> findForexDealByDealUniqueId(String uniqueDealId);
}
