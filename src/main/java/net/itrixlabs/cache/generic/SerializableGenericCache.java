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
package net.itrixlabs.cache.generic;

import static net.itrixlabs.cache.config.CacheType.GENERIC;
import static org.springframework.security.core.SpringSecurityCoreVersion.SERIAL_VERSION_UID;

import net.itrixlabs.cache.core.AbstractFileSystemCache;

/**
 * <p>
 * Efficient implementation of <code>ApplicationCache</code> for
 * non-differential subsystems which are unknown to this API (better support for
 * runtime resolution will be provided in future when out team gets the chance
 * to look into this).
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 11<sup>th</sup>, 2015
 *
 */
public class SerializableGenericCache<V> extends AbstractFileSystemCache<V> {

	private static final long serialVersionUID = SERIAL_VERSION_UID;

	/**
	 * <p>
	 * Constructs a <code>SerializableGenericCache</code>. Sensible defaults
	 * will be used for required parameters unless explicitly set. An
	 * application can typically have several instances of generic cache to
	 * store different types of non-differential details.
	 * </p>
	 * 
	 */
	public SerializableGenericCache() {
		super(GENERIC);
	}

	/**
	 * <p>
	 * Constructs a <code>SerializableGenericCache</code> with the given type of
	 * referenced details, the cache storage directory and cache file name.
	 * Provides better control on caching strategy out of the box. An
	 * application can typically have several instances of generic cache to
	 * store different types of non-differential details.
	 * </p>
	 * 
	 * @param cacheDir
	 *            the cache directory location to use
	 * @param cacheFile
	 *            the cache file name
	 */
	public SerializableGenericCache(String cacheDir, String cacheFile) {
		super(GENERIC, cacheDir, cacheFile);
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