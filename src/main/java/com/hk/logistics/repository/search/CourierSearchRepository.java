package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Courier;
import com.hk.logistics.service.dto.CourierDTO;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Courier entity.
 */
public interface CourierSearchRepository extends ElasticsearchRepository<Courier, Long> {

	Page<Courier> findByName(String queryStringQuery, Pageable pageable);
}
