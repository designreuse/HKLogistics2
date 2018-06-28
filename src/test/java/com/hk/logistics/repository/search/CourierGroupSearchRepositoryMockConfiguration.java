package com.hk.logistics.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CourierGroupSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CourierGroupSearchRepositoryMockConfiguration {

    @MockBean
    private CourierGroupSearchRepository mockCourierGroupSearchRepository;

}
