package org.wicketstuff.pickwick;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;

public class Utils {

	public static String getUri(PageParameters params){
		if(params == null){ //can be null because of authentication
			return "";
		}
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
	
	public static String getFolderFor(String uri){
		if (uri != null){
			if (uri.lastIndexOf("/") == -1){
				return uri;
			}
			String folder = uri.substring(0, uri.lastIndexOf("/"));
			//still an image remove last name
			if (folder.endsWith(".jpg") || folder.endsWith(".JPG")){
				folder = folder.substring(0, folder.lastIndexOf("/"));
			}
			return folder;
		}
		return null;
	}
}
