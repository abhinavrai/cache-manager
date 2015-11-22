package net.itrixlabs.cache.user;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SerializableUserCacheTest {

    private SerializableUserCache<User> userCache = new SerializableUserCache<>();

    private User actualUser1, actualUser2, actualUser3;
    private String keyString1, keyString2, keyString3;

    @Before
    public void setup() {
	keyString1 = UUID.randomUUID().toString();
	keyString2 = UUID.randomUUID().toString();
	keyString3 = UUID.randomUUID().toString();
	actualUser1 = new User("user1", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH1"));
	actualUser2 = new User("user2", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH2"));
	actualUser3 = new User("user3", "password", true, true, true, true,
		AuthorityUtils.commaSeparatedStringToAuthorityList("AUTH3"));
	userCache.putInCache(keyString1, actualUser1);
    }

    @Test
    public void getFromCache() {
	Assert.assertEquals("Not the same user", actualUser1.getUsername(),
		userCache.getFromCache(keyString1).getUsername());
    }

    @Test
    public void putInCache() {
	userCache.putInCache(keyString2, actualUser2);
	Assert.assertEquals("Not the same user", actualUser2.getUsername(),
		userCache.getFromCache(keyString2).getUsername());
    }

    @Test
    public void evictFromCache() {
	userCache.putInCache(keyString3, actualUser3);
	Assert.assertEquals("User wasn't inserted in cache", actualUser3.getUsername(),
		userCache.getFromCache(keyString3).getUsername());
	userCache.evictFromCache(keyString3);
	Assert.assertEquals("User wasn't evicted from cache", null,
		userCache.getFromCache(keyString3));
    }
}