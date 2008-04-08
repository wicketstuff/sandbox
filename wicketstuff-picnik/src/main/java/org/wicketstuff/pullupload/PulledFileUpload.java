/**
 * 
 */
package org.wicketstuff.pullupload;

import java.net.URL;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.upload.FileItem;

/**
 * FileUpload implementation which does not get its data via a HTTP-mulitpart-POST, but by pulling
 * it from a URL.
 * <p>
 * Created 06.04.2008 18:52:48
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PulledFileUpload extends FileUpload {

	private URL url;

	public PulledFileUpload(URL url, FileItem item) {
		super(item);
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

}
