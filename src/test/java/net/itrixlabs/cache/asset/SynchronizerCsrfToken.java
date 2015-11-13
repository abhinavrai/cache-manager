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

import static org.springframework.security.core.SpringSecurityCoreVersion.SERIAL_VERSION_UID;

import net.itrixlabs.cache.util.Assert;

/**
 * <p>
 * An implementation of <code>CsrfToken</code> suitable for use in stateless systems. Follows some
 * of synchronizer pattern for creating and validating tokens.
 * </p>
 * 
 * @author Abhinav Rai
 * @see CsrfToken
 * @since November 13<sup>th</sup>, 2015
 *
 */
public class SynchronizerCsrfToken implements CsrfToken {

    private static final long serialVersionUID = SERIAL_VERSION_UID;

    private static String headerName = "CSRF-TOKEN";
    private static String cookieName = "X-CSRF-TOKEN";
    private final String identifier;
    private final String token;

    /**
     * <p>
     * Instantiates the token and makes sure it is sanitized at the time of creation.
     * </p>
     * 
     * @param identifier
     *            the identifier for token
     * @param token
     *            the actual value of the token
     */
    public SynchronizerCsrfToken(String identifier, String token) {
	Assert.assertNotNull(identifier, token);
	Assert.assertEquals(identifier, token.split("-")[0],
		"The specified token doesn't represent a synchronizer pattern!");
	this.identifier = identifier;
	this.token = token;
    }

    @Override
    public String getHeaderName() {
	return headerName;
    }

    /**
     * <p>
     * For setting the token's header name. Default is <code>CSRF-TOKEN</code>.
     * </p>
     * 
     * @param headerName
     *            the header name to set
     * @return the <code>SynchronizerCsrfToken</code> for further customization
     */
    public SynchronizerCsrfToken setHeaderName(String headerName) {
	Assert.assertNotEmpty(headerName, "A header name is required");
	SynchronizerCsrfToken.headerName = headerName;
	return this;
    }

    @Override
    public String getCookieName() {
	return cookieName;
    }

    /**
     * <p>
     * For setting the token's header name. Default is <code>X-CSRF-TOKEN</code>.
     * </p>
     * 
     * @param cookieName
     *            the cookie name to set
     * @return the <code>SynchronizerCsrfToken</code> for further customization
     */
    public SynchronizerCsrfToken setCookieName(String cookieName) {
	Assert.assertNotEmpty(cookieName, "A cookie name is required");
	SynchronizerCsrfToken.cookieName = cookieName;
	return this;
    }

    @Override
    public String getIdentifier() {
	return identifier;
    }

    @Override
    public String getToken() {
	return token;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof SynchronizerCsrfToken) {
	    if ((this.identifier == ((SynchronizerCsrfToken) obj).getIdentifier())
		    && (this.token == ((SynchronizerCsrfToken) obj).getToken()))
		return true;
	}
	return false;
    }
}