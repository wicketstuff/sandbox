package contrib.wicket.cms.example;

import javax.servlet.ServletContext;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.spring.injection.annot.AnnotSpringWebApplication;
import contrib.wicket.cms.example.page.Home;
import contrib.wicket.cms.example.session.SecuritySession;
import contrib.wicket.cms.initializer.CMSInitializer;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.security.ContentAuthorizationStrategy;

public class CMSExampleApplication extends AnnotSpringWebApplication {

	@Override
	protected void init() {

		// Setup Environment
		ServletContext servletContext = this.getWicketServlet()
				.getServletContext();
		if (servletContext.getInitParameter("deployment") != null) {
			configure("deployment");
		} else {
			configure("development");
		}

		getMarkupSettings().setStripWicketTags(true);

		// Setup Security
		// getSecuritySettings().setAuthorizationStrategy(
		// new ResearchJunctionAuthorizationStrategy());

		// Nice URLs
		// mount("/q", PackageName.forClass(Home.class));

		// BEGIN CMS SETUP
		ContentAuthorizationStrategy strategy = new ContentAuthorizationStrategy() {

			public boolean hasWriteAccess(Content resource) {
				return SecuritySession.get().getMemberId() != null;
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
		// END CMS SETUP
	}

	public Class getHomePage() {
		return Home.class;
	}

	@Override
	protected ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new SecuritySession(CMSExampleApplication.this);
			}
		};
	}
}
