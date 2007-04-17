package wicket.contrib.gmap;

import wicket.Component;
import wicket.RequestCycle;
import wicket.Response;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.html.IHeaderResponse;
import wicket.response.StringResponse;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapAjaxBehavior extends AbstractDefaultAjaxBehavior
{
	/**
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		StringBuffer s = new StringBuffer(
				"\t<script language=\"JavaScript\" type=\"text/javascript\">\n");
		s.append("\tfunction gmapRequest(componentUrl, componentId) { \n");
		s.append("\t\tfunction success() {\n");
		s.append("\t\t\tvar srcComp = wicketGet(componentId);\n");
		s.append("\t\t\tvar dstComp = wicketGet('dst'+componentId);\n");
		s.append("\t\t\tdstComp.innerHTML = srcComp.innerHTML;\n");
		// s.append("\t\t\tsrcComp.style.display = \"none\";\n");
		s.append("\t\t}\n");
		s.append("\t\tfunction failure() {\n");
		s.append("\t\t\talert('ooops!');\n");
		s.append("\t\t}\n");
		s.append("\t\twicketAjaxGet(componentUrl, success, failure)\n");
		s.append("\n\t}\n");
		s.append("\t</script>\n");
		response.renderString(s.toString());
	}

	/**
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#respond(wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void respond(AjaxRequestTarget target)
	{

		GMarkerContainer component = (GMarkerContainer)getComponent();
		component.toggleVisibility();

		target.addComponent(component);
	}

}
