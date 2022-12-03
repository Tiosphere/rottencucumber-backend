package tk.rottencucumber.backend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(
                new ConcurrentMapCache("genres"),
                new ConcurrentMapCache("language"),
                new ConcurrentMapCache("platform"),
                new ConcurrentMapCache("actors"),
                new ConcurrentMapCache("writers"),
                new ConcurrentMapCache("directors"),
                new ConcurrentMapCache("movies")
        ));
        return manager;
    }
}
