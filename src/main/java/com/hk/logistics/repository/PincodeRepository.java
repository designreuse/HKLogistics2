package com.hk.logistics.repository;

import com.hk.logistics.domain.Pincode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pincode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long>, JpaSpecificationExecutor<Pincode> {

    Pincode findByPincode(String pincode);

}
