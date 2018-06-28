package com.hk.logistics.repository;

import com.hk.logistics.domain.CourierChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourierChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierChannelRepository extends JpaRepository<CourierChannel, Long>, JpaSpecificationExecutor<CourierChannel> {

}
