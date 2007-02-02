package wicket.contrib.gmap;

import wicket.Response;
import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapAjaxBehavior extends AbstractDefaultAjaxBehavior
{

	/**
	 * @see wicket.ajax.AbstractDefaultAjaxBehavior#onRenderHeadInitContribution(wicket.Response)
	 */
	@Override
	protected void onRenderHeadInitContribution(Response response)
	{
		super.onRenderHeadInitContribution(response);

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

		response.write(s);
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
