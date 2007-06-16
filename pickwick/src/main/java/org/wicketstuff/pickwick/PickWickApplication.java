package org.wicketstuff.pickwick;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.basic.EmptyRequestTarget;
import org.apache.wicket.request.target.basic.URIRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.request.target.component.IBookmarkablePageRequestTarget;
import org.apache.wicket.request.target.resource.ResourceStreamRequestTarget;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.backend.converter.ImageConverter;
import org.wicketstuff.pickwick.backend.converter.ImageMagickImageConverter;
import org.wicketstuff.pickwick.frontend.pages.ImagePage;
import org.wicketstuff.pickwick.frontend.pages.SequencePage;

/**
 * Wicket application for PickWick
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class PickWickApplication extends WebApplication {
	ImageUtils imageUtils;

	FileFilter imageFilter;

	FeedGenerator feedGenerator;

	Settings settings;

	public static final String SEQUENCE_PAGE_PATH = "sequence";

	public static final String SCALED_IMAGE_PATH = "scaled";

	public static final String THUMBNAIL_IMAGE_PATH = "thumbnail";

	public static final String IMAGE_PAGE_PATH = "image";

	public static final String FEED_PATH = "feed";

	private static final Log log = LogFactory.getLog(PickWickApplication.class);

	@Override
	public Class getHomePage() {
		return SequencePage.class;
	}

	public PickWickApplication() {
		settings = new Settings();
		// settings.setBaseURL(((WebRequestCycle)
		// RequestCycle.get()).getWebRequest().getHttpServletRequest()
		// .getRequestURL().toString());
		settings.setBaseURL("http://localhost:8080/");
		settings.setImageDirectoryRoot(new File("src/main/webapp/images"));

		imageFilter = new ImageFilter();
		imageUtils = new ImageUtils();
		imageUtils.setSettings(settings);
		imageUtils.setImageFilter(imageFilter);

		feedGenerator = new FeedGenerator();
		feedGenerator.setSettings(settings);
		feedGenerator.setImageUtils(imageUtils);
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

			@Override
			public boolean matches(IRequestTarget requestTarget) {
				if (requestTarget instanceof IBookmarkablePageRequestTarget) {
					return ((IBookmarkablePageRequestTarget) requestTarget).getPageClass().equals(SequencePage.class);
				}
				return false;
			}
		});
		mount(new URIRequestTargetUrlCodingStrategy("/" + IMAGE_PAGE_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				PageParameters params = new PageParameters();
				params.add("uri", getURI(requestParameters));
				return new BookmarkablePageRequestTarget(ImagePage.class, params);
			}

			@Override
			public boolean matches(IRequestTarget requestTarget) {
				if (requestTarget instanceof IBookmarkablePageRequestTarget) {
					return ((IBookmarkablePageRequestTarget) requestTarget).getPageClass().equals(ImagePage.class);
				}
				return false;
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
		mount(new URIRequestTargetUrlCodingStrategy("/" + FEED_PATH) {
			@Override
			public IRequestTarget decode(RequestParameters requestParameters) {
				try {
					final ByteArrayOutputStream pout = new ByteArrayOutputStream();
					getFeedGenerator().generate(settings.getImageDirectoryRoot(), getURI(requestParameters), pout);

					IResourceStream resource = new AbstractResourceStream() {
						public InputStream getInputStream() throws ResourceStreamNotFoundException {
							return new ByteArrayInputStream(pout.toByteArray());
						}

						public void close() throws IOException {
							pout.close();
						}

						@Override
						public long length() {
							return pout.size();
						}
					};
					return new ResourceStreamRequestTarget(resource);
				} catch (Exception e) {
					throw new WicketRuntimeException(e);
				}
			}
		});
	}

	IRequestTarget processImage(String uri, int size) throws IOException, FileNotFoundException,
			ImageConversionException {
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
		return this.settings;
	}

	public static PickWickApplication get() {
		return (PickWickApplication) Application.get();
	}

	public ImageUtils getImageUtils() {
		return this.imageUtils;
	}

	public void setImageUtils(ImageUtils imageUtils) {
		this.imageUtils = imageUtils;
	}

	/**
	 * Filters the images in the sequences
	 * 
	 * @return an instance of {@link ImageFilter}
	 */
	public FileFilter getImageFilter() {
		return this.imageFilter;
	}

	public void setImageFilter(FileFilter imageFilter) {
		this.imageFilter = imageFilter;
	}

	public FeedGenerator getFeedGenerator() {
		return this.feedGenerator;
	}

	public void setFeedGenerator(FeedGenerator feedGenerator) {
		this.feedGenerator = feedGenerator;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
