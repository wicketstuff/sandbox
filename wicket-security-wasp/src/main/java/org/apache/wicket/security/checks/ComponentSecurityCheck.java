package org.apache.wicket.security.checks;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.security.actions.AbstractWaspAction;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.models.ISecureModel;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;


/**
 * Basic security check for components. Tries to authorize the component and optionally
 * its {@link ISecureModel} if it exists. Note that this check does not automaticly
 * authenticate the user on a request for authorization, since this usually is already
 * done by {@link Page}s at the instantiation check.
 * Both {@link ISecureModel} and this check need to authenticate / authorize the user before an approval is given.
 * @author marrink
 */
public class ComponentSecurityCheck extends AbstractSecurityCheck
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Component component;

	private boolean checkModel;

	/**
	 * Constructs a ComponentSecurityCheck that never checks the model. Note that the
	 * check still needs to be manually added to the component.
	 * @param component the target component for this security check.
	 * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
	 * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
	 */
	public ComponentSecurityCheck(Component component)
	{
		this(component, false);
	}

	/**
	 * Constructs a ComponentSecurityCheck that optionally checks the model. Note that the
	 * check still needs to be manually added to the component.
	 * @param component the target component for this security check.
	 * @param checkSecureModelIfExists forces the model to be checked after this check is fired
	 * @see ISecureComponent#setSecurityCheck(ISecurityCheck)
	 * @see SecureComponentHelper#setSecurityCheck(Component, ISecurityCheck)
	 */
	public ComponentSecurityCheck(Component component, boolean checkSecureModelIfExists)
	{
		super();
		checkModel = checkSecureModelIfExists;
		if (component == null)
			throw new IllegalArgumentException("component must be specified.");
		this.component = component;
	}

	/**
	 * Checks if the user is authenticated for this component. if the model is also checked
	 * both the model and the component need to be authenticated before we return true.
	 * @see ISecurityCheck#isAuthenticated()
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(Component)
	 */
	public boolean isAuthenticated()
	{
		boolean result= getStrategy().isComponentAuthenticated(getComponent());
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel)getComponent().getModel()).isAuthenticated(getComponent());
		return result;
	}

	/**
	 * Returns the target component for this securitycheck.
	 * @return the component
	 */
	protected final Component getComponent()
	{
		return component;
	}

	/**
	 * Checks if the user is authorized for this component. if the model is also checked
	 * both the model and the component need to be authorized before we return true.
	 * @return true if the component (and optionally the model) are authorized, false
	 *         otherwise.
	 * @see wicket.jaas.checks.ISecurityCheck#isActionAuthorized(AbstractWaspAction)
	 * @see WaspAuthorizationStrategy#isComponentAuthorized(Component, AbstractWaspAction)
	 * @see WaspAuthorizationStrategy#isModelAuthorized(ISecureModel, Component, AbstractWaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		boolean result = getStrategy().isComponentAuthorized(getComponent(), action);
		if (result && checkSecureModel() && SecureComponentHelper.hasSecureModel(getComponent()))
			return ((ISecureModel)getComponent().getModel()).isAuthorized(getComponent(), action);
		return result;
	}

	/**
	 * Flags if we need to check the {@link ISecureModel} of a component if it exists at
	 * all.
	 * @return true if we must check the model, false otherwise.
	 */
	protected final boolean checkSecureModel()
	{
		return checkModel;
	}

}
