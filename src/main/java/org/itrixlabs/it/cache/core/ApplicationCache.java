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
package org.itrixlabs.it.cache.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * Primordial interface for maintaining application cache. In contrast to other cache providers,
 * this type of cache ensures type checking and provides efficient caching for a distributed system.
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
 * @since October 16<sup>th</sup>, 2015
 *
 */
public abstract interface ApplicationCache extends InitializingBean, DisposableBean {

    /**
     * <p>
     * Initializes the application cache. Can make use of a data-store for pre-fetching already
     * stored application data (if required) typically upon container restart.
     * </p>
     */
    void initialize();

    /**
     * <p>
     * Flushes the application cache. Note that flushing an application cache doesn't mean it is
     * destroyed. It may just be reduced in size or cleared in order to re-claim memory.
     * </p>
     */
    void flush();
}