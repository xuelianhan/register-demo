package com.github.register.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Provide some caches used on this application
 *
 * @author sniper
 * @date Fri Sep 15, 2023
 **/
@Configuration
public class CacheConfiguration {

    /**
     * System default cache TTL time: 4 minutes.
     * Some data need to plan the expiration time according to this function.
     */
    public static final long SYSTEM_DEFAULT_EXPIRES = 4 * 60 * 1000;

    @Bean
    public CacheManager configCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(SYSTEM_DEFAULT_EXPIRES, TimeUnit.MILLISECONDS));
        return manager;
    }

    @Bean(name = "settlement")
    public Cache getSettlementTTLCache() {
        return new CaffeineCache("settlement", Caffeine.newBuilder().expireAfterAccess(SYSTEM_DEFAULT_EXPIRES, TimeUnit.MILLISECONDS).build());
    }

}
