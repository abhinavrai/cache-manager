/*
 * Copyright (c) 2014-2015. Author or original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.itrixlabs.cache.auth;

import static net.itrixlabs.cache.config.CacheType.AUTH;
import static org.springframework.security.core.SpringSecurityCoreVersion.SERIAL_VERSION_UID;

import net.itrixlabs.cache.core.AbstractFileSystemCache;

/**
 * <p>
 * Simple implementation of <code>ApplicationCache</code> for authorization sub-systems.
 * </p>
 * <p>
 * Authorization providers can simply invoke an instance of this class in order to utilize its
 * caching capabilities. Authorization specific customizations can be added in future. Anyways, the
 * API is closed for modification (to prevent harm you might cause to your application) but open for
 * extension.
 * </p>
 * 
 * @author Abhinav Rai
 * @see AbstractFileSystemCache
 * @since November 11<sup>th</sup>, 2015
 *
 */
public class SerializableAuthCache<V> extends AbstractFileSystemCache<V> {

    private static final long serialVersionUID = SERIAL_VERSION_UID;

    /**
     * <p>
     * Constructs a <code>SerializableAuthCache</code>. Sensible defaults will be used for required
     * parameters unless explicitly set. An application can typically have several instances of auth
     * cache to store different types of auth tokens (for example, a scenario with multiple
     * authentication/authorization providers using different user details services).
     * </p>
     */
    public SerializableAuthCache() {
	super(AUTH);
    }

    /**
     * <p>
     * Constructs a <code>SerializableAuthCache</code> with the given type of auth token, the cache
     * storage directory and cache file name. Provides better control on caching strategy out of the
     * box. An application can typically have several instances of auth cache to store different
     * types of auth tokens (for example, a scenario with multiple authentication/authorization
     * providers using different user details services).
     * </p>
     * 
     * @param cacheDir
     *            the cache directory location to use
     * @param cacheFile
     *            the cache file name
     */
    public SerializableAuthCache(String cacheDir, String cacheFile) {
	super(AUTH, cacheDir, cacheFile);
    }

    @Override
    public V getFromCache(Object key) {
	return super.cache.get(super.generate(key));
    }

    @Override
    public void putInCache(Object key, V entry) {
	super.cache.putIfAbsent(super.generate(key), entry);
    }

    @Override
    public void evictFromCache(Object key) {
	super.cache.remove(super.generate(key));
    }
}