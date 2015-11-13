package net.itrixlabs.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.itrixlabs.cache.auth.SerializableAuthCache;
import net.itrixlabs.cache.csrf.SerializableCsrfCache;
import net.itrixlabs.cache.user.SerializableUserCache;

public class CacheManagementWorker<U, A, C> implements Runnable {

    private static final Log logger = LogFactory.getLog(CacheManagementWorker.class);

    private SerializableUserCache<U> userCache;
    private SerializableAuthCache<A> authCache;
    private SerializableCsrfCache<C> csrfCache;

    CacheManagementWorker() {
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
	logger.info("Cache management task completed.");
    }

    CacheManagementWorker<U, A, C> setUserCache(SerializableUserCache<U> userCache) {
	this.userCache = userCache;
	return this;
    }

    CacheManagementWorker<U, A, C> setAuthCache(SerializableAuthCache<A> authCache) {
	this.authCache = authCache;
	return this;
    }

    CacheManagementWorker<U, A, C> setCsrfCache(SerializableCsrfCache<C> csrfCache) {
	this.csrfCache = csrfCache;
	return this;
    }
}