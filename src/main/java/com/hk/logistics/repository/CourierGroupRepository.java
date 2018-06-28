package com.hk.logistics.repository;

import com.hk.logistics.domain.CourierGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourierGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierGroupRepository extends JpaRepository<CourierGroup, Long>, JpaSpecificationExecutor<CourierGroup> {

}
