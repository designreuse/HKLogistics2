package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Channel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Channel entity.
 */
public interface ChannelSearchRepository extends ElasticsearchRepository<Channel, Long> {
}
