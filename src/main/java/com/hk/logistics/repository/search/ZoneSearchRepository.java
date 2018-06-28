package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Zone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Zone entity.
 */
public interface ZoneSearchRepository extends ElasticsearchRepository<Zone, Long> {
}
