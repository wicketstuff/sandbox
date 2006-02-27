package wicket.contrib.authentication.example;

import wicket.contrib.authentication.AuthenticatedWebApplication;
import wicket.contrib.authentication.AuthenticatedWebSession;
import wicket.markup.html.WebPage;

/**
 * A role-authorized, authenticated web application in just a few lines of code.
 *
 * @author Jonathan Locke
 */
public class MyAuthenticatedWebApplication extends AuthenticatedWebApplication
{
  @Override
    protected Class< ? extends AuthenticatedWebSession> getWebSessionClass()
  {
    return MyAuthenticatedWebSession.class;
  }
  
  @Override
    protected Class< ? extends WebPage> getSignInPageClass()
  {
    return MySignInPage.class;
  }
  
  /**
   * @see wicket.Application#getHomePage()
   */
  @Override
    public Class getHomePage()
  {
    return HomePage.class;
  }
}
