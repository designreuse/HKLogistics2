package com.hk.logistics.repository.search;

import com.hk.logistics.domain.RegionType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegionType entity.
 */
public interface RegionTypeSearchRepository extends ElasticsearchRepository<RegionType, Long> {
}
