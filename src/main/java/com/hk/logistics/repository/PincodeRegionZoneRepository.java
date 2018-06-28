package com.hk.logistics.repository;

import com.hk.logistics.domain.PincodeRegionZone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PincodeRegionZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeRegionZoneRepository extends JpaRepository<PincodeRegionZone, Long>, JpaSpecificationExecutor<PincodeRegionZone> {

}
