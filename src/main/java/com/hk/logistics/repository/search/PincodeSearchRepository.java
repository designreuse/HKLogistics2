package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Pincode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pincode entity.
 */
public interface PincodeSearchRepository extends ElasticsearchRepository<Pincode, Long> {
}
