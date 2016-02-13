package net.itrixlabs.cache.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class SerializableAuthCacheTest {

    //@formatter:off
    private SerializableAuthCache<UsernamePasswordAuthenticationToken> authCache = 
	    new SerializableAuthCache<>();
    //@formatter:on

    private UsernamePasswordAuthenticationToken actualToken1, actualToken2, actualToken3;
    private Long keyLong1, keyLong2, keyLong3, keyLong4;

    @Before
    public void setup() {
	keyLong1 = System.nanoTime() + (-110345734834L);
	keyLong2 = System.nanoTime() * (-33L);
	keyLong3 = System.nanoTime() + (110343434834L);
	keyLong4 = System.nanoTime() + (-234565433L);
	actualToken1 = new UsernamePasswordAuthenticationToken("user1", "creds1",
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH1"));
	actualToken2 = new UsernamePasswordAuthenticationToken("user2", "creds2",
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH2"));
	actualToken3 = new UsernamePasswordAuthenticationToken("user2", "creds2",
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH3"));
	authCache.putInCache(keyLong1, actualToken1);
    }

    @Test
    public void getFromCache() {
	Assert.assertEquals("Not the same token", actualToken1.getPrincipal(),
		authCache.getFromCache(keyLong1).getPrincipal());
    }

    @Test
    public void putInCache() {
	authCache.putInCache(keyLong2, actualToken2);
	Assert.assertEquals("Not the same token", actualToken2.getPrincipal(),
		authCache.getFromCache(keyLong2).getPrincipal());
    }

    @Test
    public void evictFromCache() {
	authCache.putInCache(keyLong3, actualToken3);
	Assert.assertEquals("Token wasn't inserted in cache", actualToken3.getPrincipal(),
		authCache.getFromCache(keyLong3).getPrincipal());
	authCache.evictFromCache(keyLong3);
	Assert.assertEquals("Token wasn't evicted from cache", null,
		authCache.getFromCache(keyLong3));
    }

    @Test
    public void isPresentInCache() {
	Assert.assertEquals("Key wasn't present in cache", true,
		authCache.isPresentInCache(keyLong1));
	Assert.assertEquals("Key was present in cache", false,
		authCache.isPresentInCache(keyLong4));
    }
}