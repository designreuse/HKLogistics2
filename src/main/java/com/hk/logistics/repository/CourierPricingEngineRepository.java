package com.hk.logistics.repository;

import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.CourierPricingEngine;
import com.hk.logistics.domain.RegionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;


/**
 * Spring Data  repository for the CourierPricingEngine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierPricingEngineRepository extends JpaRepository<CourierPricingEngine, Long>, JpaSpecificationExecutor<CourierPricingEngine> {

    CourierPricingEngine findByCourierAndRegionTypeAndValidUpto(Courier courier, RegionType regionType, LocalDate shipmentDate);

}
