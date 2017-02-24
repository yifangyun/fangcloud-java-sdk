package com.fangcloud.sdk;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Always used by server, which has multiply users at the same time
 *
 * @param <T> The identify to verify user in your app
 */
public class YfyClientFactory<T> {
    private YfyRequestConfig requestConfig;
    private YfyRefreshListener<T> refreshListener;
    private Map<T, YfyClient<T>> lruCache;

    public YfyClientFactory(int maxCapacity, YfyRequestConfig requestConfig, YfyRefreshListener<T> refreshListener) {
        if (requestConfig == null) {
            throw new NullPointerException("request config");
        }
        this.lruCache = new LRULinkedHashMap<>(maxCapacity);
        this.requestConfig = requestConfig;
        this.refreshListener = refreshListener;
    }

    public YfyClientFactory(int maxCapacity, YfyRequestConfig requestConfig) {
        this(maxCapacity, requestConfig, null);
    }

    public Map<T, YfyClient<T>> getLruCache() {
        return lruCache;
    }

    public YfyClient getClient(T key) {
        return lruCache.get(key);
    }

    public YfyClient getClient(T key, String accessToken, String refreshToken) {
        YfyClient<T> client = lruCache.get(key);
        if (client == null) {
            synchronized (YfyClientFactory.class) {
                client = lruCache.get(key);
                if (client == null) {
                    client = new YfyClient<T>(key, requestConfig, accessToken, refreshToken, refreshListener);
                    lruCache.put(key, client);
                }
            }
        }
        return client;
    }

    /**
     * This is a thread-safe LRU cache
     * @param <K> Map key
     * @param <V> Map value
     */
    private class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;
        private final int maxCapacity;
        private ReadWriteLock lock;

        public LRULinkedHashMap(int maxCapacity) {
            super((int) Math.ceil(maxCapacity / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_LOAD_FACTOR, true);
            this.maxCapacity = maxCapacity;
            this.lock = new ReentrantReadWriteLock();
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxCapacity;
        }

        @Override
        public V get(Object key) {
            lock.readLock().lock();
            try {
                return super.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public V put(K key, V value) {
            lock.writeLock().lock();
            try {
                return super.put(key, value);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }


}
