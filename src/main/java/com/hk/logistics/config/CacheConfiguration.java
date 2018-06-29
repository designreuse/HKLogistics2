package com.hk.logistics.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.hk.logistics.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.hk.logistics.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.RegionType.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.CourierChannel.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.CourierChannel.class.getName() + ".vendorWHCourierMappings", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.CourierGroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.CourierGroup.class.getName() + ".couriers", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Courier.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Courier.class.getName() + ".courierChannels", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Pincode.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.CourierPricingEngine.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.ProductVariant.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Channel.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Channel.class.getName() + ".courierChannels", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.SourceDestinationMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.VendorWHCourierMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.AwbStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Awb.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.State.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Zone.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Hub.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Hub.class.getName() + ".pinCodes", jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.PincodeCourierMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.PincodeRegionZone.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.ShipmentServiceType.class.getName(), jcacheConfiguration);
            cm.createCache(com.hk.logistics.domain.Courier.class.getName() + ".vendorWHCourierMappings", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
