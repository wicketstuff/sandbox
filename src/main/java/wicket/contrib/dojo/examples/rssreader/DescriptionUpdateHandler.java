package wicket.contrib.dojo.examples.rssreader;

import java.util.Date;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.model.Model;

public class DescriptionUpdateHandler extends DojoUpdateHandler{

	/**
	 * Method which subclasses can use to update the bound Component.
	 * 
	 * @return Component array containing Components to be updated. 
	 * NOTE: currently only supports 1 component.
	 */
	protected Component[] updateComponent()
	{
		Component[] components = new Component[1];
		components[0] = ((RSSItemPanel)getComponent()).getDPanel();
		String desc = ((RSSItemPanel)getComponent()).getDescription();
		String title = ((RSSItemPanel)getComponent()).getTitle();
		Date date = ((RSSItemPanel)getComponent()).getPublishedDate();
		String link = ((RSSItemPanel)getComponent()).getLink();
		
		DescriptionModel dmodel = new DescriptionModel(title, date, link,desc);
		((DescriptionPanel)components[0]).update(new Model(dmodel));
		return components;
	}
	/**
	 * Checks if bound component is Updatable and adds HTMLID
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		// create a unique HTML for the explode component
	
		// Add ID to component, and bind effect to trigger
		//function render_update(componentUrl,mtype, nodeId) { 
		//String onclick = "render_update('" + getCallbackUrl() + "','text/plain', '" + dPanel.getHTMLID() + "')";
		
		
		c.add(new AttributeModifier("onclick", true, new Model(){
			public java.lang.Object getObject(Component co){
			     return "javascript:"
					+ "render_update('" 
					+ getCallbackUrl() 
					+ "','text/plain', '" 
					+ ((RSSItemPanel)getComponent()).getDPanel().getHTMLID() 
					+ "','loading_node')";
			   }}));
		

	}
}
