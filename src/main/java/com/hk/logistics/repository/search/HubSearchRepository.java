package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Hub;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Hub entity.
 */
public interface HubSearchRepository extends ElasticsearchRepository<Hub, Long> {
}
