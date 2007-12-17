package wicket.contrib.gmap;

import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.util.string.JavascriptUtils;

/**
 * @author Iulian-Corneliu Costan
 */
class GMapInitializer extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private GMap gmap;

	/**
	 * Construct.
	 * 
	 * @param gmap
	 */
	public GMapInitializer(GMap gmap)
	{
		this.gmap = gmap;
	}

	/**
	 * @see org.apache.wicket.behavior.AbstractAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		StringBuffer buffer = new StringBuffer("\n//<![CDATA[\n").append("function initGMap() {\n")
				.append("if (GBrowserIsCompatible()) {\n").append("\n" + gmapDefinition()).append(
						"\n" + overlayDefinitions()).append("}\n").append("}\n").append("//]]>\n");
		response.renderJavascript(buffer.toString(), "gmap-init");
	}

	/**
	 * @see org.apache.wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest()
	{
		// nop
	}

	@Override
	protected void onComponentRendered()
	{
		Response response = getComponent().getResponse();
		StringBuffer script = new StringBuffer("initGMap();\n");
		script.append("");
		JavascriptUtils.writeJavascript(response, script, "load");
	}

	private String overlayDefinitions()
	{
		StringBuffer buffer = new StringBuffer();
		for (Overlay overlay : gmap.getOverlays())
		{
			buffer.append("map.addOverlay(" + overlay.getFactoryMethod() + "());\n");
		}
		return buffer.toString();
	}

	private String gmapDefinition()
	{
		StringBuffer buffer = new StringBuffer(
				"map = new GMap2(document.getElementById(\"map\"));\n");
		if (gmap.isSmallMapControl())
		{
			buffer.append("map.addControl(new GSmallMapControl());\n");
		}
		if (gmap.isTypeControl())
		{
			buffer.append("map.addControl(new GMapTypeControl());\n");
		}
		buffer.append("map.setCenter(").append(gmap.getCenter().toString()).append(", ")
				.append(gmap.getZoomLevel()).append(");\n");
		return buffer.toString();
	}

	public static final String ID = "gmapComponent";
}
