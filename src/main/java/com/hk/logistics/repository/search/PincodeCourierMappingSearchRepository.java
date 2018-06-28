package com.hk.logistics.repository.search;

import com.hk.logistics.domain.PincodeCourierMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PincodeCourierMapping entity.
 */
public interface PincodeCourierMappingSearchRepository extends ElasticsearchRepository<PincodeCourierMapping, Long> {
}
