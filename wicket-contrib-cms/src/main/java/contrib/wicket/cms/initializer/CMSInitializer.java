package contrib.wicket.cms.initializer;

import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import wicket.Application;
import wicket.IInitializer;
import wicket.MetaDataKey;
import wicket.protocol.http.WebApplication;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;
import contrib.wicket.cms.service.ContentService;

public class CMSInitializer implements IInitializer {

	public static MetaDataKey CONTENT_AUTHORIZATION_STRATEGY_KEY = new MetaDataKey(
			ContentAuthorizationStrategy.class) {
	};

	ContentAuthorizationStrategy contentAuthorizationStrategy;

	public CMSInitializer(
			ContentAuthorizationStrategy contentAuthorizationStrategy) {
		this.contentAuthorizationStrategy = contentAuthorizationStrategy;
	}

	public void init(Application application) {

		WebApplication webApplication = (WebApplication) application;

		// new TinyMCEInitializer().init(webApplication);

		webApplication.setMetaData(CONTENT_AUTHORIZATION_STRATEGY_KEY,
				contentAuthorizationStrategy);

		initDatabase(webApplication);
	}

	public void initDatabase(WebApplication webApplication) {
		ServletContext sc = webApplication.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);

		ContentService contentService = (ContentService) ac
				.getBean(ContentService.BEAN_NAME);

		Session session = contentService.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		createContentType(session, ContentType.FOLDER, "Folder");
		createContentType(session, ContentType.UNKNOWN, "Unknown");
		createContentType(session, ContentType.TEXT, "Text");
		createContentType(session, ContentType.HTML, "HTML");
		createContentType(session, ContentType.GIF, "GIF");
		createContentType(session, ContentType.JPG, "JPG");
		createContentType(session, ContentType.PNG, "PNG");
		createContentType(session, ContentType.FLASH, "Flash");
		createContentType(session, ContentType.PDF, "PDF");

		session.flush();
		createRootFolder(session);

		tx.commit();
		session.close();

	}

	public void createContentType(Session session, Integer contentTypeId,
			String name) {
		ContentType contentType = (ContentType) session.get(ContentType.class,
				contentTypeId);
		if (contentType == null) {
			contentType = new ContentType();
			contentType.setId(contentTypeId);
			contentType.setName(name);
			session.save(contentType);
		}
	}

	public void createRootFolder(Session session) {
		try {
			Content rootFolder = (Content) session.get(Content.class,
					Content.ROOT);
			if (rootFolder == null) {
				rootFolder = new Content();
				rootFolder.setName("ROOT");
				rootFolder.setContentType((ContentType) session.load(
						ContentType.class, ContentType.FOLDER));
				session.save(rootFolder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
