package wicket.contrib.util.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wicket.Component;
import wicket.behavior.AbstractHeaderContributor;
import wicket.markup.html.IHeaderContributor;


/**
 * a simple header contributor that delegates to a List of {@link VelocityContributor}
 *
 */
public class VelocityHeaderContributor extends AbstractHeaderContributor
{
	
	List contributors = new ArrayList();
	
	public IHeaderContributor[] getHeaderContributors()
	{
		return (IHeaderContributor[]) contributors.toArray(new IHeaderContributor[]{});
	}

	public VelocityHeaderContributor add(VelocityContributor vc)
	{
		contributors.add(vc);
		return this;
	}
	
	public void detachModel(Component component)
	{
		Iterator it = contributors.iterator();
		while (it.hasNext()) {
			VelocityContributor vc = (VelocityContributor) it.next();
			vc.detachModel(component);
		}
	}
}
