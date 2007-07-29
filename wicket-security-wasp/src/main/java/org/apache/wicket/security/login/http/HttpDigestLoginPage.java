/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.login.http;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Login page that uses httpauthentication to login. This class adds support for
 * the digest scheme. RFC 2617. Support for the basic scheme is configurable.
 * 
 * @author marrink
 * @see <a href="http://tools.ietf.org/html/rfc2617">rfc2617</a>
 */
public abstract class HttpDigestLoginPage extends HttpAuthenticationLoginPage
{
	private static final Logger log = LoggerFactory.getLogger(HttpDigestLoginPage.class);
	private boolean allowBasicAuthenication = true;

	/**
	 * Construct.
	 */
	public HttpDigestLoginPage()
	{
	}

	/**
	 * Construct.
	 * 
	 * @param model
	 */
	public HttpDigestLoginPage(IModel model)
	{
		super(model);
	}

	/**
	 * Construct.
	 * 
	 * @param pageMap
	 */
	public HttpDigestLoginPage(IPageMap pageMap)
	{
		super(pageMap);
	}

	/**
	 * Construct.
	 * 
	 * @param parameters
	 */
	public HttpDigestLoginPage(PageParameters parameters)
	{
		super(parameters);
	}

	/**
	 * Construct.
	 * 
	 * @param pageMap
	 * @param model
	 */
	public HttpDigestLoginPage(IPageMap pageMap, IModel model)
	{
		super(pageMap, model);
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#handleAuthentication(org.apache.wicket.protocol.http.WebRequest,
	 *      org.apache.wicket.protocol.http.WebResponse, java.lang.String,
	 *      java.lang.String)
	 */
	protected void handleAuthentication(WebRequest request, WebResponse response, String scheme,
			String param) throws LoginException
	{
		if (!handleDigestAuthentication(request, response, scheme, param))
			return;
		if (isAllowBasicAuthenication())
			super.handleAuthentication(request, response, scheme, param);
		else
		{
			log.error("Unsupported Http authentication type: " + scheme);
			throw new RestartResponseAtInterceptPageException(Application.get()
					.getApplicationSettings().getAccessDeniedPage());
		}
	}

	/**
	 * Handles authentication for the "Digest" scheme. If the scheme is not the
	 * digest scheme true is returned so another implementation may try it
	 * 
	 * @param request
	 * @param response
	 * @param scheme
	 * @param param
	 * @return true if authentication by another scheme should be attempted,
	 *         false if authentication by another scheme should not be
	 *         attempted.
	 */
	protected boolean handleDigestAuthentication(WebRequest request, WebResponse response,
			String scheme, String param)
	{
		// TODO finish this
		return true;
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#requestAuthentication(org.apache.wicket.protocol.http.WebRequest,
	 *      org.apache.wicket.protocol.http.WebResponse)
	 */
	protected void requestAuthentication(WebRequest request, WebResponse response)
	{
		if (allowBasicAuthenication)
			super.requestAuthentication(request, response);
		else
			response.getHttpServletResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		addDigestHeaders(request, response);

	}

	/**
	 * Adds a "WWW-Authenticate" header for digest authentication to the
	 * response.
	 * 
	 * @param request
	 * @param response
	 */
	protected void addDigestHeaders(WebRequest request, WebResponse response)
	{
		AppendingStringBuffer buffer = new AppendingStringBuffer(150);
		buffer.append("Digest realm=\"").append(getRealm(request, response)).append("\"");
		String domain = getDomain(request, response);
		if (domain != null)
			buffer.append(", domain=\"").append(domain).append("\"");
		buffer.append(", nonce=\"").append(getNonce(request, response)).append("\"");
		buffer.append(", opaque=\"").append(getNonce(request, response)).append("\"");
		DigestAuthorizationRequestHeader header = parseHeader(request);
		if (header != null)
			buffer.append(", stale=\"").append(isNonceStale(request, header.getNonce())).append(
					"\"");
		String algorithm = getAlgorithm(request, response);
		if (algorithm != null)
			buffer.append(", algorithm=\"").append(algorithm).append("\"");
		String qop = getQop(request, response);
		if (qop != null)
			buffer.append(", qop=\"").append(qop).append("\"");
		response.getHttpServletResponse().addHeader("WWW-Authenticate", buffer.toString());
	}

	/**
	 * The optional qop-options as specified in section 3.2.1 of RFC 2617.
	 * Default is to return both 'auth' and 'auth-int'.
	 * 
	 * @param request
	 * @param response
	 * @return a string identifying the supported qop-options
	 */
	protected String getQop(WebRequest request, WebResponse response)
	{
		return "auth auth-int";
	}

