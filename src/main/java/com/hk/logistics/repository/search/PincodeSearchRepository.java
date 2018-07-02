package com.hk.logistics.repository.search;

import com.hk.logistics.domain.Pincode;
import com.hk.logistics.service.dto.PincodeDTO;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pincode entity.
 */
public interface PincodeSearchRepository extends ElasticsearchRepository<Pincode, Long> {

	List<Pincode> findByPincode(String pincode);
}
