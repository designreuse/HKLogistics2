package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Courier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Courier entity.
 */
public interface CourierSearchRepository extends ElasticsearchRepository<Courier, Long> {
}
