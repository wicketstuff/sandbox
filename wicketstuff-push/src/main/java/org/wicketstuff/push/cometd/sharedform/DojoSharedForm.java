package org.wicketstuff.push.cometd.sharedform;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * <b>WARNING : This component is a prototype, Do NOT use it. It is still internal</b><br/>
 * {@link DojoSharedForm} uses Cometd to make it usable you should use an other servlet named cometd:
 * <pre>
 * 	&lt;servlet>
 * 		&lt;servlet-name>cometd&lt;/servlet-name>
 *	    &lt;servlet-class>org.mortbay.cometd.CometdServlet&lt;/servlet-class>
 *	    &lt;init-param>
 *	      &lt;param-name>timeout&lt;/param-name>
 *	      &lt;param-value>15000&lt;/param-value>
 *	    &lt;/init-param>
 *	    &lt;init-param>
 *	      &lt;param-name>multi-timeout&lt;/param-name>
 *	      &lt;param-value>1500&lt;/param-value>
 *	    &lt;/init-param>
 *	    &lt;init-param>
 *	      &lt;param-name>verbose&lt;/param-name>
 *	      &lt;param-value>true&lt;/param-value>
 *	    &lt;/init-param>
 *	    &lt;load-on-startup>1</load-on-startup>
 *	  &lt;/servlet>
 *
 *
 * 	  &lt;servlet-mapping>
 *	    &lt;servlet-name>cometd&lt;/servlet-name>
 *	    &lt;url-pattern>/cometd/*&lt;/url-pattern>
 *	  &lt;/servlet-mapping>
 * </pre>
 *
 * see http://cometd.org
 *
 * @author Vincent demay
 *
 */
public class DojoSharedForm extends Form {
  private static final long serialVersionUID = 1L;



  public DojoSharedForm(final String id) {
		this(id,null);
		setOutputMarkupId(true);
		add(new DojoSharedFormBehavior("/sharedForm"));
	}

	public DojoSharedForm(final String id, final IModel model) {
		super(id, model);
	}

	public static String getSharedScriptSender(){
		return "sendSharedForm()";
	}

  protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("dojoType", "Form");
	}

	// msparer: deprecated method made final in current wicket version
//	protected void onAttach() {
//		super.onAttach();
//		visitChildren(new OnChangeAppennerVisitor());
//	}


	// msparer: suggested by wicket to user this method instead of onAttach
  protected void onBeforeRender() {
		super.onBeforeRender();
		visitChildren(new OnChangeAppennerVisitor());
	}



	protected class OnChangeAppennerVisitor implements IVisitor{

		public Object component(final Component component) {
			component.add(new AttributeAppender("onchange",true,new Model(DojoSharedForm.getSharedScriptSender()), ";"));
			return IVisitor.CONTINUE_TRAVERSAL;
		}

	}

}
