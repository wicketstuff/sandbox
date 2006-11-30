package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.markup.ComponentTag;

public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{

	public DojoModalFloatingPane(String id)
	{
		super(id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("templatePath", urlFor(new ResourceReference(DojoModalFloatingPane.class, "FloatingPane.htm")));
		tag.put(DojoIdConstants.DOJO_TYPE, DojoIdConstants.DOJO_TYPE_MODALFLOATINGPANE);
	}
	
	public void show(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').show()");
	}
}
