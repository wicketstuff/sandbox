/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar
 * 2006) eelco12 $ $Revision: 5004 $ $Date: 2006-03-17 20:47:08 -0800 (Fri, 17
 * Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavaScriptReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;


/**
 * @author syca
 */
class GMapClickEventBehavior extends AbstractDefaultAjaxBehavior
{
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(GMapClickEventBehavior.class);

	private GMapClickListener listener;

	private static final JavascriptResourceReference template = new JavascriptResourceReference(
			GMapPanel.class, "wicket-contrib-gmap.js");

	/**
	 * Construct.
	 * 
	 * @param listener
	 */
	public GMapClickEventBehavior(GMapClickListener listener)
	{
		this.listener = listener;
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavascriptReference(template);

		StringBuffer builder = new StringBuffer();
		// builder.append("function initClickListener(map) {\n");
		// builder.append("GEvent.addListener(map, \"click\", function (marker,
		// point) {\n\t");
		//
		// CharSequence callbackUrl = getCallbackUrl(true);
		// builder.append("var callbackUrl = '").append(callbackUrl).append(
		// "&x=' + point.x + '&y=' + point.y;\n");
		// CharSequence script =
		// generateCallbackScript("wicketAjaxGet(callbackUrl");
		// builder.append(script);
		//
		// builder.append("\n});\n");
		// builder.append("}\n");

		String map = getComponent().getMarkupId();
		CharSequence callbackUrl = getCallbackUrl();
		builder.append("addClickListener(" + map + ", '" + callbackUrl + "')");
		response.renderJavascript(builder.toString(), "add-onclick-listener");
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	protected void respond(AjaxRequestTarget target)
	{
		String x = getX();
		String y = getY();
		log.debug("executing onclick handler: " + "x=" + x + ",y=" + y);

		listener.onClick(target, new GLatLng(0, 0));

		String msg = "x=" + x + ":y=" + y;
		target.appendJavascript("alert('" + msg + "');");
	}

	private String getY()
	{
		return getComponent().getRequest().getParameter("y");
	}

	private String getX()
	{
		return getComponent().getRequest().getParameter("x");
	}

	protected void onBind()
	{
		getComponent().setOutputMarkupId(true);
	}
}
