package wicket.contrib.util.resource;

import java.util.ArrayList;
import java.util.List;

import wicket.Component;
import wicket.behavior.AbstractHeaderContributor;
import wicket.markup.html.IHeaderContributor;

/**
 * a simple header contributor that delegates to a List of
 * {@link VelocityContributor}
 */
public class VelocityHeaderContributor extends AbstractHeaderContributor
{
	/** contributors. */
	List<VelocityContributor> contributors = new ArrayList<VelocityContributor>();

	/**
	 * Add a velocity contributor.
	 * 
	 * @param vc
	 *            contributor
	 * @return This
	 */
	public VelocityHeaderContributor add(VelocityContributor vc)
	{
		contributors.add(vc);
		return this;
	}

	/**
	 * @see wicket.behavior.AbstractBehavior#detach(wicket.Component)
	 */
	@Override
	public void detach(Component component)
	{
		for (VelocityContributor vc : contributors)
		{
			vc.detachModel();
		}
	}

	/**
	 * @see wicket.behavior.AbstractHeaderContributor#getHeaderContributors()
	 */
	@Override
	public IHeaderContributor[] getHeaderContributors()
	{
		return contributors.toArray(new IHeaderContributor[] {});
	}

	/**
	 * @see wicket.behavior.AbstractBehavior#onRendered(wicket.Component)
	 */
	@Override
	public void onRendered(Component component)
	{
		for (VelocityContributor vc : contributors)
		{
			vc.onRendered(component);
		}
	}
}
