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
package net.itrixlabs.cache.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * Primordial interface for maintaining application cache. In contrast to other cache providers,
 * this type of cache ensures type checking and specifies efficient caching for a distributed
 * systems. However, implementations can be focused upon non-distributed systems too.
 * </p>
 * <p>
 * Implementations should provide appropriate methods to set their cache parameters (e.g.
 * time-to-live) and/or force removal of entities before their normal expiration. These are not part
 * of the AuthorizationCache interface contract because they vary depending on the type of caching
 * system used (in-memory, disk, cluster, hybrid etc.).
 * </p>
 * <p>
 * Caching is generally only required in applications which do not maintain server-side state, such
 * as remote clients or web services. The authentication credentials are then presented on each
 * invocation and the overhead of accessing a database or other persistent storage mechanism to
 * validate would be excessive. In this case, you would configure a cache to store the UserDetails
 * information rather than loading it each time.
 * </p>
 * <p>
 * <b><i>Note that the interface is declared</i></b> <code>abstract</code> <b><i>which doesn't make
 * any sense.</i></b> But, it's just to notify the programmer that this type is not an
 * initialization/autowiring candidate. Interfaces/Classes extending/implementing this contract must
 * be initialized so as to support multiple types and instances of cache per container (as suited).
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 10<sup>th</sup>, 2015
 *
 */
public abstract interface ApplicationCache<Key, V> extends InitializingBean, DisposableBean {

    /**
     * <p>
     * Initializes the application cache (doesn't create it). Can make use of a data-store for
     * pre-fetching already stored application data (if required) typically upon container restart.
     * </p>
     * <p>
     * Each type of application cache is initialized separately in order to maintain sanity and
     * usage simplicity. Thus, please be advised that a cache type must be instantiated &amp;
     * initialized manually before use (manually just means that extra code is required for the
     * same).
     * </p>
     */
    void initialize();

    /**
     * <p>
     * Obtains a stored entry from the cache. Implementations must make sure not to throw an
     * exception even if one occurs here.
     * </p>
     * <p>
     * <b><i>Important:</i></b>&nbsp;<i>Note that you may use any well known java type as key like
     * {@link java.lang.Long} or {@link java.lang.String} or a custom type as long as it abides by
     * the contract defined by the {@link net.itrixlabs.cache.config.Key} interface, i.e., the key
     * should be uniquely identifiable using its value and not the <code>Key</code> instance as
     * whole. In short, you must override the {@link Object#equals(Object)} and
     * {@link Object#hashCode()} methods of that particular key type to make sure your identifier
     * can be uniquely distinguished from with-in the cache.</i>
     * </p>
     * 
     * @param key
     *            the unique identifier to use for fetching the entry; be advised that the same
     *            identifier must be used for saving the details into the cache
     * @return a populated entry object containing enough information to facilitate the function it
     *         is required for; if none is found
     */
    V getFromCache(Object key);

    /**
     * <p>
     * Places an entry in the cache. The <code>key</code> is the identifier used to subsequently
     * retrieve the entry.
     * </p>
     * <p>
     * <b><i>Important:</i></b>&nbsp;<i>Note that you may use any well known java type as key like
     * {@link java.lang.Long} or {@link java.lang.String} or a custom type as long as it abides by
     * the contract defined by the {@link net.itrixlabs.cache.config.Key} interface, i.e., the key
     * should be uniquely identifiable using its value and not the <code>Key</code> instance as
     * whole. In short, you must override the {@link Object#equals(Object)} and
     * {@link Object#hashCode()} methods of that particular key type to make sure your identifier
     * can be uniquely distinguished from with-in the cache.</i>
     * </p>
     * 
     * @param key
     *            the key corresponding to which entry must be stored in the cache
     * @param entry
     *            the object entry to place into the cache
     */
    void putInCache(Object key, V entry);

    /**
     * <p>
     * Removes the specified entry from the cache. The <code>key</code> is the identifier used to
     * remove the entry. If the entry is not found, the method should simply return (not throw an
     * exception).
     * </p>
     * <p>
     * Some cache implementations may not support eviction from the cache, in which case they should
     * provide appropriate behavior to alter the token in either its documentation, via an
     * exception, or through a log message.
     * </p>
     * <p>
     * <b><i>Important:</i></b>&nbsp;<i>Note that you may use any well known java type as key like
     * {@link java.lang.Long} or {@link java.lang.String} or a custom type as long as it abides by
     * the contract defined by the {@link net.itrixlabs.cache.config.Key} interface, i.e., the key
     * should be uniquely identifiable using its value and not the <code>Key</code> instance as
     * whole. In short, you must override the {@link Object#equals(Object)} and
     * {@link Object#hashCode()} methods of that particular key type to make sure your identifier
     * can be uniquely distinguished from with-in the cache.</i>
     * </p>
     *
     * @param key
     *            the predefined key/identifier for evicting an entry from the cache
     */
    void evictFromCache(Object key);

    /**
     * <p>
     * Flushes the application cache (doesn't destroy it). Note that flushing an application cache
     * doesn't mean it is destroyed. It may just be reduced in size or cleared in order to re-claim
     * memory.
     * </p>
     * <p>
     * Each type of application cache is flushed separately in order to maintain sanity and usage
     * simplicity. Thus, please be advised that a cache type must be flushed &amp; manually whenever
     * required (manually just means that extra code is required for the same).
     * </p>
     */

    /**
     * <p>
     * Checks the application cache on which the method is called for the specified key and returns
     * true if the condition is true, else false.
     * </p>
     * <p>
     * <b><i>Important:</i></b>&nbsp;<i>Note that you may use any well known java type as key like
     * {@link java.lang.Long} or {@link java.lang.String} or a custom type as long as it abides by
     * the contract defined by the {@link net.itrixlabs.cache.config.Key} interface, i.e., the key
     * should be uniquely identifiable using its value and not the <code>Key</code> instance as
     * whole. In short, you must override the {@link Object#equals(Object)} and
     * {@link Object#hashCode()} methods of that particular key type to make sure your identifier
     * can be uniquely distinguished from with-in the cache.</i>
     * </p>
     * 
     * @param key
     *            the key to be tested for presence in the cache
     * @return true if the key exists, else false (an exception must never be thrown)
     */
    boolean isPresentInCache(Object key);

    void flush();
}