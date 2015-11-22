package net.itrixlabs.cache.csrf;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.itrixlabs.cache.asset.SynchronizerCsrfToken;

public class SerializableCsrfCacheTest {

    //@formatter:off
    private SerializableCsrfCache<SynchronizerCsrfToken> csrfCache = 
	    new SerializableCsrfCache<>();
    //@formatter:on

    private SynchronizerCsrfToken actualToken1, actualToken2, actualToken3;
    private String value, identifier, keyString1, keyString2, keyString3;

    @Before
    public void setup() {
	keyString1 = UUID.randomUUID().toString();
	keyString2 = UUID.randomUUID().toString();
	keyString3 = UUID.randomUUID().toString();
	value = UUID.randomUUID().toString();
	identifier = value.split("-")[0];
	actualToken1 = new SynchronizerCsrfToken(identifier, value);
	value = UUID.randomUUID().toString();
	identifier = value.split("-")[0];
	actualToken2 = new SynchronizerCsrfToken(identifier, value);
	value = UUID.randomUUID().toString();
	identifier = value.split("-")[0];
	actualToken3 = new SynchronizerCsrfToken(identifier, value);
	csrfCache.putInCache(keyString1, actualToken1);
    }

    @Test
    public void getFromCache() {
	Assert.assertEquals("Not the same token", actualToken1.getIdentifier(),
		csrfCache.getFromCache(keyString1).getIdentifier());
    }

    @Test
    public void putInCache() {
	csrfCache.putInCache(keyString2, actualToken2);
	Assert.assertEquals("Not the same user", actualToken2.getIdentifier(),
		csrfCache.getFromCache(keyString2).getIdentifier());
    }

    @Test
    public void evictFromCache() {
	csrfCache.putInCache(keyString3, actualToken3);
	Assert.assertEquals("User wasn't inserted in cache", actualToken3.getIdentifier(),
		csrfCache.getFromCache(keyString3).getIdentifier());
	csrfCache.evictFromCache(keyString3);
	Assert.assertEquals("Token wasn't evicted from cache", null,
		csrfCache.getFromCache(keyString3));
    }
}