package contrib.wicket.cms.initializer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import wicket.Application;
import wicket.IInitializer;
import wicket.MetaDataKey;
import wicket.contrib.tinymce.TinyMCEInitializer;
import wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import wicket.spring.SpringWebApplication;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.page.ContentManager;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;
import contrib.wicket.cms.service.ContentService;

public class CMSInitializer implements IInitializer {

	public static MetaDataKey CONTENT_AUTHORIZATION_STRATEGY_KEY = new MetaDataKey(
			ContentAuthorizationStrategy.class) {
	};

	ContentAuthorizationStrategy contentAuthorizationStrategy;

	String contentManagerPath = "/ContentManager";

	public CMSInitializer(
			ContentAuthorizationStrategy contentAuthorizationStrategy) {
		this.contentAuthorizationStrategy = contentAuthorizationStrategy;
	}

	public void init(Application application) {
		SpringWebApplication webApplication = (SpringWebApplication) application;

		new TinyMCEInitializer().init(webApplication);

		webApplication.setMetaData(CONTENT_AUTHORIZATION_STRATEGY_KEY,
				contentAuthorizationStrategy);

		webApplication.mount(contentManagerPath,
				new IndexedParamUrlCodingStrategy(contentManagerPath,
						ContentManager.class));

		initDatabase(webApplication);
	}

	public void initDatabase(SpringWebApplication application) {
		ContentService contentService = (ContentService) application
				.getSpringContextLocator().getSpringContext().getBean(
						ContentService.BEAN_NAME);

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
		Content rootFolder = (Content) session.get(Content.class, Content.ROOT);
		if (rootFolder == null) {
			rootFolder = new Content();
			rootFolder.setName("ROOT");
			rootFolder.setContentType((ContentType) session.load(
					ContentType.class, ContentType.FOLDER));
			session.save(rootFolder);
		}
	}

	public String getContentManagerPath() {
		return contentManagerPath;
	}

	public void setContentManagerPath(String contentManagerPath) {
		this.contentManagerPath = contentManagerPath;
	}

}
