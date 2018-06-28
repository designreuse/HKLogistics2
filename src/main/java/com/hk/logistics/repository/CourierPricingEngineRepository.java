package com.hk.logistics.repository;

import com.hk.logistics.domain.CourierPricingEngine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourierPricingEngine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierPricingEngineRepository extends JpaRepository<CourierPricingEngine, Long>, JpaSpecificationExecutor<CourierPricingEngine> {

}
