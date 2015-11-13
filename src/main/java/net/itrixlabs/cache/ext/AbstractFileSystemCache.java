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
package net.itrixlabs.cache.ext;

import static java.lang.System.currentTimeMillis;
import static net.itrixlabs.cache.config.Key.DEFAULT_TTL;
import static net.itrixlabs.cache.config.Key.DEFAULT_TTL_TIMEUNIT;
import static org.springframework.security.core.SpringSecurityCoreVersion.SERIAL_VERSION_UID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.itrixlabs.cache.config.CacheKey;
import net.itrixlabs.cache.config.CacheType;
import net.itrixlabs.cache.config.Key;
import net.itrixlabs.cache.core.ApplicationCache;
import net.itrixlabs.cache.util.Assert;

/**
 * <p>
 * Extends the <code>ApplicationCache</code> contract providing base for using file system as
 * storage for the cache.
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 11<sup>th</sup>, 2015
 * @see ApplicationCache
 *
 */
public abstract class AbstractFileSystemCache<V> implements ApplicationCache<Key, V>, Serializable {

    private static final long serialVersionUID = SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(AbstractFileSystemCache.class);

    /*
     * Represents the cache type (from the ones which are currently supported)
     */
    private CacheType type;

    /*
     * Specifies the TTL of an entry in the cache
     */
    private Long ttl = DEFAULT_TTL;

    /*
     * TimeUnit for the ttl period
     */
    private TimeUnit ttlUnit = DEFAULT_TTL_TIMEUNIT;

    /*
     * Represents the directory for setting-up the cache directory
     */
    private String cacheDir;

    /*
     * Represents the file in which cache data must be stored
     */
    private String cacheFile;

    /*
     * Represents the cache location on the file system. Typically, cacheDir + cacheFile
     */
    private String cacheLocation;

    protected final ConcurrentMap<Key, V> cache = new ConcurrentHashMap<>();

    public AbstractFileSystemCache() {

	//@formatter:off
	throw new IllegalStateException(
		"Can't invoke an application cache like this."
		+ " Atleast type argument is required."
		+ " Please use an appropriate constructor."
	);
	//@formatter:on
    }

    public AbstractFileSystemCache(CacheType type) {
	this(type, System.getProperty("user.dir") + File.separatorChar + "temp" + File.separatorChar
		+ "app_cache", type.toString() + ".ser");
    }

    public AbstractFileSystemCache(CacheType type, String cacheDir, String cacheFile) {
	this.type = type;
	this.cacheDir = cacheDir;
	this.cacheFile = cacheFile + ".ser";
	this.cacheLocation = cacheDir + File.separatorChar + cacheFile;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize() {
	try (ObjectInputStream objectInputStream = new ObjectInputStream(
		new FileInputStream(cacheLocation))) {
	    this.cache.putAll((Map<Key, ? extends V>) objectInputStream.readObject());
	} catch (FileNotFoundException e) {
	    logger.info(this.type.toString() + " cache doesn't exist! Creating a brand new one.");
	    File fileSystemCacheDir = new File(this.cacheDir);
	    if (fileSystemCacheDir.exists())
		fileSystemCacheDir.setWritable(true);
	    else {
		fileSystemCacheDir.getParentFile().getParentFile().setWritable(true); // Never null
		fileSystemCacheDir.getParentFile().mkdirs();
		fileSystemCacheDir.getParentFile().setWritable(true);
		fileSystemCacheDir.mkdirs();
		fileSystemCacheDir.setWritable(true);
	    }
	    try {
		File file = new File(cacheLocation);
		FileOutputStream stream = new FileOutputStream(file);
		stream.write(0);
		stream.flush();
		stream.close();
	    } catch (IOException ex) {
		logger.fatal(ex.getMessage());
	    }
	} catch (IOException | ClassNotFoundException e) {
	    if (e instanceof IOException)
		logger.warn(this.type.toString() + " cache was tampered with!"
			+ " Trust us, this is the last thing you want to do."
			+ " By design, this cannot be done by an attacker."
			+ " So, if it was a clean-up activity or a rougue administrator,"
			+ " you have been warned!");
	    else
		logger.error(this.type.toString()
			+ " cache implementation has changed from last invocation."
			+ " Delete the cache manually and re-create."
			+ " Automatic recovery will be supported in future releases.");
	}
    }

    protected Key generate(Object identifier) {
	Key key = new CacheKey();
	key.setKey(identifier);
	key.setCreationTime(currentTimeMillis());
	return key;
    }

    @Override
    public void flush() {
	long ttlMillis = ttlUnit.toMillis(ttl);
	Iterator<Key> iter = this.cache.keySet().iterator();
	while (iter.hasNext()) {
	    Key key = (Key) iter.next();
	    if (key.getCreationTime() < System.currentTimeMillis() - ttlMillis)
		cache.remove(key);
	}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	Assert.assertNotEmpty(this.cacheDir, "A cache directory location is required.");
	Assert.assertNotEmpty(this.cacheFile, "A cache filename is required.");
	Assert.assertNotNull(this.type, "A cache type is required.");
	this.initialize();
    }

    @Override
    public void destroy() throws Exception {
	try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		new FileOutputStream(new File(cacheLocation)))) {
	    objectOutputStream.writeObject(this.cache);
	} catch (NullPointerException | IOException e) {
	    logger.info(this.cache + "Cache storage tampered/unreadable/unavailable!"
		    + " Details weren't persisted to the storage."
		    + " It is advisable to clean the cache directory.");
	}
    }

    /**
     * <p>
     * Sets the time-to-live for an entry in cache.
     * </p>
     * 
     * @param ttl
     *            the ttl to set
     * @return <code>AbstractFileSystemCache</code> for further customization
     */
    public AbstractFileSystemCache<V> setTtl(Long ttl) {
	Assert.assertNotNull(ttl, "Can't accept null as TTL.");
	this.ttl = ttl;
	return this;
    }

    /**
     * <p>
     * Sets the time unit for cache entry time-to-live strategy.
     * </p>
     * 
     * @param ttlUnit
     *            the ttl time unit to set
     * @return <code>AbstractFileSystemCache</code> for further customization
     */
    public AbstractFileSystemCache<V> setTtlUnit(TimeUnit ttlUnit) {
	Assert.assertNotNull(ttlUnit, "Can't accept null as TTL TimeUnit.");
	this.ttlUnit = ttlUnit;
	return this;
    }

    /**
     * <p>
     * Sets the cache directory location to use with the particular type of cache in focus.
     * </p>
     * 
     * @param cacheDir
     *            the cache directory location to use
     * @return <code>AbstractFileSystemCache</code> for further customization
     */
    public AbstractFileSystemCache<V> setCacheDir(String cacheDir) {
	Assert.assertNotEmpty(cacheDir, "A cache directory location is required.");
	this.cacheDir = cacheDir;
	return this;
    }

    /**
     * <p>
     * Sets the cache filename to use with the particular type of cache in focus.
     * </p>
     * 
     * @param cacheFile
     *            the cache filename to use
     * @return <code>AbstractFileSystemCache</code> for further customization
     */
    public AbstractFileSystemCache<V> setCacheFile(String cacheFile) {
	Assert.assertNotEmpty(cacheFile, "A cache filename is required.");
	this.cacheFile = cacheFile;
	return this;
    }
}