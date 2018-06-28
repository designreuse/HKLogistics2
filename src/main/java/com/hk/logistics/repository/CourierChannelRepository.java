package com.hk.logistics.repository;

import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.Courier;
import com.hk.logistics.domain.CourierChannel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CourierChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierChannelRepository extends JpaRepository<CourierChannel, Long>, JpaSpecificationExecutor<CourierChannel> {

    List<CourierChannel> findByChannel(Channel channel);
    CourierChannel findByCourierAndChannel(Courier courier, Channel channel);

}
