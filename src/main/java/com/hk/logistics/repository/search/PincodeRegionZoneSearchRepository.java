package com.hk.logistics.repository.search;

import com.hk.logistics.domain.PincodeRegionZone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PincodeRegionZone entity.
 */
public interface PincodeRegionZoneSearchRepository extends ElasticsearchRepository<PincodeRegionZone, Long> {
}
