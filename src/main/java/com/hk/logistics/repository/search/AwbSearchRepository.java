package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Awb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Awb entity.
 */
public interface AwbSearchRepository extends ElasticsearchRepository<Awb, Long> {
}
