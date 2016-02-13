package net.itrixlabs.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.itrixlabs.cache.auth.SerializableAuthCache;
import net.itrixlabs.cache.csrf.SerializableCsrfCache;
import net.itrixlabs.cache.generic.SerializableGenericCache;
import net.itrixlabs.cache.user.SerializableUserCache;

public class SerializableCacheManagementWorker<U, A, C, G> implements Runnable {

    private static final Log logger = LogFactory.getLog(SerializableCacheManagementWorker.class);

    private SerializableUserCache<U> userCache;
    private SerializableAuthCache<A> authCache;
    private SerializableCsrfCache<C> csrfCache;
    private SerializableGenericCache<G> genericCache;

    SerializableCacheManagementWorker() {
    }

    @Override
    public void run() {
	logger.info("Commencing cache management task.");
	if (userCache != null)
	    userCache.flush();
	if (authCache != null)
	    authCache.flush();
	if (csrfCache != null)
	    csrfCache.flush();
	if (genericCache != null)
	    genericCache.flush();
	logger.info("Cache management task completed.");
    }

    SerializableCacheManagementWorker<U, A, C, G> setUserCache(SerializableUserCache<U> userCache) {
	this.userCache = userCache;
	return this;
    }

    SerializableCacheManagementWorker<U, A, C, G> setAuthCache(SerializableAuthCache<A> authCache) {
	this.authCache = authCache;
	return this;
    }

    SerializableCacheManagementWorker<U, A, C, G> setCsrfCache(SerializableCsrfCache<C> csrfCache) {
	this.csrfCache = csrfCache;
	return this;
    }

    SerializableCacheManagementWorker<U, A, C, G> setGenericCache(
	    SerializableGenericCache<G> genericCache) {
	this.genericCache = genericCache;
	return this;
    }
}