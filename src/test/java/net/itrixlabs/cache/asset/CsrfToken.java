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
package net.itrixlabs.cache.asset;

import java.io.Serializable;

/**
 * <p>
 * Provides the information about an expected CSRF token.
 * </p>
 * 
 * @author Abhinav Rai
 * @since November 13<sup>th</sup>, 2015
 *
 */
public interface CsrfToken extends Serializable {

    /**
     * <p>
     * For obtaining the HTTP header that the CSRF is populated on the response and can be placed on
     * requests instead of the parameter. Cannot be <code>null</code>.
     * </p>
     *
     * @return the HTTP header that the CSRF is populated on the response and can be placed on
     *         requests instead of the parameter
     */
    public String getHeaderName();

    /**
     * <p>
     * For obtaining the HTTP header that the CSRF is populated on the response and can be placed on
     * requests instead of the parameter. Cannot be <code>null</code>.
     * </p>
     *
     * @return the HTTP header that the CSRF is populated on the response and can be placed on
     *         requests instead of the parameter
     */
    public String getCookieName();

    /**
     * <p>
     * For obtaining the CSRF identifier. Cannot be <code>null</code>.
     * </p>
     * 
     * @return the <code>identifier</code> associated with a token
     */
    public String getIdentifier();

    /**
     * <p>
     * For obtaining the CSRF token value. Cannot be <code>null</code>.
     * </p>
     * 
     * @return the <code>value</code> of a token
     */
    public String getToken();
}