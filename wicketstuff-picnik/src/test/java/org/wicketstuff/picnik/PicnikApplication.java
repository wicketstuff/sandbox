package org.wicketstuff.picnik;

import org.apache.wicket.IConverterLocator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.convert.ConverterLocator;
import org.apache.wicket.util.file.Folder;
import org.wicketstuff.pullupload.PulledFileUpload;
import org.wicketstuff.pullupload.PulledFileUploadConverter;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 */
public class PicnikApplication extends WebApplication
{    
	@Override
	protected void init() {
		super.init();
		mountBookmarkablePage("/picnikinabox", PicnikInABoxPage.class);
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage()
	{
		return PicnikExamplePage.class;
	}

	@Override
	protected IConverterLocator newConverterLocator() 
	{
		ConverterLocator ret =  new ConverterLocator();
		ret.set(PulledFileUpload.class, new PulledFileUploadConverter());
		return ret;
	}


}
