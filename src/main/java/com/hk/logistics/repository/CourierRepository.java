package com.hk.logistics.repository;

import com.hk.logistics.domain.Courier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Courier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourierRepository extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

    Courier findByShortCode(String name);

    List<Courier> findAllByActive(Boolean active);

}
