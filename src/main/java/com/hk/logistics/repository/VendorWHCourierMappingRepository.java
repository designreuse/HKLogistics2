package com.hk.logistics.repository;

import com.hk.logistics.domain.CourierChannel;
import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the VendorWHCourierMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorWHCourierMappingRepository extends JpaRepository<VendorWHCourierMapping, Long>, JpaSpecificationExecutor<VendorWHCourierMapping> {

    List<VendorWHCourierMapping> findByVendorAndCourierChannelInAndActive(String vendor, List<CourierChannel> courierChannel, Boolean active);
    VendorWHCourierMapping findByVendorAndCourierChannelAndActive(String vendor,CourierChannel courierChannel,Boolean active);
    List<VendorWHCourierMapping> findByWarehouseInAndCourierChannelInAndActive(List<Long> warehouse,List<CourierChannel> courierChannel, boolean active);
    List<VendorWHCourierMapping> findByWarehouseAndCourierChannelInAndActive(Long warehouse,List<CourierChannel> courierChannel, boolean active);
    VendorWHCourierMapping findByWarehouseAndCourierChannelAndActive(Long warehouse,CourierChannel courierChannel, boolean active);

    VendorWHCourierMapping findByVendorAndWarehouseAndCourierChannelAndActive(String vendor,Long warehouse,CourierChannel courierChannel,Boolean active);

}
