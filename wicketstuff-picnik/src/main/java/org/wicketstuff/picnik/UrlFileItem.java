package org.wicketstuff.picnik;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.wicket.util.convert.converters.LongConverter;
import org.apache.wicket.util.io.DeferredFileOutputStream;
import org.apache.wicket.util.upload.FileItem;
import org.apache.wicket.util.upload.ParameterParser;

/**
 * 
 */

/**
 * FileItem implementation where the data is being pulled from an URL.
 * <p>Created 06.04.2008 17:24:15</p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
@Deprecated
public class UrlFileItem implements FileItem {
	
	// Parts of this class were copied from DiskFileItem

	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UrlFileItem.class);
	
	/**
	 * Default content charset to be used when no explicit charset parameter is provided by the
	 * sender. Media subtypes of the "text" type are defined to have a default charset value of
	 * "ISO-8859-1" when received via HTTP.
	 */
	public static final String DEFAULT_CHARSET = "ISO-8859-1";
	
	private URL url;

	private String contentType;

	private String name;

	private long size;
	
	private String fieldName = "pullfile";

	private boolean formField;
	
	/**
	 * Cached contents of the file.
	 */
	private byte[] cachedContent;
	
	/**
	 * Size of buffer to use when writing an item to disk.
	 */
	private static final int WRITE_BUFFER_SIZE = 2048;
	
	/**
	 * Output stream for this item.
	 */
	private DeferredFileOutputStream dfos;

	public UrlFileItem(URL url) throws IOException {
		this.url = url;
		
		HttpClient client = new HttpClient(); 
		HttpMethod method = new GetMethod(url.toString());

		try {
			client.executeMethod(method);
			
			// get headers for metadata
			
			LOG.debug("getting headers");
			Header[] headers = method.getResponseHeaders();
			for (Header header : headers) {
				LOG.debug(header.getName() + " => " + header.getValue());
			}
			
			this.contentType = method.getResponseHeader("Content-Type").getValue();
			this.name = url.getPath();
			this.size = Long.parseLong(method.getRequestHeader("Content-Length").getValue()); 
		}
		finally {
			method.releaseConnection();
		}
	}
	
	/**
	 * @see org.apache.wicket.util.upload.FileItem#delete()
	 */
	@Override
	public void delete() {
		// can't delete URL...
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#get()
	 */
	@Override
	public byte[] get() {
		if (dfos.isInMemory())
		{
			if (cachedContent == null)
			{
				cachedContent = dfos.getData();
			}
			return cachedContent;
		}

		byte[] fileData = new byte[(int)getSize()];
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(dfos.getFile());
			fis.read(fileData);
		}
		catch (IOException e)
		{
			fileData = null;
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (IOException e)
				{
					// ignore
				}
			}
		}

		return fileData;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getContentType()
	 */
	@Override
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		if (!dfos.isInMemory())
		{
			return new FileInputStream(dfos.getFile());
		}

		if (cachedContent == null)
		{
			cachedContent = dfos.getData();
		}
		return new ByteArrayInputStream(cachedContent);
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Implement method getOutputStream
		return null;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getSize()
	 */
	@Override
	public long getSize() {
		return this.size;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getString()
	 */
	@Override
	public String getString() {
		byte[] rawdata = get();
		String charset = getCharSet();
		if (charset == null)
		{
			charset = DEFAULT_CHARSET;
		}
		try
		{
			return new String(rawdata, charset);
		}
		catch (UnsupportedEncodingException e)
		{
			return new String(rawdata);
		}
	}

	/**
	 * Returns the content charset passed by the agent or <code>null</code> if not defined.
	 * 
	 * @return The content charset passed by the agent or <code>null</code> if not defined.
	 */
	public String getCharSet()
	{
		ParameterParser parser = new ParameterParser();
		parser.setLowerCaseNames(true);
		// Parameter parser can handle null input
		Map params = parser.parse(getContentType(), ';');
		return (String)params.get("charset");
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#getString(java.lang.String)
	 */
	@Override
	public String getString(String encoding) throws UnsupportedEncodingException {
		return new String(get(), encoding);
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#isFormField()
	 */
	@Override
	public boolean isFormField() {
		return this.formField;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#isInMemory()
	 */
	@Override
	public boolean isInMemory() {
		return (dfos.isInMemory());
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#setFieldName(java.lang.String)
	 */
	@Override
	public void setFieldName(String name) {
		this.fieldName = name;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#setFormField(boolean)
	 */
	@Override
	public void setFormField(boolean state) {
		this.formField = state;
	}

	/**
	 * @see org.apache.wicket.util.upload.FileItem#write(java.io.File)
	 */
	@Override
	public void write(File file) throws Exception {
		// TODO Implement method write

	}

	public URL getUrl() {
		return url;
	}

	protected void setUrl(URL url) {
		this.url = url;
	}
}
