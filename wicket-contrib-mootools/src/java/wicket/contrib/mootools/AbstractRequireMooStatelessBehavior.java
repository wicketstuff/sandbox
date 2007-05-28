package wicket.contrib.mootools;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;


/**
 * Handles all stateless components
 * <p>
 * This class inherits from {@link AbstractBehavior}
 * </p>
 * @author victori
 *
 */
public abstract class AbstractRequireMooStatelessBehavior extends AbstractBehavior {
	private static final long serialVersionUID = 1L;
	private MooDomReady domReadyFuncations = new MooDomReady();
		
	/**
	 * Place any JS code here which will be appended to @{link MooDomReady} 
	 * @return
	 */
	public abstract String mooFunction();
	
	
	/**
	 * Check to see if the component in question is mootools bindable
	 * @param c
	 * @return
	 */
	public Boolean isMoobindable(Component c) {
		return MooBase.getInstance().isMooBindable(c);
	}
	
	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(MooBase.getInstance().getMootoolsReference());
		super.renderHead(response);
	}
	
	
	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component, org.apache.wicket.markup.ComponentTag)
	 */
	public void onComponentTag(Component arg0, ComponentTag arg1) {
		super.onComponentTag(arg0, arg1);
		arg1.put("id",arg0.getMarkupId());
	}
	
	/**
	 * @see org.apache.wicket.behavior.AbstractBehavior#onRendered(org.apache.wicket.Component)
	 */
	public void onRendered(Component arg0) {
		if(domReadyFuncations.size() > 0){
			Response resp = arg0.getResponse();
			resp.write(MFXJavascriptUtils.DOM_READY_OPEN());
			Iterator i = domReadyFuncations.iterator();
			while(i.hasNext())
				resp.write((String)i.next());
			resp.write(MFXJavascriptUtils.DOM_READY_CLOSE());
		}
		super.onRendered(arg0);
	}
	
	/**
	 * Appending a MooTools JS function
	 * @param func
	 */
	public void addMooDomFunction(String func) {
		domReadyFuncations.add(func);
	}
	
	/**
	 * Array that holds MooTools JS functions to will be placed into domReady function in head.
	 * @author victori
	 *
	 */
	public class MooDomReady extends HashSet<String> {
		private static final long serialVersionUID = 1L;
	}
}
