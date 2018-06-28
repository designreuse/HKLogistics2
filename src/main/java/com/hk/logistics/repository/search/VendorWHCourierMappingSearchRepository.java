package com.hk.logistics.repository.search;

import com.hk.logistics.domain.VendorWHCourierMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VendorWHCourierMapping entity.
 */
public interface VendorWHCourierMappingSearchRepository extends ElasticsearchRepository<VendorWHCourierMapping, Long> {
}
