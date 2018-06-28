package com.hk.logistics.repository;

import com.hk.logistics.domain.PincodeCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PincodeCourierMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeCourierMappingRepository extends JpaRepository<PincodeCourierMapping, Long>, JpaSpecificationExecutor<PincodeCourierMapping> {

}
