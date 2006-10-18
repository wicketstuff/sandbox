package wicket.contrib.dojo.examples.rssreader;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.model.Model;

public class AddFeedHandler extends DojoUpdateHandler{

	private String url;
	
	public AddFeedHandler(String url)
	{
		this.url=url;
	}
	
	protected Component[] updateComponent() {
		Component[] components = new Component[1];
		components[0] = ((AddPanel)getComponent()).getMain();
		
		
		
		int pos = ((AddPanel)getComponent()).getIndex() - 1;
		((MainContainer)components[0]).addFeed(pos,url);
		
		return components;

	}
	
	protected void onBind()
	{
		Component c = getComponent();
		// create a unique HTML for the explode component
				
		// Add ID to component, and bind effect to trigger
		//function render_update(componentUrl,mtype, nodeId) { 
		//String onclick = "render_update('" + getCallbackUrl() + "','text/plain', '" + dPanel.getHTMLID() + "')";
		
		
		c.add(new AttributeModifier("onclick", true, new Model(){
			public java.lang.Object getObject(){
			     return "javascript:"
					+ "render_update('" 
					+ getCallbackUrl() 
					+ "','text/plain', '" 
					+ ((AddPanel)getComponent()).getMain().getHTMLID() 
					+ "','loading_node')";
			   }}));
	}

}
