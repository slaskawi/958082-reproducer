package org.infinispan.tutorial.simple.map;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

import java.util.stream.IntStream;

public class InfinispanMap {

   public static void main(String[] args) {

      String tempPath = System.getProperty("java.io.tmpdir");
      System.out.println("TEMP: " + tempPath);

      ConfigurationBuilder confBuilder = new ConfigurationBuilder();
      confBuilder.persistence().addSingleFileStore()
              .location(tempPath).fetchPersistentState(Boolean.TRUE)
              .preload(true).purgeOnStartup(false);

      // Construct a simple local cache manager with default configuration
      DefaultCacheManager cacheManager = new DefaultCacheManager(confBuilder.build());
      // Obtain the default cache
      Cache<String, String> cache = cacheManager.getCache();
      System.out.println("PRELOADED: " + cache.size());
      // Store a value
      IntStream.range(0, 10_000).forEach(i -> cache.put("key" + i, "value" + i));
      // Retrieve the value and print it out
      System.out.printf("key = %s\n", cache.get("key"));
      // Stop the cache manager and release all resources
      cacheManager.stop();
   }

}
