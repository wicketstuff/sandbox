/**
 * 
 */
package org.wicketstuff.pullupload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.upload.DiskFileItemFactory;
import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.util.upload.FileUpload;

/**
 * Converter for PulledFileUpload objects into the pull-URLs and vice versa.
 * Apply this to a FormComponent, and its target Model will be updated
 * with a PulledFileUpload.
 * <p>
 * Created 06.04.2008 17:08:36
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PulledFileUploadConverter implements IConverter {

//	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PulledFileUploadConverter.class);

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,
	 *      java.util.Locale)
	 */
	public Object convertToObject(String value, Locale locale) {

		HttpClient client = new HttpClient();
		HttpMethod method = null;

		try {
			URL url = new URL(value);
			method = new GetMethod(url.toString());
			client.executeMethod(method);

			// get headers for metadata
			String fieldName = "pulledFileUpload";	// not sure how to get the real value in here...
			String contentType = method.getResponseHeader("Content-Type").getValue();
			String name = url.getPath();
			boolean isFormField = true;

			DiskFileItemFactory factory = new DiskFileItemFactory();
			FileItem fileItem = factory.createItem(fieldName, contentType, isFormField, name);
			IOUtils.copy(method.getResponseBodyAsStream(), fileItem.getOutputStream());

			return new PulledFileUpload(url, fileItem);
		} catch (MalformedURLException e) {
			ConversionException ex = new ConversionException("Can't convert " + value + " into a URL.", e);
			ex.setConverter(this);
			ex.setLocale(locale);
			throw ex;
		} catch (IOException e) {
			ConversionException ex = new ConversionException("Can't download " + value + ".", e);
			ex.setConverter(this);
			ex.setLocale(locale);
			throw ex;
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToString(java.lang.Object,
	 *      java.util.Locale)
	 */
	public String convertToString(Object value, Locale locale) {
		return ((PulledFileUpload) value).getUrl().toString();
	}

}
