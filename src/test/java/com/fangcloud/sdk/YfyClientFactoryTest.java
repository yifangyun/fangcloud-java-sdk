package com.fangcloud.sdk;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class YfyClientFactoryTest {
    @Test
    public void lruCacheTest() {
        YfyClientFactory<Integer> clientFactory = new YfyClientFactory<Integer>(10, new YfyRequestConfig());
        Map<Integer, YfyClient<Integer>> lruCache = clientFactory.getLruCache();
        for (int i = 1; i < 20; i++) {
            clientFactory.getClient(i, "access-token", "refresh-token");
            if (i > 10) {
                assertEquals(null, clientFactory.getClient(i - 10));
                assertEquals(10, lruCache.size());
            } else {
                assertEquals(i, lruCache.size());
            }
        }
    }
}
