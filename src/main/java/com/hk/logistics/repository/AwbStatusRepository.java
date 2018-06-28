package com.hk.logistics.repository;

import com.hk.logistics.domain.AwbStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AwbStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AwbStatusRepository extends JpaRepository<AwbStatus, Long>, JpaSpecificationExecutor<AwbStatus> {

}
