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
package net.itrixlabs.cache.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 * Primary marker for cache management. Simply extends the <code>InitializingBean</code> &amp;
 * <code>DisposableBean</code> in order to define the post-instantiation, execution &amp;
 * pre-destruction contract.
 * </p>
 * <p>
 * <b><i>Note that the interface is declared</i></b> <code>abstract</code> <b><i>which doesn't make
 * any sense.</i></b> But, it's just to notify the programmer that this type is not an
 * initialization/autowiring candidate. Interfaces/Classes extending/implementing this contract must
 * be initialized so as to support multiple types and instances of cache per container (as suited).
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 10<sup>th</sup>, 2015
 *
 */
public abstract interface CacheManager extends InitializingBean, DisposableBean {
}