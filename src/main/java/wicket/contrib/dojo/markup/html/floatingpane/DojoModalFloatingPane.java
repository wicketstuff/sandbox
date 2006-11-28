package wicket.contrib.dojo.markup.html.floatingpane;

import wicket.MarkupContainer;
import wicket.ResourceReference;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.IHeaderResponse;
import static wicket.contrib.dojo.DojoIdConstants.*;

public class DojoModalFloatingPane extends DojoAbstractFloatingPane
{

	public DojoModalFloatingPane(MarkupContainer parent, String id)
	{
		super(parent, id);
		add(new DojoModalFloatingPaneHandler());
		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("templatePath", urlFor(new ResourceReference(DojoModalFloatingPane.class, "FloatingPane.htm")));
		tag.put(DOJO_TYPE, DOJO_TYPE_MODALFLOATINGPANE);
	}
	
	public void show(AjaxRequestTarget target){
		target.appendJavascript("dojo.widget.byId('" + getMarkupId() + "').show()");
	}
}
