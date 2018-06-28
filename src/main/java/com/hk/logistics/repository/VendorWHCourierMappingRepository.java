package com.hk.logistics.repository;

import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VendorWHCourierMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorWHCourierMappingRepository extends JpaRepository<VendorWHCourierMapping, Long>, JpaSpecificationExecutor<VendorWHCourierMapping> {

}
