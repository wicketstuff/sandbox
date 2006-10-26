package contrib.wicket.cms.example;

import wicket.ISessionFactory;
import wicket.Request;
import wicket.Session;
import wicket.protocol.http.WebApplication;
import wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import wicket.spring.injection.SpringComponentInjector;
import contrib.wicket.cms.example.page.Directory;
import contrib.wicket.cms.example.page.Home;
import contrib.wicket.cms.example.page.Registration;
import contrib.wicket.cms.example.session.SecuritySession;
import contrib.wicket.cms.initializer.CMSInitializer;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;

public class CMSExampleApplication extends WebApplication {

	@Override
	protected void init() {

		// Setup Environment
		getMarkupSettings().setStripWicketTags(true);

		mountBookmarkablePage("/Registration", Registration.class);
		mount(new IndexedParamUrlCodingStrategy("/Directory", Directory.class));

		// BEGIN CMS SETUP
		ContentAuthorizationStrategy strategy = new ContentAuthorizationStrategy() {

			public boolean hasWriteAccess(Content resource) {
				// TODO: enable security access again
				// return SecuritySession.get().getMemberId() != null;
				return true;
			}

			public boolean hasReadAccess(Content resource) {
				// By default everyone can see all resources

				// Logic can be inserted here to restrict read access to a
				// resource
				return true;
			}

		};

		CMSInitializer initializer = new CMSInitializer(strategy);
		initializer.init(this);

		addComponentInstantiationListener(new SpringComponentInjector(this));
		// END CMS SETUP
	}

	public Class getHomePage() {
		return Home.class;
	}

	@Override
	protected ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession(Request request) {
				return new SecuritySession(CMSExampleApplication.this);
			}
		};
	}
}
