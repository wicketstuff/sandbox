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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.PropertyResolverConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
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
	/**
	 * Matches recurring patterns like : key="some value" or key=value separated
	 * by a comma (,). groups are as following 1:key-value pair, 2:key, 3:value
	 * without quotes if value was quoted, 4: value if value was not quoted.
	 */
	private static final Pattern HEADER_FIELDS = Pattern
			.compile("(([a-zA-Z]+)=(?:\"([\\p{Graph}\\p{Blank}]+?)\"|([^\\s\",]+)))+,?");


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
		if (!"Digest".equalsIgnoreCase(scheme))
			return true;
		if (param == null)
		{
			log.error("Digest headers not supplied");
			return false;
		}
		DigestAuthorizationRequestHeader header = parseHeader(request);
		if (header == null)
		{
			log.error("Invalid Digest headers supplied:" + param);
			return false;
		}
		// is there anything we should do with the digest-uri?
		String supportedQop = getQop(request, response);
		boolean qopSupport = !Strings.isEmpty(supportedQop);
		if (qopSupport)
		{
			// if we sent qop header the client must return one of the options
			String[] qopOptions = supportedQop.split(" ");
			boolean supported = false;
			for (int i = 0; i < qopOptions.length && !supported; i++)
				supported = qopOptions[i].equals(header.getQop());
			if (!supported)
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
			// we requested qop so these headers must be present
			if (Strings.isEmpty(header.getCnonce()) || Strings.isEmpty(header.getNc()))
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
			// TODO validate nonce-count (nc)
		}
		else
		{
			// no qop support so these headers are not allowed
			if (!(Strings.isEmpty(header.getCnonce()) && Strings.isEmpty(header.getNc())))
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		// verify response (request-digest)


		// TODO finish this
		return false;
	}

	/**
	 * Performs a digest over a secret and some data as required by the
	 * algorithm. The default only supports MD5 and MD5-sess.
	 * 
	 * @param header
	 * @param secret
	 * @param data
	 * @return the digest or null if the algorithm is not supported
	 * @see #checksum(String, String)
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.1">section
	 *      3.2.1 (algorithm)</a>
	 */
	protected String digest(DigestAuthorizationRequestHeader header, String secret, String data)
	{
		// TODO replace header with algorithm, if we don't need the complete
		// header
		String algorithm = header.getAlgorithm();
		if ("MD5".equals(algorithm) || "MD5-sess".equals(algorithm))
		{
			return checksum(algorithm, secret + ":" + data);
		}
		return null;
	}

	/**
	 * Performs a checksum operation over the data as required by the algorithm.
	 * The default only supports MD5 and MD5-sess.
	 * 
	 * @param algorithm
	 * @param data
	 * @return a checksum or null if the algorithm is not supported
	 * @throws WicketRuntimeException
	 *             if the algorithm could not be located
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.1">section
	 *      3.2.1 (algorithm)</a>
	 */
	protected String checksum(String algorithm, String data)
	{
		if ("MD5".equals(algorithm) || "MD5-sess".equals(algorithm))
		{
			MessageDigest digest = null;
			try
			{
				digest = MessageDigest.getInstance(algorithm);
			}
			catch (NoSuchAlgorithmException e)
			{
				throw new WicketRuntimeException("Client requested " + algorithm
						+ ", but the algorithm could not be located");
			}
			digest.update(data.getBytes());
			return new String(digest.digest());
		}
		return null;
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
		if (!header.startsWith("Digest "))
			return null;
		header = header.substring(7);
		Matcher m = HEADER_FIELDS.matcher(header);
		if (!m.matches())
			return null;
		DigestAuthorizationRequestHeader digestHeader = new DigestAuthorizationRequestHeader();
		m.reset();
		while (m.find())
		{
			String key = m.group(2);
			String value = m.group(3);
			if (Strings.isEmpty(value))
				value = m.group(4);
			if (!digestHeader.addKeyValuePair(key, value))
				log.warn("Unknown header: " + key + ", skipping header.");
		}
		return digestHeader;
	}

	/**
	 * Simple pojo to hold all the parsed fields from the request header
	 * "Authorization".
	 * 
	 * @author marrink
	 */
	protected static final class DigestAuthorizationRequestHeader
	{
		private static final PropertyResolverConverter converter = new NoOpPropertyResolverConverter();
		private String username;
		private String realm;
		private String nonce;
		private String uri;
		private String qop;
		private String nc;
		private String cnonce;
		private String response;
		private String opaque;
		private String algorithm;

		/**
		 * Constructor to be used when key value pairs are going to be added
		 * later.
		 * 
		 * @see #addKeyValuePair(String, String)
		 */
		protected DigestAuthorizationRequestHeader()
		{

		}

		/**
		 * Dynamically resolves a header to the correct field and sets it value.
		 * 
		 * @param key
		 * @param value
		 * @return true, if the value was set, false if the value could not be
		 *         set
		 */
		public boolean addKeyValuePair(String key, String value)
		{
			if (Strings.isEmpty(key))
				return false;
			try
			{
				PropertyResolver.setValue(key, this, value, converter);
			}
			catch (WicketRuntimeException e)
			{
				log.debug("Failed to set header: " + key, e);
				return false;
			}
			return true;
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

		/**
		 * Gets username.
		 * 
		 * @return username
		 */
		public String getUsername()
		{
			return username;
		}

		/**
		 * Sets username.
		 * 
		 * @param username
		 *            username
		 */
		public void setUsername(String username)
		{
			this.username = username;
		}

		/**
		 * Gets uri.
		 * 
		 * @return uri
		 */
		public String getUri()
		{
			return uri;
		}

		/**
		 * Sets uri.
		 * 
		 * @param uri
		 *            uri
		 */
		public void setUri(String uri)
		{
			this.uri = uri;
		}

		/**
		 * Gets qop.
		 * 
		 * @return qop
		 */
		public String getQop()
		{
			return qop;
		}

		/**
		 * Sets qop.
		 * 
		 * @param qop
		 *            qop
		 */
		public void setQop(String qop)
		{
			this.qop = qop;
		}

		/**
		 * Gets nc.
		 * 
		 * @return nc
		 */
		public String getNc()
		{
			return nc;
		}

		/**
		 * Sets nc.
		 * 
		 * @param nc
		 *            nc
		 */
		public void setNc(String nc)
		{
			this.nc = nc;
		}

		/**
		 * Gets cnonce.
		 * 
		 * @return cnonce
		 */
		public String getCnonce()
		{
			return cnonce;
		}

		/**
		 * Sets cnonce.
		 * 
		 * @param cnonce
		 *            cnonce
		 */
		public void setCnonce(String cnonce)
		{
			this.cnonce = cnonce;
		}

		/**
		 * Gets response.
		 * 
		 * @return response
		 */
		public String getResponse()
		{
			return response;
		}

		/**
		 * Sets response.
		 * 
		 * @param response
		 *            response
		 */
		public void setResponse(String response)
		{
			this.response = response;
		}

		/**
		 * Sets realm.
		 * 
		 * @param realm
		 *            realm
		 */
		public void setRealm(String realm)
		{
			this.realm = realm;
		}

		/**
		 * Sets nonce.
		 * 
		 * @param nonce
		 *            nonce
		 */
		public void setNonce(String nonce)
		{
			this.nonce = nonce;
		}

		/**
		 * Sets opaque.
		 * 
		 * @param opaque
		 *            opaque
		 */
		public void setOpaque(String opaque)
		{
			this.opaque = opaque;
		}

		/**
		 * Gets algorithm.
		 * 
		 * @return algorithm
		 */
		public String getAlgorithm()
		{
			return algorithm;
		}

		/**
		 * Sets algorithm.
		 * 
		 * @param algorithm
		 *            algorithm
		 */
		public void setAlgorithm(String algorithm)
		{
			this.algorithm = algorithm;
		}
	}
	private static class NoOpPropertyResolverConverter extends PropertyResolverConverter
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 */
		public NoOpPropertyResolverConverter()
		{
			super(null, null);
		}

		/**
		 * @see org.apache.wicket.util.lang.PropertyResolverConverter#convert(java.lang.Object,
		 *      java.lang.Class)
		 */
		public Object convert(Object object, Class clz)
		{
			return object; // assume correct type.
		}

	}
}
