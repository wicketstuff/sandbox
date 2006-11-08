package wicket.contrib.util.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import wicket.Component;
import wicket.behavior.AbstractHeaderContributor;
import wicket.markup.html.IHeaderContributor;


/**
 * a simple header contributor that delegates to a List of {@link VelocityContributor}
 *
 */
public class VelocityHeaderContributor extends AbstractHeaderContributor
{
	
	List<VelocityContributor> contributors = new ArrayList<VelocityContributor>();
	
	@Override
	public IHeaderContributor[] getHeaderContributors()
	{
		return contributors.toArray(new IHeaderContributor[]{});
	}

	public VelocityHeaderContributor add(VelocityContributor vc)
	{
		contributors.add(vc);
		return this;
	}
	
	
	@Override
	public void detach(Component component)
	{
		for (VelocityContributor vc : contributors)
		{
			vc.detachModel();
		}
	}
	
	@Override
	public void onRendered(Component component)
	{
		for (VelocityContributor vc : contributors)
		{
			vc.onRendered(component);
		}
	}
}
