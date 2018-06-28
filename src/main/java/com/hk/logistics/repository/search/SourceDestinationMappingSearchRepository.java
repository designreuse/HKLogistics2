package com.hk.logistics.repository.search;

import com.hk.logistics.domain.SourceDestinationMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SourceDestinationMapping entity.
 */
public interface SourceDestinationMappingSearchRepository extends ElasticsearchRepository<SourceDestinationMapping, Long> {
}
