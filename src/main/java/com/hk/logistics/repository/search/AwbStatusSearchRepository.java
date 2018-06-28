package com.hk.logistics.repository.search;

import com.hk.logistics.domain.AwbStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AwbStatus entity.
 */
public interface AwbStatusSearchRepository extends ElasticsearchRepository<AwbStatus, Long> {
}
