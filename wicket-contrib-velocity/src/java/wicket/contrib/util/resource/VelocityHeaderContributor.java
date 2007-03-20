package wicket.contrib.util.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.Component;
import wicket.behavior.AbstractHeaderContributor;
import wicket.markup.html.IHeaderContributor;

/**
 * a simple header contributor that delegates to a List of
 * {@link VelocityContributor}
 * 
 */
public class VelocityHeaderContributor extends AbstractHeaderContributor
{

	List contributors = new ArrayList();

	/**
	 * Adds a contributor.
	 * 
	 * @param velocityContributor
	 * @return This for chaining
	 */
	public VelocityHeaderContributor add(VelocityContributor velocityContributor)
	{
		contributors.add(velocityContributor);
		return this;
	}

	/**
	 * @see wicket.behavior.AbstractBehavior#detachModel(wicket.Component)
	 */
	public void detachModel(Component component)
	{
		Iterator it = contributors.iterator();
		while (it.hasNext())
		{
			VelocityContributor vc = (VelocityContributor) it.next();
			vc.detachModel(component);
		}
	}

	/**
	 * @see wicket.behavior.AbstractHeaderContributor#getHeaderContributors()
	 */
	public IHeaderContributor[] getHeaderContributors()
	{
		return (IHeaderContributor[]) contributors.toArray(new IHeaderContributor[] {});
	}
}
