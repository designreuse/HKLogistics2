package com.hk.logistics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ShipmentServiceTypeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ShipmentServiceTypeSearchRepositoryMockConfiguration {

    @MockBean
    private ShipmentServiceTypeSearchRepository mockShipmentServiceTypeSearchRepository;

}
