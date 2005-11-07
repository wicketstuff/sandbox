package wicket.contrib.dojo.dojofx;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.dojo.dojofx.DojoFXHandler.AppendAttributeModifier;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.model.Model;

public class FXOnMouseOverHighlighter extends DojoFXHandler
{
	
	private final String type;
	private String HTMLID;
	private String componentId;
	private RGB startColor;
	private RGB endColor;
	
	public FXOnMouseOverHighlighter(int duration, Component trigger, int toR, int toG, int toB)
	{
		super("OnMouseOver", duration, trigger);
		this.type = "b2c";
		endColor = new RGB(toR, toG, toB);
		
	}
	
	public FXOnMouseOverHighlighter(int duration, Component trigger, int startR, int startG, int startB, int endR, int endG, int endB)
	{
		super("OnMouseOver",duration, trigger);
		this.type = "c2c";
		startColor = new RGB(startR, startG, startB);
		endColor = new RGB(endR, endG, endB);
	}
	protected void renderHeadContribution(HtmlHeaderContainer container)
	{
//		String to be written to header
		String s;
		//dojo function calls for fadein/out
		String highlightInFunction;
		String highlightOutFunction;
		
		//check for type, and call dojo.fx.html so that:
		//it fades node over duration (from startOpac to endOpac) and with callback.
		//callback sets the right state variable to the present state and 
		//does mouseover checks for stability improvements.
		//the following code might look a bit abracadabra, but it works and is thouroughly stress-tested.
		if(type=="c2c")
		{
			highlightInFunction = "dojo.fx.html.colorFade(node, " + startColor.toString() + "," + endColor.toString() + ", duration, function(){"+ componentId  + "_highlighterState='highlighted';if("+componentId+"_mouseover == 0){"+componentId+"_highlight(id, duration);}});";
			highlightOutFunction = "dojo.fx.html.colorFade(node, " + endColor.toString() + "," + startColor.toString() + ", duration, function(){"+ componentId  + "_highlighterState='unhighlighted';if("+componentId+"_mouseover == 1){"+componentId+"_highlight(id, duration);}});";
		} 
		else 
		{
			highlightInFunction = "dojo.fx.html.colorFadeOut(node, " + endColor.toString() + ", duration ,0,function(){"+ componentId + "_highlighterState='highlighted';if("+componentId+"_mouseover == 0){"+componentId+"_highlight(id, duration);}});";
			highlightOutFunction = "dojo.fx.html.colorFadeOut(node, startbc, duration ,0,function(){"+ componentId + "_highlighterState='unhighlighted';if("+componentId+"_mouseover == 1){"+componentId+"_highlight(id, duration);}});";
		}
		
			
		s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
		"\t"+ componentId  + "_highlighterState = 'unhighlighted'; \n" + 
		"\t"+ componentId  + "_first = false; \n"+ 
		"\t"+ componentId  + "_mouseover = 0; \n";
				
		s = s + "\tfunction "+ componentId  + "_highlight(id, duration) { \n" +
		"\t\tif("+ componentId  + "_highlighterState!='highlighting'){\n"+
		"\t\t\tnode = document.getElementById(id);\n" +
		"\t\t\tif(!"+ componentId  + "_first){\n"+
		"\t\t\t"+ componentId  + "_first = true; \n"+
		"\t\t\t\tstartbc = dojo.html.getBackgroundColor(node);\n" +
		"\t\t\t}\n"+ 
		"\t\t\tif("+ componentId  + "_highlighterState == 'unhighlighted') \n" + 
		"\t\t\t{ \n" +
		"\t\t\t\t"+ componentId  + "_highlighterState = 'highlighting';\n" +
		"\t\t\t\t" + highlightInFunction + "\n" +
		"\t\t\t} else {\n" +
		"\t\t\t\t"+ componentId  + "_highlighterState = 'highlighting';\n" +
		"\t\t\t\t" + highlightOutFunction + "\n" +
		"\t\t\t}\n" +
		"\t\t}\n"+
		"\t}\n"; 
			
		
		s = s + "\tfunction "+ componentId  + "_setMouseOver(ismouseover){\n" + 
			"\t\tif (ismouseover == 1){\n" + 
				"\t\t\t"+ componentId  + "_mouseover = 1;\n" + 
			"\t\t}else{\n" + 
				"\t\t\t"+ componentId  + "_mouseover = 0;\n" + 
			"\t\t}\n" + 
		"\t}\n"+
		"\t</script>\n\n";
		container.getResponse().write(s);
		
		
	}

	private class RGB
	{
		private final int R;
		private final int G;
		private final int B;
		
		public RGB(int R, int G, int B)
		{
			this.R = R;
			this.G = G;
			this.B = B;
						
		}
		
		public int getR()
		{
			return R;
		}
		
		public int getG()
		{
			return G;
		}
		
		public int getB()
		{
			return B;
		}
		
		public String toString()
		{
			return "["+ R +", "+ G +", "+ B +"]";
		}
		
	}
	
	
	
	
	
	protected void onBind()
	{
		Component c = getComponent();
		this.component = (Component)c;
		this.componentId = c.getId();
		
		//create a unique HTML for the wipe component
		HTMLID = this.component.getId() + "_" + component.getPath();
		//Add ID to component, and bind effect to trigger
		
		this.component.add(new AttributeModifier("id", true, new Model(HTMLID)));
		this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model(componentId  + "_setMouseOver(1);" + componentId + "_highlight('"+ HTMLID +"', " + getDuration() + ");")));
		this.getTrigger().add(new AppendAttributeModifier("OnMouseOut",true, new Model(componentId  + "_setMouseOver(0);" + componentId + "_highlight('"+ HTMLID +"', " + getDuration() + ");")));

	}

}
