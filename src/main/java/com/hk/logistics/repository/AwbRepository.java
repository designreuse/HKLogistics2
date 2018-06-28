package com.hk.logistics.repository;

import com.hk.logistics.domain.Awb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Awb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AwbRepository extends JpaRepository<Awb, Long>, JpaSpecificationExecutor<Awb> {

}
