package org.innobuilt.wicket.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;

public abstract class AbstractWebServicePage extends WebPage {

	//TODO make docs available through framework by visiting /mounted-path/docs
	//TODO Post this back to http://blog.brunoborges.com.br/2008/11/restful-web-services-with-wicket.html
	private static Logger LOGGER = Logger.getLogger(AbstractWebServicePage.class);
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String GET = "GET";
	public static final String DELETE = "DELETE";

	public AbstractWebServicePage(PageParameters params) {
		super(params);
		setStatelessHint(true);
	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		LOGGER.debug("QueryString:\n---------\n"+((WebRequest) getRequest()).getHttpServletRequest().getQueryString());
		String method = ((WebRequest) getRequest()).getHttpServletRequest().getMethod();
		if (POST.equals(method)) {
			try {
				setModelFromBody(getRequestBody());
			} catch(Exception e) {
				//swallow this exception
			}
			doPost(getPageParameters());
		} else if (GET.equals(method)) {
			doGet(getPageParameters());
		} else if (PUT.equals(method)) {
			try {
				setModelFromBody(getRequestBody());
			} catch(Exception e) {
				//swallow this exception
			}
			doPut(getPageParameters());
		} else if (DELETE.equals(method)) {
			doDelete(getPageParameters());
		}
	}

	@Override
	public final boolean hasAssociatedMarkup() {
		return false;
	}

	/**
	 * Use this method for lookups in the service implementation class
	 * TODO This should not have to be implemented, it should return an error message by default.
	 * @param params
	 */
	public abstract void doGet(PageParameters params);

	/**
	 * Use this method for updates in the service implementation class
	 * @param params
	 */
	public abstract void doPost(PageParameters params);

	/**
	 * Use this method for creates in the service implementation class
	 * @param params
	 */
	public abstract void doPut(PageParameters params);
	
	/**
	 * Use this method for deletes in the service implementation class
	 * @param params
	 */
	public abstract void doDelete(PageParameters params);

	/**
	 * @param body
	 */
	protected abstract void setModelFromBody(String body);

	private String getRequestBody() {
		HttpServletRequest request = ((WebRequest) getRequest()).getHttpServletRequest();
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			setDefaultModelObject(ex.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					setDefaultModelObject(ex.getMessage());
				}
			}
		}
		String body = stringBuilder.toString();
		LOGGER.debug("Message Body:\n---------\n"+body);
		return body;

	}

	@Override
	public Component add(IBehavior... arg0) {
		throw new UnsupportedOperationException("WebServicePage does not support IBehaviours");
	}

}
