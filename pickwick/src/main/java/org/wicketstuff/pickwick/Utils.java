package org.wicketstuff.pickwick;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;

public class Utils {

	public static String getUri(PageParameters params){
		String uri = params.getString("uri");
		return decodeUri(uri);
	}
	
	public static String decodeUri(String uri){
		if (uri == null){
			return null;
		}
		try {
			return uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new WicketRuntimeException("can not decode " + uri, e);
		}
	}
}
