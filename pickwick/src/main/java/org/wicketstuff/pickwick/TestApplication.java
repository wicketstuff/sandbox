package org.wicketstuff.pickwick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.basic.EmptyRequestTarget;
import org.apache.wicket.request.target.basic.URIRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

public class TestApplication extends WebApplication {
	public static final String SEQUENCE_PAGE_PATH = "sequence";

	public static final String SCALED_IMAGE_PATH = "scaled";

	public static final String THUMBNAIL_IMAGE_PATH = "thumbnail";

	public static final String IMAGE_PAGE_PATH = "image";

	private static final Log log = LogFactory.getLog(TestApplication.class);

	@Override
	public Class getHomePage() {
		return SequencePage.class;
	}

	@Override
	protected void init() {
		// FIXME how to specify url coding strategy for the home page?
		mount(new URIRequestTargetUrlCodingStrategy("/" + SEQUENCE_PAGE_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				PageParameters params = new PageParameters();
				params.add("uri", getURI(requestParameters));
				return new BookmarkablePageRequestTarget(SequencePage.class, params);
			}
		});
		mount(new URIRequestTargetUrlCodingStrategy("/" + IMAGE_PAGE_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				PageParameters params = new PageParameters();
				params.add("uri", getURI(requestParameters));
				return new BookmarkablePageRequestTarget(ImagePage.class, params);
			}
		});
		mount(new URIRequestTargetUrlCodingStrategy("/" + THUMBNAIL_IMAGE_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				try {
					return processImage(getURI(requestParameters), 64);
				} catch (Exception e) {
					throw new WicketRuntimeException(e);
				}
			}
		});
		mount(new URIRequestTargetUrlCodingStrategy("/" + SCALED_IMAGE_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				try {
					return processImage(getURI(requestParameters), 640);
				} catch (Exception e) {
					throw new WicketRuntimeException(e);
				}
			}
		});
	}

	/*
	 * @Override protected IRequestCycleProcessor newRequestCycleProcessor() {
	 * return new WebRequestCycleProcessor() { @Override protected Page
	 * onRuntimeException(Page page, RuntimeException e) { String path =
	 * RequestCycle.get().getRequest().getPath(); if
	 * (path.startsWith(SCALED_IMAGE_PATH + "/") ||
	 * path.startsWith(THUMBNAIL_IMAGE_PATH + "/")) { // Error already logged by
	 * RequestCycle //log.error("Error occured when serving image", e); // FIXME
	 * onRuntimeException() should return an IRequestTarget
	 * RequestCycle.get().setRequestTarget(EmptyRequestTarget.getInstance());
	 * return null; } else { return super.onRuntimeException(page, e); } } }; }
	 */

	IRequestTarget processImage(String uri, int size) throws IOException, FileNotFoundException, ImageConversionException {
		File baseDirectory = new File("src/main/webapp/" + size);
		File file = new File(baseDirectory, uri);
		if (!file.getCanonicalPath().startsWith(baseDirectory.getCanonicalPath())) {
			// For security matters, we don't serve a file above the base
			// directory, can happen if user forges the URL
			throw new FileNotFoundException(file.toString());
		}
		log.debug("Serving " + file);
		File srcfile = new File("src/main/webapp/images/" + uri);
		if (!srcfile.exists()) {
			// Don't throw an exception, it means the URL is not correct
			// FIXME return a PageNotFoundRequestTarget
			return EmptyRequestTarget.getInstance();
		}
		if (!file.exists() || srcfile.lastModified() > file.lastModified()) {
			getImageConverter().scaleImage(srcfile, file, size, null);
		}
		IResourceStream fileResource = new FileResourceStream(new org.apache.wicket.util.file.File(file));
		return new ResourceStreamRequestTarget(fileResource);
	}

	public ImageConverter getImageConverter() {
		return new ImageMagickImageConverter();
	}
	
	public Settings getSettings() {
		Settings settings = new Settings();
		settings.setImageDirectoryRoot(new File("src/main/webapp/images"));
		return settings;
	}
	public static TestApplication get() {
		return (TestApplication)Application.get();
	}
}
