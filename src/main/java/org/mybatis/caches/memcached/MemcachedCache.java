/*
 *    Copyright 2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.memcached;

import org.apache.ibatis.cache.Cache;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * The Memcached-based Cache implementation.
 *
 * @author Simone Tripodi
 */
public final class MemcachedCache implements Cache {

    private static MemcachedClientWrapper MEMCACHED_CLIENT = new MemcachedClientWrapper();

    /**
     * The {@link ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new DummyReadWriteLock();

    /**
     * The cache id.
     */
    private final String id;

    /**
     * Builds a new Memcached-based Cache.
     *
     * @param id the Mapper id.
     */
    public MemcachedCache(final String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        getClient().removeGroup(this.id);
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    public Object getObject(Object key) {
        return getClient().getObject(key);
    }

    /**
     * {@inheritDoc}
     */
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * {@inheritDoc}
     */
    public int getSize() {
        return Integer.MAX_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    public void putObject(Object key, Object value) {
        getClient().putObject(key, value, this.id);
    }

    /**
     * {@inheritDoc}
     */
    public Object removeObject(Object key) {
        return getClient().removeObject(key);
    }

    private static MemcachedClientWrapper getClient() {
        if (MEMCACHED_CLIENT == null) {
            MEMCACHED_CLIENT = new MemcachedClientWrapper();
        }
        return MEMCACHED_CLIENT;
    }

    public static void setClient(MemcachedClientWrapper wrapper) {
        MEMCACHED_CLIENT = wrapper;
    }

}