	/**
	 * The optional algorithm as specified in section 3.2.1 of RFC 2617. Default
	 * is to return MD5.
	 * 
	 * @param request
	 * @param response
	 * @return a string identifying the request algorithm
	 */
	protected String getAlgorithm(WebRequest request, WebResponse response)
	{
		return "MD5";
	}

	/**
	 * The optional (list of) domain(s) as specified in section 3.2.1 of RFC
	 * 2617. Default is to return null.
	 * 
	 * @param request
	 * @param response
	 * @return an unquoted list of space separated URI's, or null if all URI's
	 *         on this server apply.
	 */
	protected String getDomain(WebRequest request, WebResponse response)
	{
		return null;
	}

	/**
	 * Validates the contents of nonce send with the request. as specified in
	 * section 3.2.1 of RFC 2617. By default it does not enforce a time limit on
	 * nonces but does check for a valid timestamp, ETag and private key.
	 * 
	 * @param request
	 * 
	 * @param nonce
	 * @return true if the nonce is stale, false otherwise
	 * @see #getNonce(WebRequest, WebResponse)
	 */
	protected boolean isNonceStale(WebRequest request, String nonce)
	{
		String[] parts = new String(Base64.decodeBase64(nonce.getBytes())).split(":");
		if (parts.length != 3)
			return true;
		long nonceTime = 0;
		try
		{
			nonceTime = Long.parseLong(parts[0]);
		}
		catch (NumberFormatException e)
		{
			return true;
		}
		if (nonceTime < 0 || nonceTime > System.currentTimeMillis())
			return true;
		if (!Objects.equal(parts[1], request.getHttpServletRequest().getHeader("ETag")))
			return true;
		return !Objects.equal(getPrivateKey(), parts[2]);
	}

	/**
	 * The nonce as specified in section 3.2.1 of RFC 2617.
	 * 
	 * @param request
	 * @param response
	 * @return a base64 encoded string
	 * @see #isNonceStale(WebRequest, String)
	 */
	protected String getNonce(WebRequest request, WebResponse response)
	{
		long time = System.currentTimeMillis();
		return new String(Base64.encodeBase64(new AppendingStringBuffer(50).append(time)
				.append(":").append(request.getHttpServletRequest().getHeader("ETag")).append(":")
				.append(getPrivateKey()).toString().getBytes()));
	}

	/**
	 * A private server key used by the default implementation of
	 * {@link #getNonce(WebRequest, WebResponse)}
	 * 
	 * @return a private server key.
	 */
	protected String getPrivateKey()
	{
		return "Wasp, to protect and serve 'The Queen'.";
	}

	/**
	 * The opaque as specified in section 3.2.1 of RFC 2617.
	 * 
	 * @param request
	 * @param response
	 * @return a base64 encoded string
	 */
	protected String getOpaque(WebRequest request, WebResponse response)
	{
		return new String(Base64.encodeBase64("Wicket, tastes like honey.".getBytes()));
	}

	/**
	 * Tells if besides digest also basic authentication is supported. Default
	 * is true
	 * 
	 * @return true if basic authentication is also supported, false otherwise
	 */
	public final boolean isAllowBasicAuthenication()
	{
		return allowBasicAuthenication;
	}

	/**
	 * Sets the flag to allow or disallow basic authentication.
	 * 
	 * @param allowBasicAuthenication
	 *            allowBasicAuthenication
	 */
	public final void setAllowBasicAuthenication(boolean allowBasicAuthenication)
	{
		this.allowBasicAuthenication = allowBasicAuthenication;
	}

	/**
	 * Parses the authorization header for a digest scheme.
	 * 
	 * @param request
	 * @return the header or null if the header was not available or for a
	 *         different scheme
	 */
	protected final DigestAuthorizationRequestHeader parseHeader(WebRequest request)
	{
		String header = request.getHttpServletRequest().getHeader("Authorization");
		if (header == null)
			return null;
		// TODO finish this
		return null;
	}

	/**
	 * Simple pojo to hold all the parsed fields from the request header
	 * "Authorization".
	 * 
	 * @author marrink
	 */
	protected static final class DigestAuthorizationRequestHeader
	{
		private String realm;
		private String nonce;
		private String opaque;

		/**
		 * Construct.
		 * 
		 * @param realm
		 * @param nonce
		 * @param opaque
		 */
		protected DigestAuthorizationRequestHeader(String realm, String nonce, String opaque)
		{
			super();
			this.realm = realm;
			this.nonce = nonce;
			this.opaque = opaque;
		}

		/**
		 * Gets realm.
		 * 
		 * @return realm
		 */
		public final String getRealm()
		{
			return realm;
		}

		/**
		 * Gets nonce.
		 * 
		 * @return nonce
		 */
		public final String getNonce()
		{
			return nonce;
		}

		/**
		 * Gets opaque.
		 * 
		 * @return opaque
		 */
		public final String getOpaque()
		{
			return opaque;
		}
	}
}
