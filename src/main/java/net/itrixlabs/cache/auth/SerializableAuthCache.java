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

import static org.springframework.security.core.SpringSecurityCoreVersion.SERIAL_VERSION_UID;

import net.itrixlabs.cache.ext.AbstractFileSystemCache;

/**
 * <p>
 * Efficient implementation of <code>ApplicationCache</code> for authorization sub-systems.
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 11<sup>th</sup>, 2015
 *
 */
public class SerializableAuthCache<K, V> extends AbstractFileSystemCache<K, V> {

    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Override
    public V getFromCache(K key) {
	return super.cache.get(key);
    }

    @Override
    public void putInCache(K key, V entry) {
	super.cache.putIfAbsent(key, entry);
    }

    @Override
    public void evictFromCache(K key) {
	super.cache.remove(key);
    }
}