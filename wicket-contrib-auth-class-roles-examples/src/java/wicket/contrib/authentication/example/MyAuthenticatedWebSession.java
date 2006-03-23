package wicket.contrib.authentication.example;

import wicket.contrib.authentication.AuthenticatedWebApplication;
import wicket.contrib.authentication.AuthenticatedWebSession;
import wicket.contrib.authorization.strategies.role.example.AdminRole;

/**
 * Authenticated session subclass
 *
 * @author Jonathan Locke
 * @author Gili Tzabari
 */
public class MyAuthenticatedWebSession extends AuthenticatedWebSession
{
  /**
   * Construct.
   *
   * @param application
   *            The application
   */
  public MyAuthenticatedWebSession(final AuthenticatedWebApplication application)
  {
    super(application);
  }

  /**
   * @see wicket.contrib.authentication.AuthenticatedWebSession#authenticate(java.lang.String,
   *      java.lang.String)
   */
  @Override
    public boolean authenticate(final String username, final String password)
  {
    // Check username and password
    return username.equals("wicket") && password.equals("wicket");
  }

  /**
   * @see wicket.contrib.authentication.AuthenticatedWebSession#getRole()
   */
  @Override
    public Class getRole()
  {
    if (isSignedIn())
    {
      // If the user is signed in, they have these roles
      return AdminRole.class;
    }
    return null;
  }
}
