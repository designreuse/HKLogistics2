package com.hk.logistics.repository.search;

import com.hk.logistics.domain.ProductVariant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductVariant entity.
 */
public interface ProductVariantSearchRepository extends ElasticsearchRepository<ProductVariant, Long> {
}
