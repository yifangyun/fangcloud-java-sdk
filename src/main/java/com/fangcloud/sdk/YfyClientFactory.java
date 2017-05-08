package com.fangcloud.sdk;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Always used by server, which has multiply users at the same time
 *
 * @param <T> User identify type in your account system
 */
public class YfyClientFactory<T> {
    private YfyRequestConfig requestConfig;
    private YfyRefreshListener<T> refreshListener;
    private volatile Map<T, YfyClient<T>> lruCache;

    /**
     * Get a YfyClientFactory to build YfyClient with the same arguments
     *
     * @param maxCapacity Max number of LRU cache to cache user client
     * @param requestConfig Request config type
     * @param refreshListener Personal refresh listener
     */
    public YfyClientFactory(int maxCapacity, YfyRequestConfig requestConfig, YfyRefreshListener<T> refreshListener) {
        if (requestConfig == null) {
            throw new NullPointerException("request config");
        }
        this.lruCache = new LRULinkedHashMap<T, YfyClient<T>>(maxCapacity);
        this.requestConfig = requestConfig;
        this.refreshListener = refreshListener;
    }

    /**
     * Get a YfyClientFactory to build YfyClient with the same arguments
     *
     * @param maxCapacity Max number of LRU cache to cache user client
     * @param requestConfig Request config type
     */
    public YfyClientFactory(int maxCapacity, YfyRequestConfig requestConfig) {
        this(maxCapacity, requestConfig, null);
    }

    public Map<T, YfyClient<T>> getLruCache() {
        return lruCache;
    }

    /**
     * Get YfyClient by user identify in LRU cache. If LRU doesn't contain, return null
     *
     * @param key User identify in your account system
     * @return YfyClient
     */
    public YfyClient getClient(T key) {
        return lruCache.get(key);
    }

    /**
     * Get YfyClient by user identify in LRU cache. If LRU doesn't contain, build a new YfyClient
     *
     * @param key User identify in your account system
     * @param accessToken User's access token
     * @param refreshToken User's refresh token
     * @return YfyClient
     */
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
