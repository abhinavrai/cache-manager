package net.itrixlabs.cache.generic;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SerializableGenericCacheTest {

    private SerializableGenericCache<User> genericCache = new SerializableGenericCache<>();

    private User actualUser1, actualUser2, actualUser3;
    private String keyString1, keyString2, keyString3, keyString4;

    @Before
    public void setup() {
	keyString1 = UUID.randomUUID().toString();
	keyString2 = UUID.randomUUID().toString();
	keyString3 = UUID.randomUUID().toString();
	keyString4 = UUID.randomUUID().toString();
	actualUser1 = new User("user1", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH1"));
	actualUser2 = new User("user2", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH2"));
	actualUser3 = new User("user3", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH3"));
	genericCache.putInCache(keyString1, actualUser1);
    }

    @Test
    public void getFromCache() {
	Assert.assertEquals("Not the same user", actualUser1.getUsername(),
		genericCache.getFromCache(keyString1).getUsername());
    }

    @Test
    public void putInCache() {
	genericCache.putInCache(keyString2, actualUser2);
	Assert.assertEquals("Not the same user", actualUser2.getUsername(),
		genericCache.getFromCache(keyString2).getUsername());
    }

    @Test
    public void evictFromCache() {
	genericCache.putInCache(keyString3, actualUser3);
	Assert.assertEquals("User wasn't inserted in cache", actualUser3.getUsername(),
		genericCache.getFromCache(keyString3).getUsername());
	genericCache.evictFromCache(keyString3);
	Assert.assertEquals("User wasn't evicted from cache", null,
		genericCache.getFromCache(keyString3));
    }

    @Test
    public void isPresentInCache() {
	Assert.assertEquals("Key wasn't present in cache", true,
		genericCache.isPresentInCache(keyString1));
	Assert.assertEquals("Key was present in cache", false,
		genericCache.isPresentInCache(keyString4));
    }
}