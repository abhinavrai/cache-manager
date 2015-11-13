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
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import net.itrixlabs.cache.auth.SerializableAuthCache;
import net.itrixlabs.cache.csrf.SerializableCsrfCache;
import net.itrixlabs.cache.user.SerializableUserCache;
import net.itrixlabs.cache.util.Assert;

public class CacheManager<U, A, C> implements InitializingBean, DisposableBean {

    private static final Log logger = LogFactory.getLog(CacheManager.class);

    private SerializableUserCache<U> userCache;
    private SerializableAuthCache<A> authCache;
    private SerializableCsrfCache<C> csrfCache;

    private ScheduledThreadPoolExecutor cacheManagementExecutor;

    private CacheManagementWorker<U, A, C> cacheManagementWorker;

    public CacheManager() {
	this.cacheManagementExecutor = (ScheduledThreadPoolExecutor) Executors
		.newScheduledThreadPool(10);
	this.cacheManagementWorker = new CacheManagementWorker<>();
    }

    @Override
    public void afterPropertiesSet() {

	this.cacheManagementWorker.setAuthCache(this.authCache);
	this.cacheManagementWorker.setUserCache(this.userCache);
	this.cacheManagementWorker.setCsrfCache(this.csrfCache);

	Assert.assertNotNull(cacheManagementExecutor,
		"Cache management executor is not initialized!");

	Assert.assertNotNull(cacheManagementWorker,
		"Cache management worker thread pool can't be null!");

	cacheManagementExecutor.scheduleAtFixedRate(cacheManagementWorker, 60L, 600L, SECONDS);
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

    public CacheManager<U, A, C> setCacheManagementWorker(
	    CacheManagementWorker<U, A, C> cacheManagementWorker) {
	this.cacheManagementWorker = cacheManagementWorker;
	return this;
    }

    public CacheManager<U, A, C> setUserCache(SerializableUserCache<U> userCache) {
	this.userCache = userCache;
	return this;
    }

    public CacheManager<U, A, C> setAuthCache(SerializableAuthCache<A> authCache) {
	this.authCache = authCache;
	return this;
    }

    public CacheManager<U, A, C> setCsrfCache(SerializableCsrfCache<C> csrfCache) {
	this.csrfCache = csrfCache;
	return this;
    }
}