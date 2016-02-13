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

/**
 * <p>
 * Named constants describing the types of caches supported by the cache manager
 * out of the box.
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 11<sup>th</sup>, 2015
 *
 */
public enum CacheType {

	/**
	 * Represents an Authentication/Authorization cache.
	 */
	AUTH,

	/**
	 * Represents a CSRF cache.
	 */
	CSRF,

	/**
	 * Represents a Spring Security styled <code>UserDetails</code> object.
	 */
	USER,

	/**
	 * Represents a Generic cache object provided the manager is being used for
	 * a non-differential use.
	 */
	GENERIC;

	public String toString() {
		return name().toLowerCase();
	};
}