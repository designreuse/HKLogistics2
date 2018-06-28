package com.hk.logistics.repository.search;

import com.hk.logistics.domain.CourierPricingEngine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CourierPricingEngine entity.
 */
public interface CourierPricingEngineSearchRepository extends ElasticsearchRepository<CourierPricingEngine, Long> {
}
