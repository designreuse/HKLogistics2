package com.hk.logistics.repository;

import com.hk.logistics.domain.ShipmentServiceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShipmentServiceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentServiceTypeRepository extends JpaRepository<ShipmentServiceType, Long>, JpaSpecificationExecutor<ShipmentServiceType> {

}
