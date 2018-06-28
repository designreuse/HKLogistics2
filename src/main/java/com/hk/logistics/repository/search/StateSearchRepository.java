package com.hk.logistics.repository.search;

import com.hk.logistics.domain.State;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the State entity.
 */
public interface StateSearchRepository extends ElasticsearchRepository<State, Long> {
}
