package wicket.contrib.dojo.dojofx;

import wicket.contrib.dojo.dojofx.DojoFXHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.model.Model;
import wicket.util.value.ValueMap;
import wicket.AttributeModifier;
import wicket.Component;

/**
 * THIS CLASS IS EXPERIMENTAL, PLEASE USE FXOnClickWiper FOR NOW.
 *
 */
public class FXWiper extends DojoFXHandler
{
	
	
	public FXWiper(String eventName, int duration, Component trigger)
	{
		super(eventName, duration, trigger);
		//System.out.println("hey ik ben aangeroepe: "+ this.component.getPath());
		
	}

	/*public final void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put("id", getId());
		
	}
*/
	public final void renderHeadContribution(HtmlHeaderContainer container)
	{
		System.out.println("jahoor, daar heb je'em weer");
		
		 String s =

		"\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
		"\twipedOut = 1; \n" + 
		"\tfunction wipe(id, duration) { \n" +
		"\t\tnode = document.getElementById(id);\n" +
		"\t\tif(wipedOut == 1) \n" + 
		"\t\t{ \n" +
		"\t\t\twipedOut = 0;\n" +
		"\t\t\tdojo.fx.wipeOut(node, 400);\n" +
		"\t\t} else {\n" +
		"\t\t\twipedOut = 1;\n" +
		"\t\t\tdojo.fx.wipeIn(node, 400);\n" +
		"\t\t}\n" + 
		"\t}\n" + 
		"\t</script>\n";

		
		container.getResponse().write(s);
		
	}
	
	protected String getBodyOnloadContribution()
	{
		return "init();";
	}
	
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		//Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(component.getPath())));
		this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model("wipe('"+this.component.getPath()+"', 1400);")));
		
	}
	
	
}
