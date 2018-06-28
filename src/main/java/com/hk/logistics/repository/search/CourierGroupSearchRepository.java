package com.hk.logistics.repository.search;

import com.hk.logistics.domain.CourierGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CourierGroup entity.
 */
public interface CourierGroupSearchRepository extends ElasticsearchRepository<CourierGroup, Long> {
}
