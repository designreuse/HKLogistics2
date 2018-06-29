package com.hk.logistics.repository;

import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the VendorWHCourierMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorWHCourierMappingRepository extends JpaRepository<VendorWHCourierMapping, Long>, JpaSpecificationExecutor<VendorWHCourierMapping> {

    List<VendorWHCourierMapping> findByVendorAndCourierInAndActive(String vendor, List<Courier> couriers,Boolean active);
    VendorWHCourierMapping findByVendorAndCourierAndActive(String vendor,Courier courier,Boolean active);
    List<VendorWHCourierMapping> findByWarehouseInAndCourierInAndActive(List<Long> warehouse,List<Courier> couriers, boolean active);
    List<VendorWHCourierMapping> findByWarehouseAndCourierInAndActive(Long warehouse,List<Courier> couriers, boolean active);
    VendorWHCourierMapping findByWarehouseAndCourierAndActive(Long warehouse,Courier couriers, boolean active);

    VendorWHCourierMapping findByVendorAndWarehouseAndCourierAndActive(String vendor,Long warehouse,Courier couriers,Boolean active);

}
