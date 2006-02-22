package wicket.contrib.markup.html.tooltip;


import java.util.StringTokenizer;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.ResourceReference;
import wicket.markup.html.PackageResourceReference;
import wicket.markup.html.internal.HtmlHeaderContainer;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.model.Model;

/**
 * 
 * 
 * based on textsoft.it's multy-line html tooltip tutorial: 
 * <a href="http://www.texsoft.it/index.php?c=software&m=sw.js.htmltooltip&l=it">texsoft.it's Tooltip tutoroial</a><br/>
 * 
 * In short this is a fully customizable Javascript-HTML-Layout Wicket Tooltip.
 * What does it do? Well you make a MVOTooltip.java (My Very Own Tooltip)<br/>
 * which extends Tooltip.java, and write the corresponding MVOTooltip.html<br/> 
 * as if it were the HTML for a panel, you can make you're very own cusomized tooltip.<br/> 
 *
 * Note: Using setter methods afeter construction probably wont do much good, <br/>
 * because instance fields are used to render AttributeModifiers in the initTooltip() method.<br/>
 *
 *for usage examples see: <br/>
 *<a href="http://www.jroller.com/page/ruudmarco?entry=tooltip_tutioral_part_one">Tutorial 1: Static Tooltip</a><br/>
 *<a href="http://www.jroller.com/comments/ruudmarco/Weblog/tooltip_tutioral_part_2_dynamic">Tutorial 2: Dynamic Tooltip</a>
 * @author Marco & Ruud
 */

public class TooltipPanel extends Panel
{
	private final Component target; //component which tooltip should react to
	private final int offsetX; // X offset from target's upperleft corner
	private final int offsetY; //Y offset from target's top
	
	//z-index for HTML comopnent. Default = 2. 
	//Can be set to custom value if user's page requires tooltip to have higher z-index.
	private final int zIndex = 2;

	/**
	 * Constructor with default X and Y offset 
	 * @param id component id
	 * @param target target component bound to Tooltip
	 */
	public TooltipPanel(Component target)
	{
		//set standardId for a tooltipPanel so user does not have to
		//pass 2 Id's when creating a tooltip
		super("tooltipPanel");
		
		//default offset. 
		this.offsetX = 10;
		this.offsetY = 20;
		this.target = target;
		
		initTooltip();
	}
	
	/**
	 * Constructor with default X and Y offset and Model object 
	 * @param target target component bound to Tooltip
	 * @param model Model to set for the Panel
	 */	
	public TooltipPanel(IModel model, Component target)
	{
		//set standardId for a tooltipPanel so user does not have to
		//pass 2 Id's when creating a tooltip
		super("tooltipPanel", model);
		
		//default offset. 
		this.offsetX = 10;
		this.offsetY = 20;
		this.target = target;
		
		initTooltip();
	}
	
	/**
	 * constructor with custom x and y offsets
	 * NOTE: IE seems to take y as the offset from target's top and FF from target's bottom
	 * @param id Component id
	 * @param target Target component bound to Tooltip
	 * @param x X offset from target's upperleft corner
	 * @param y Y offset from target's upperleft corner
	 *
	 */
	public TooltipPanel(Component target, int x, int y)
	{
		//set standardId for a tooltipPanel so user does not have to
		//pass 2 Id's when creating a tooltip
		super("tooltipPanel");
		this.offsetX = x;
		this.offsetY = y;
		this.target = target;
		initTooltip();
	}
	
	/**
	 * constructor with custom x and y offsets and Model object
	 * @param model IModel the model of the component
	 * @param target Target component bound to Tooltip
	 * @param x X offset from target's upperleft corner
	 * @param y Y offset from target's upperleft corner
	 * 
	 * NOTE: IE seems to take y as the offset from target's top and FF from target's bottom
	 */
	public TooltipPanel(IModel model, Component target, int x, int y)
	{
		//set standardId for a tooltipPanel so user does not have to
		//pass 2 Id's when creating a tooltip
		super("tooltipPanel", model);
		this.offsetX = x;
		this.offsetY = y;
		this.target = target;
		
		initTooltip();
	}

    protected ResourceReference getJs() {
        return new PackageResourceReference(TooltipPanel.class, "tooltip.js");
    }
	
	/**
	 * @see wicket.markup.html.panel.Panel#renderHead(wicket.markup.html.internal.HtmlHeaderContainer)
	 */
	public void renderHead(HtmlHeaderContainer container) {
        super.renderHead(container);

        addJsReference(container, getJs());
    }

