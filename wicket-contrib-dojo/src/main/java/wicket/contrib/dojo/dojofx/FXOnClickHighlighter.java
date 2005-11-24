package wicket.contrib.dojo.dojofx;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.dojo.dojofx.DojoFXHandler.AppendAttributeModifier;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.model.Model;

public class FXOnClickHighlighter extends DojoFXHandler
{
	
	private final String type;
	private String HTMLID;
	private String componentId;
	private RGB startColor;
	private RGB endColor;

	
	public FXOnClickHighlighter(int duration, Component trigger, int toR, int toG, int toB)
	{
		super("onclick", duration, trigger);
		this.type = "b2c";
		endColor = new RGB(toR, toG, toB);
		
	}
	
	public FXOnClickHighlighter(int duration, Component trigger, int startR, int startG, int startB, int endR, int endG, int endB)
	{
		super("OnClick",duration, trigger);
		this.type = "c2c";
		startColor = new RGB(startR, startG, startB);
		endColor = new RGB(endR, endG, endB);
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

	/* (non-Javadoc)
	 * @see wicket.AjaxHandler#renderHeadContribution(wicket.markup.html.HtmlHeaderContainer)
	 */
	protected void renderHeadContribution(HtmlHeaderContainer container)
	{
//		String to be written to the header
		String s;
		//dojo function calls for highlight/unhighlight
		String highlightInFunction;
		String highlightOutFunction;
		
		//set the correct dojo functions for the type of highlighter
		if(type=="c2c")
		{
			highlightInFunction = "dojo.fx.html.colorFade(node, " + startColor.toString() + "," + endColor.toString() + ", duration, function(){"+ componentId  + "_highlighterState='highlighted';});";
			highlightOutFunction = "dojo.fx.html.colorFade(node, " + endColor.toString() + "," + startColor.toString() + ", duration, function(){"+ componentId  + "_highlighterState='unhighlighted';});";
		} 
		else 
		{
			highlightInFunction = "dojo.fx.html.colorFadeOut(node, " + endColor.toString() + ", duration ,0,function(){"+ componentId + "_highlighterState='highlighted';});";
			highlightOutFunction = "dojo.fx.html.colorFadeOut(node, startbc, duration ,0,function(){"+ componentId + "_highlighterState='unhighlighted';});";
		}

		s = "\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
		"\t"+ componentId  + "_highlighterState = 'unhighlighted'; \n"	+
		"\t"+ componentId  + "_first = false; \n"+ 
		"\tfunction "+ componentId  + "_highlight(id, duration) { \n" + 
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
		"\t}\n" + 
		"\t</script>\n";
		
		
		container.getResponse().write(s);
		
		
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
		this.getTrigger().add(new AppendAttributeModifier(getEventName(),true, new Model(componentId + "_highlight('"+ HTMLID +"', " + getDuration() + ");")));

	}
 
}
