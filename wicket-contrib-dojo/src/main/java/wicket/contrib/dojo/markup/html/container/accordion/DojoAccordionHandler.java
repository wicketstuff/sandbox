package wicket.contrib.dojo.markup.html.container.accordion;

import wicket.Component;
import wicket.Component.IVisitor;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.markup.html.container.AbstractDojoContainer;
import wicket.markup.html.IHeaderResponse;

/**
 * A Dojo Dialog Box Handler
 * @author vdemay
 *
 */
public class DojoAccordionHandler extends AbstractRequireDojoBehavior
{
	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		libs.add("dojo.widget.AccordionContainer");
	}

	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		AbstractDojoContainer container = (AbstractDojoContainer) getComponent();
		((DojoAccordionContainer)getComponent()).onChange();
	}

	
	/**
	 * @return javascript that will generate an ajax GET request to this
	 *         behavior *
	 * @param recordPageVersion
	 *            if true the url will be encoded to execute on the current page
	 *            version, otherwise url will be encoded to execute on the
	 *            latest page version
	 */
	protected CharSequence getCallbackScript(boolean recordPageVersion)
	{
		return getCallbackScript("wicketAjaxGet('" + getCallbackUrl(recordPageVersion) + "'", null,null);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		//add onShow event on each child in the tabContainer
		DojoAccordionContainer container = (DojoAccordionContainer)getComponent();
		RenderHeadCreator head = new RenderHeadCreator(container);
		container.visitChildren(head);
		
		response.renderString(head.getHead());
	}
	
	/**
	 * Create the head contribution
	 * @author Vincent demay
	 */
	private class RenderHeadCreator implements IVisitor{

		private String toReturn;
		private DojoAccordionContainer container;
		
		public RenderHeadCreator(DojoAccordionContainer container)
		{
			toReturn = "";
			toReturn += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
			toReturn += "function initTab" + container.getMarkupId() + "(){\n";
			
			this.container = container;
			
		}

		public Object component(Component< ? > component)
		{
			String id = component.getMarkupId();
			toReturn += "	var widget = dojo.widget.byId('" + id + "')\n";
			toReturn += "	dojo.event.connect(widget,'onShow', function(){" + getCallbackScript() + "})\n";
			
			return CONTINUE_TRAVERSAL;
		}
		
		public String getHead(){
			toReturn += "}\n";
			toReturn += "dojo.event.connect(dojo, \"loaded\", \"initTab" + container.getMarkupId() + "\");\n";
			toReturn += "</script>\n";
			return toReturn;
		}
		
	}

}