    private void addJsReference(HtmlHeaderContainer container, ResourceReference ref) {
        String url = container.getPage().urlFor(ref);
        String s =
            "\t<script type=\"text/javascript\" language='JavaScript' src=\"" + url + "\"></script>\n";
        write(container, s);
    }
    /**
     * Writes the given string to the header container.
     * @param container the header container
     * @param s the string to write
     */
    private void write(HtmlHeaderContainer container, String s) {
        container.getResponse().write(s);
    }

	
	/**
	 * @return The html id
	 */
	public String getHTMLID()
	{
		return "id" + "_" + target.getPath();
	}
	
	/**
	 * @return the target html id
	 */
	public String getTargetHTMLID()
	{
		String componentpath = removeColon(target.getPath());
		return "f_" + target.getId() + "_" + componentpath;
	}
	
	/**
	 * @return The iframe id
	 */
	public String getIFrameID()
	{
		return getHTMLID() + "_ttiframe";
	}
	
	/*
	 * removes the colons in the componentPath. In order to use in Javascript variables
	 */
	private String removeColon(String s) {
		  StringTokenizer st = new StringTokenizer(s,":",false);
		  String t="";
		  while (st.hasMoreElements()) t += st.nextElement();
		  return t;
	  }
	
	/**
	 * innitializes the tooltip
	 * to be called from constructors to avoid redundant code.
	 */
	private void initTooltip()
	{
		
		//add the javascript file
			
		 //add(new JavaScriptReference("tooltipMain", Tooltip.class, "tooltip.js"));
		
		//add id attributes for javascript identification 
		//to the tooltip the parent, and the corresponding Iframe
		add(new AttributeModifier("id" , true, new Model(getHTMLID())));
		target.add(new AttributeModifier("id", true, new Model(getTargetHTMLID())));
		
		//add necessary style to component tag
		add(new AppendAttributeModifier("style", true, new Model("visibility:hidden; z-index:5; position:absolute")));
		
		//add mousehandler functions to target
		AppendAttributeModifier onMouseOverMod = new AppendAttributeModifier("onmouseover", true, new Model("xstooltip_show('" + getHTMLID() + "', '" + getTargetHTMLID() + "', " + offsetX + ", " + offsetY + ", '"+ getIFrameID() + "');"));  
		AppendAttributeModifier onMouseOutMod = new AppendAttributeModifier("onmouseout", true, new Model("xstooltip_hide('" + getHTMLID() + "', '" + getIFrameID() + "');"));
		target.add(onMouseOverMod);
		target.add(onMouseOutMod);
	}
	
/*	protected void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag)
	{
		//super.onComponentTagBody(markupStream, openTag);
		//write tooltip unique IFRAME in order to prevent 
		//MSIE problems with windowed controlers and z-indices
		System.out.println("hier");
		replaceComponentTagBody(markupStream, openTag, "<IFRAME id='" + getIFrameID() + "' style='visibility: hidden; LEFT: 0px; POSITION: absolute; TOP: 0p' frameBorder='0' scrolling='no'></IFRAME>");
		
	}
	
	*//**
	 * @return the iframe Component corresponding to this TooltipPanel.
	 *//*
	public WebMarkupContainer getIframe()
	{
		return ((Tooltip)getParent()).getIframe();
	}*/
	
	/**
	 * @return x-offset
	 */
	public int getOffsetX()
	{
		return this.offsetX;
	}

	/**
	 * @return y-offset
	 */
	public int getOffsetY()
	{
		return this.offsetY;
	}

	/**
	 * @return z-index
	 */
	public int getZIndex()
	{
		return this.zIndex;
	}

	/**
	 * @return target Component
	 */
	public Component getTarget()
	{
		return target;
	}
	
	
	/**
	 * AttributeModifier that appends the new value to the current value if an old value
	 * exists. If it does not exist, it sets the new value.
	 * @author Ruud Booltink
	 * @author Marco van de Haar
	 */
	private final static class AppendAttributeModifier extends AttributeModifier
	{
		/**
		 * @param attribute
		 * @param addAttributeIfNotPresent
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, boolean addAttributeIfNotPresent, IModel replaceModel) {
			super(attribute, addAttributeIfNotPresent, replaceModel);
		}

		/**
		 * @param attribute
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, IModel replaceModel) {
			super(attribute, replaceModel);
		}

		/**
		 * @param attribute
		 * @param pattern
		 * @param addAttributeIfNotPresent
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, String pattern, boolean addAttributeIfNotPresent, IModel replaceModel) {
			super(attribute, pattern, addAttributeIfNotPresent, replaceModel);
		}

		/**
		 * @param attribute
		 * @param pattern
		 * @param replaceModel
		 */
		public AppendAttributeModifier(String attribute, String pattern, IModel replaceModel) {
			super(attribute, pattern, replaceModel);
		}

		protected String newValue(String currentValue, String replacementValue) {
			return (currentValue==null?"":currentValue) + replacementValue;
		}
	}

}
