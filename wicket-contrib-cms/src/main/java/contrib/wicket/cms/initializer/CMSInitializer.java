package contrib.wicket.cms.initializer;

import wicket.Application;
import wicket.IInitializer;
import wicket.MetaDataKey;
import wicket.protocol.http.WebApplication;
import wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import contrib.wicket.cms.page.ContentManager;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;

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
		WebApplication webApplication = (WebApplication) application;

		webApplication.setMetaData(CONTENT_AUTHORIZATION_STRATEGY_KEY,
				contentAuthorizationStrategy);

		webApplication.mount(contentManagerPath,
				new IndexedParamUrlCodingStrategy(contentManagerPath,
						ContentManager.class));
	}

	public String getContentManagerPath() {
		return contentManagerPath;
	}

	public void setContentManagerPath(String contentManagerPath) {
		this.contentManagerPath = contentManagerPath;
	}

}
