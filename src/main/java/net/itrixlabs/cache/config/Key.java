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
package net.itrixlabs.cache.config;

import static java.util.concurrent.TimeUnit.DAYS;

import java.util.concurrent.TimeUnit;

import net.itrixlabs.cache.core.ApplicationCache;

/**
 * <p>
 * Primary contract for defining the key to an entry in the cache. Generally, the user needs to
 * worry only about key's name and TTL related values. Others may be used internally, unless
 * specified otherwise.
 * </p>
 * 
 * @author Abhinav Rai
 * @see ApplicationCache
 * @since November 12<sup>th</sup>, 2015
 *
 */
public interface Key {

    String DEFAULT_KEY_STRING = "KEY";

    Long DEFAULT_TTL = 1L;

    TimeUnit DEFAULT_TTL_TIMEUNIT = DAYS;

    Long DEFAULT_CREATION_TIME = System.currentTimeMillis() - 86400000L;

    Long DEFAULT_CACHE_MAX_SIZE = 640L;

    Object getKey();
    
    void setKey(Object identifier);

    Long getCreationTime();
    
    void setCreationTime(Long creationTime);
}