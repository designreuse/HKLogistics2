package com.hk.logistics.repository.search;

import com.hk.logistics.domain.ShipmentServiceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShipmentServiceType entity.
 */
public interface ShipmentServiceTypeSearchRepository extends ElasticsearchRepository<ShipmentServiceType, Long> {
}
