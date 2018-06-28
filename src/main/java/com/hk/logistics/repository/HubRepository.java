package com.hk.logistics.repository;

import com.hk.logistics.domain.Hub;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HubRepository extends JpaRepository<Hub, Long>, JpaSpecificationExecutor<Hub> {

}
