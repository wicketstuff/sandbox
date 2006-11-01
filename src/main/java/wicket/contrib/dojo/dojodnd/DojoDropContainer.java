package wicket.contrib.dojo.dojodnd;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.markup.html.form.ImmediateCheckBox;
import wicket.markup.html.WebMarkupContainer;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;

/**
 * Dojo drop container
 * @author <a href="http://www.demay-fr.net/blog">Vincent Demay</a>
 *
 */
public abstract class DojoDropContainer extends WebMarkupContainer
{

	private String dropId;
	
	public DojoDropContainer(MarkupContainer parent, String id)
	{
		super(parent, id);
		this.setOutputMarkupId(true);
		//all by default
		dropId = "*";
		add(new DojoDropContainerHandler());
	}
	
	public void setDropPattern(String pattern){
		this.dropId = pattern;
	}
	
	public String getDropId(){
		return dropId;
	}
	/**
	 * Returns the name of the javascript method that will be invoked when the
	 * processing of the ajax callback is complete. The method must have the
	 * following signature: <code>function(type, data, evt)</code> where the
	 * data argument will be the value of the resouce stream provided by
	 * <code>getResponseResourceStream</code> method.
	 * 
	 * For example if we want to echo the value returned by
	 * getResponseResourceStream stream we can implement it as follows: <code>
	 * <pre>
	 *       
	 *       getJsCallbackFunctionName() {return(&quot;handleit&quot;);}
	 *       
	 *       in javascript:
	 *       
	 *       function handleit(type, data, evt) { alert(data); } 
	 * </pre>
	 * </code>
	 * 
	 * @see ImmediateCheckBox#getResponseResourceStream()
	 * @return name of the client-side javascript callback handler
	 */
	protected String getJSCallbackFunctionName()
	{
		return null;
	}
	
	/**
	 * Called after the model is updated. Use this method to e.g. update the
	 * persistent model. Does nothing by default.
	 */
	protected void onAjaxModelUpdated(AjaxRequestTarget target)
	{
		String dragSource = getRequest().getParameter("dragSource");
		int position = Integer.parseInt(getRequest().getParameter("position"));
		MarkupContainer container = getPage(); 
		String[] ids = dragSource.split("_");
		for (int i=0; i < ids.length; i++){
			container = (MarkupContainer)container.get(ids[i]);
		}
		onDrop((DojoDragContainer) container, position);  
	}

	public abstract void onDrop(DojoDragContainer container, int position);
}
