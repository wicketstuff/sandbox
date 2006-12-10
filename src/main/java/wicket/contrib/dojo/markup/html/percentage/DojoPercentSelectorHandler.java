package wicket.contrib.dojo.markup.html.percentage;

import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractRequireDojoBehavior;
import wicket.contrib.dojo.markup.html.percentage.model.PercentageRanges;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;

public class DojoPercentSelectorHandler extends AbstractRequireDojoBehavior
{

	@Override
	public void setRequire(RequireDojoLibs libs)
	{
		//DO NOTHING For the moment
		
	}

	@Override
	protected void respond(AjaxRequestTarget target)
	{
		String result = getComponent().getRequest().getParameter("json");
		((PercentageRanges)getComponent().getModelObject()).createFromJson(result);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderJavascriptReference(new ResourceReference(DojoPercentSelectorHandler.class, "PercentSelector.js"));
		if (((PercentageRanges)getComponent().getModelObject()) != null){
			String toRender = "";
			toRender += "<script language=\"JavaScript\" type=\"text/javascript\">\n";
			toRender += "function init" + getComponent().getMarkupId() + "(){\n";
			toRender += "	var json='" + ((PercentageRanges)getComponent().getModelObject()).generateJson() + "'\n";
			toRender += "	dojo.widget.byId('" + getComponent().getMarkupId() + "').setJson(json);\n";
			toRender += "}\n";
			toRender += "dojo.addOnLoad(init"+ getComponent().getMarkupId() +");\n";
			toRender += "</script>\n";
			response.renderString(toRender);
		}

	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("onValueChange", getCallbackChange());
	}
	
	protected String getCallbackChange(){
		return "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&json=' + this.getJson(), function() { }, function() { })";
	}
}
