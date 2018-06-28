package com.hk.logistics.repository.search;

import com.hk.logistics.domain.CourierChannel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CourierChannel entity.
 */
public interface CourierChannelSearchRepository extends ElasticsearchRepository<CourierChannel, Long> {
}
