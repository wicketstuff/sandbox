package org.apache.wicket.security.examples.springsecurity.security;

import org.springframework.security.SpringSecurityException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.Application;
import org.apache.wicket.security.examples.springsecurity.SpringSecureWicketApplication;

/**
 * A general purpose wrapper to authenticate a user with Swarm through Acegi. It
 * does not support multi-login. Provided as is without warranty or
 * responsibility for damage.
 *
 * @author marrink
 * @author Olger Warnier
 */
public final class SpringSecureLoginContext extends LoginContext
{
    private Authentication token;

    /**
     *
     * Constructor for logoff purposes.
     */
    public SpringSecureLoginContext()
    {

    }

    /**
     * Constructs a new LoginContext with the provided Acegi
     * AuthenticationToken.
     *
     * @param token
     *            contains credentials like username and password
     */
    public SpringSecureLoginContext(Authentication token)
    {
        this.token = token;
    }

    /**
     * @see org.apache.wicket.security.hive.authentication.LoginContext#login()
     */
    public Subject login() throws LoginException
    {
        if (token == null)
            throw new LoginException("Insufficient information to login");
        // Attempt authentication.
        try
        {
            AuthenticationManager authenticationManager = ((SpringSecureWicketApplication) Application.get()).getAuthenticationManager();
            if (authenticationManager == null)
                throw new LoginException(
                        "AuthenticationManager is not available, check if your spring config contains a property for the authenticationManager in your wicketApplication bean.");
            Authentication authResult = authenticationManager.authenticate(token);
            setAuthentication(authResult);
        }
        catch (SpringSecurityException e)
        {
            setAuthentication(null);
            throw new LoginException(e);

        }

        catch (RuntimeException e)
        {
            setAuthentication(null);
            throw new LoginException(e);
        }
        // cleanup
        token = null;
        // return result
        return new SpringSecureSubject();
    }

    /**
     * Sets the acegi authentication.
     *
     * @param authentication
     *            the authentication or null to clear
     */
    private void setAuthentication(Authentication authentication)
    {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Notify Acegi.
     *
     * @see org.apache.wicket.security.hive.authentication.LoginContext#notifyLogoff(org.apache.wicket.security.hive.authentication.Subject)
     */
    public void notifyLogoff(Subject subject)
    {
        setAuthentication(null);
    }
}
