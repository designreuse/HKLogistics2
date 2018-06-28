package com.hk.logistics.repository;

import com.hk.logistics.domain.PincodeCourierMapping;
import com.hk.logistics.domain.SourceDestinationMapping;
import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the PincodeCourierMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeCourierMappingRepository extends JpaRepository<PincodeCourierMapping, Long>, JpaSpecificationExecutor<PincodeCourierMapping> {


    List<PincodeCourierMapping> findBySourceDestinationMappingInAndVendorWHCourierMappingIn(List<SourceDestinationMapping> sourceDestinationMapping,
                                                                                            List<VendorWHCourierMapping> vendorWHCourierMapping);

    List<PincodeCourierMapping> findBySourceDestinationMappingAndVendorWHCourierMappingIn(List<SourceDestinationMapping> sourceDestinationMapping,
                                                                                          List<VendorWHCourierMapping> vendorWHCourierMapping);
}
