package wicket.contrib.dojo.dojofx;


import wicket.contrib.dojo.dojofx.DojoFXHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.model.Model;
import wicket.util.value.ValueMap;
import wicket.AttributeModifier;
import wicket.Component; 

/**
 * This classacts as an AjaxHandler an can be added to components. 
 * It adds a Dojo.fx.html wiper to the class which reactes to a target component's
 * ONCLICK method. 
 * 
 * TODO: We wanted to make a generic wiper that could also react to ONMOUSEOVER/ONMOUSEOUT
 * 		but the current dojoimplementation made our version very unstable. If you want
 * 		to help with this, or hear the problems we ran into please give a yell on the mailing list.
 * TODO: Dojo.fx.html currently only supports top-down wiping, so down-top wiping and horizontal wiping
 * 		is currently not supported. We have however, requested this on the Dojo animation wishlist.
 * TODO: streamlining javascript handling: see renderHeadContribution(HtmlHeaderContainer container).
 * TODO: Due to problems with unique javascript function/variable naming (can't use componentpath for vars and function names)
 * 		wiper can theoretically not work (yet untested...) with ListItems. The only thing
 * 		unique about ListItems is their component path.... 
 * 
 * @author Marco van de Haar
 * @author Ruud Booltink
 *
 */
public class FXOnClickWiper extends DojoFXHandler
{
	
	/**
	 * true if wiping component should be displaid from beginning.
	 */
	private final boolean startDisplay;
	private String HTMLID;
	private String componentId;
	
	/**
	 * Constructor with custom startDisplay
	 * 
	 * @param trigger Component which'ONCLICK triggers teh wiping effect
	 * @param duration duration for the animation.
	 * @param startDisplay whether the wiping component starts wiped in or wiped out(wiped in means vissible)
	 */
	public FXOnClickWiper(int duration, Component trigger, boolean startDisplay)
	{
		super("onclick", duration, trigger);
		this.startDisplay = startDisplay;
		
	}

	/**
	 * Constructor with default startDisplay
	 * 
	 * @param trigger Omponent which'ONCLICK triggers teh wiping effect
	 * @param duration duration for the animation.
	 */	
	public FXOnClickWiper(int duration, Component trigger)
	{
		super("onclick", duration, trigger);
		this.startDisplay = false;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.dojo.dojofx.DojoFXHandler#renderHeadContribution(wicket.markup.html.HtmlHeaderContainer)
	 */
	public final void renderHeadContribution(HtmlHeaderContainer container)
	/*
	 * TODO: make this part a lot more efficient!
	 * every new Wiper adds a lot of javascript to the header.
	 * I'm positive that this can work a lot more efficient, but have not secceeded
	 * in doing this yet.....
	 */
	{
		String s;
		if(startDisplay)
		{
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
				"\t"+ componentId  + "_wipedOut = 0; \n";
		} else {
			s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
			"\t"+ componentId  + "_wipedOut = 1; \n";		
		}
			
			s = s + "\t"+ componentId  + "_wiping = 0; \n" +
			"\tfunction "+ componentId  + "_wipe(id, duration) { \n" +
			"\t\tif("+ componentId  + "_wiping==0){\n"+
			"\t\t\tnode = document.getElementById(id);\n" +
			"\t\t\tif("+ componentId  + "_wipedOut == 1) \n" + 
			"\t\t\t{ \n" +
			"\t\t\t\t"+ componentId  + "_wiping = 1;\n" +
			"\t\t\t\t"+ componentId  + "_wipedOut = 0;\n" +
			"\t\t\t\tdojo.fx.html.wipeIn(node, duration, function(){"+ componentId  + "_wiping=0; node.style.height='auto';});\n" +
			"\t\t\t} else {\n" +
			"\t\t\t\t"+ componentId  + "_wiping = 1;\n" +
			"\t\t\t\t"+ componentId  + "_wipedOut = 1;\n" +
			"\t\t\t\tdojo.fx.html.wipeOut(node, duration, function(){"+ componentId  + "_wiping=0; node.style.height='auto';});\n" +
			"\t\t\t}\n" +
			"\t\t}\n"+
			"\t}\n" + 
			"\t</script>\n";
		
		
		container.getResponse().write(s);
		
	}
	
	/* 
	 * @see wicket.AjaxHandler#getBodyOnloadContribution()
	 */
	protected String getBodyOnloadContribution()
	{
		//You might want to add dojo.event.connect calls here to bind animations to functions.
		//* currently however, this is done by Attributemodifiers in onBind();
		
		//return "init();";
		return null;
	}
	
	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();
		
		//create a unique HTML for the wipe component
		HTMLID = this.component.getId() + "_" + component.getPath();
		//Add ID to component, and bind effect to trigger
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		if(!startDisplay)
		{
			this.component.add(new AppendAttributeModifier("style", true, new Model("display:none")));
		}
		System.out.println("event: " + getEventName() + " component: " + this.componentId);
		this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model(componentId + "_wipe('"+ HTMLID +"', " + getDuration() + ");")));
		
	}
	
	
	
}
