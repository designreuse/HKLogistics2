package com.hk.logistics.repository;

import com.hk.logistics.domain.RegionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionTypeRepository extends JpaRepository<RegionType, Long>, JpaSpecificationExecutor<RegionType> {

}
