package com.hk.logistics.repository;

import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.AwbStatus;
import com.hk.logistics.domain.Channel;
import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Awb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AwbRepository extends JpaRepository<Awb, Long>, JpaSpecificationExecutor<Awb> {

    List<Awb> findByVendorWHCourierMappingAndCodAndAwbStatusAndChannel(VendorWHCourierMapping vendorWHCourierMapping, Boolean cod, AwbStatus awbStatus, Channel channel);
    Awb findByVendorWHCourierMappingAndAwbNumber(VendorWHCourierMapping vendorWHCourierMapping,String awbNumber);
    Awb findByVendorWHCourierMappingAndAwbNumberAndCod(VendorWHCourierMapping vendorWHCourierMapping,String awbNumber,Boolean isCod);
    Awb findByAwbNumber(String awbNumber);
    Awb findByChannelAndAwbNumber(Channel channel, String awbNumber);
    Awb findByVendorWHCourierMappingAndAwbNumberAndCodAndChannel(VendorWHCourierMapping vendorWHCourierMapping,String awbNumber,Boolean isCod,Channel channel);

}
