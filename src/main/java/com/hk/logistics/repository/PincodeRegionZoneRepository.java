package com.hk.logistics.repository;

import com.hk.logistics.domain.CourierGroup;
import com.hk.logistics.domain.PincodeRegionZone;
import com.hk.logistics.domain.SourceDestinationMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the PincodeRegionZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeRegionZoneRepository extends JpaRepository<PincodeRegionZone, Long>, JpaSpecificationExecutor<PincodeRegionZone> {

    List<PincodeRegionZone> findBySourceDestinationMappingAndCourierGroupIn(SourceDestinationMapping sourceDestinationMapping, Set<CourierGroup> courierGroups);

    PincodeRegionZone findBySourceDestinationMappingAndCourierGroup(SourceDestinationMapping sourceDestinationMapping,CourierGroup courierGroup);

}
