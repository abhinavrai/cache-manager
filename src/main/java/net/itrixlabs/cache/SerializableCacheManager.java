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
package net.itrixlabs.cache;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.itrixlabs.cache.auth.SerializableAuthCache;
import net.itrixlabs.cache.core.CacheManager;
import net.itrixlabs.cache.csrf.SerializableCsrfCache;
import net.itrixlabs.cache.user.SerializableUserCache;
import net.itrixlabs.cache.util.Assert;

/**
 * <p>
 * Sets up a <code>CacheManager</code> for use with a definitive caching strategy.
 * </p>
 * <p>
 * Describes all of the supported <code>CacheType</code> at once. In case all are not required,
 * simply parameterize the non required type to {@link java.lang.Object} and you are good to go.
 * Please be advised that the <code>CacheManager</code> will then not entertain the type identified
 * plainly as <code>Object</code>. Passing null argument to the non-required type is also not
 * necessary as it will not be considered at all.
 * </p>
 * 
 * @author Abhinav Rai
 * @see SerializableCacheManagementWorker
 * @since November 13<sup>th</sup>, 2015
 *
 */
public class SerializableCacheManager<U, A, C> implements CacheManager {

    private static final Log logger = LogFactory.getLog(SerializableCacheManager.class);

    private SerializableUserCache<U> userCache;
    private SerializableAuthCache<A> authCache;
    private SerializableCsrfCache<C> csrfCache;

    private ScheduledThreadPoolExecutor cacheManagementExecutor;

    private SerializableCacheManagementWorker<U, A, C> serializableCacheManagementWorker;

    /**
     * Constructs an instance of cache manager as <code>SerializableCacheManager</code>. This
     * implicitly registers the cache management executor
     */
    public SerializableCacheManager() {
	this.cacheManagementExecutor = (ScheduledThreadPoolExecutor) Executors
		.newScheduledThreadPool(10);
	this.serializableCacheManagementWorker = new SerializableCacheManagementWorker<>();
    }

    @Override
    public void afterPropertiesSet() {

	this.serializableCacheManagementWorker.setAuthCache(this.authCache);
	this.serializableCacheManagementWorker.setUserCache(this.userCache);
	this.serializableCacheManagementWorker.setCsrfCache(this.csrfCache);

	Assert.assertNotNull(cacheManagementExecutor,
		"Cache management executor is not initialized!");

	Assert.assertNotNull(serializableCacheManagementWorker,
		"Cache management worker thread pool can't be null!");

	cacheManagementExecutor.scheduleAtFixedRate(serializableCacheManagementWorker, 60L, 600L,
		SECONDS);
    }

    @Override
    public void destroy() {

	logger.info("Shutting down Cache Management System."
		+ " Please do not interrupt this operation."
		+ " It is best that you stay away from the management console for now!");

	try {
	    cacheManagementExecutor.shutdown();
	    if (cacheManagementExecutor.isShutdown()) {
		logger.info("Cache Management System was shut down successfully.");
	    } else {
		cacheManagementExecutor.awaitTermination(60L, SECONDS);
		logger.error(
			"Problems encountered while trying to shutdown Cache Management System."
				+ " This would lead to problems!"
				+ " I recommend you to contact support.");
	    }
	} catch (InterruptedException e) {
	    logger.fatal("Cache Management Executor was interrupted while it was shutting down!"
		    + " This will cause a memory leak.");
	}
    }

    /**
     * <p>
     * Registers the user cache with the worker.
     * </p>
     * 
     * @param userCache
     *            the user cache to be managed
     */
    public void setUserCache(SerializableUserCache<U> userCache) {
	this.userCache = userCache;
    }

    /**
     * <p>
     * Registers the auth cache with the worker.
     * </p>
     * 
     * @param authCache
     *            the auth cache to be managed
     */
    public void setAuthCache(SerializableAuthCache<A> authCache) {
	this.authCache = authCache;
    }

    /**
     * <p>
     * Registers the csrf cache with the worker.
     * </p>
     * 
     * @param csrfCache
     *            the csrf cache to be managed
     */
    public void setCsrfCache(SerializableCsrfCache<C> csrfCache) {
	this.csrfCache = csrfCache;
    }
}