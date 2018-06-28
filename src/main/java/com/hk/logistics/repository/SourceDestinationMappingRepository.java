package com.hk.logistics.repository;

import com.hk.logistics.domain.SourceDestinationMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SourceDestinationMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceDestinationMappingRepository extends JpaRepository<SourceDestinationMapping, Long>, JpaSpecificationExecutor<SourceDestinationMapping> {

    List<SourceDestinationMapping> findBySourcePincodeInAndDestinationPincode(List<String> sourcePincodes, String destinationPincode);

    SourceDestinationMapping findBySourcePincodeAndDestinationPincode(String sourcePincode,String destinationPincode);
}
