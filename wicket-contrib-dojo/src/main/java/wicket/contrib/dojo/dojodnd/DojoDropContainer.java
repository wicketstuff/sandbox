package wicket.contrib.dojo.dojodnd;

import wicket.MarkupContainer;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.markup.html.form.ImmediateCheckBox;
import wicket.markup.html.WebMarkupContainer;

/**
 * Dojo drrop container
 * <p>
 * 	A drop container is a HTML container used to define a Drop area.
 *  This area is associated with a pattern. This pattern is used to know 
 *  if a {@link DojoDragContainer} can be drag and drop on it 
 * </p>
 * <p>
 * 	<b>Sample</b>
 *  <pre>
 * package wicket.contrib.dojo.examples;
 * 
 * import wicket.PageParameters;
 * import wicket.contrib.dojo.dojodnd.DojoDragContainer;
 * import wicket.contrib.dojo.dojodnd.DojoDropContainer;
 * import wicket.markup.html.WebPage;
 * import wicket.markup.html.image.Image;
 * 
 * public class DnDShower extends WebPage {
 * 	
 * 	public DnDShower(PageParameters parameters){
 * 		DojoDropContainer dropContainer = new DojoDropContainer("dropContainer"){
 * 		
 * 			public void onDrop(DojoDragContainer container, int position) {
 * 				System.out.println("position = " + position);
 * 				System.out.println("DojoDragContainer" + container.getId());
 * 				
 * 			}
 * 		
 * 		};
 * 		add(dropContainer);
 * 		
 * 		DojoDragContainer dragContainer1 = new DojoDragContainer("dragContainer1");
 * 		DojoDragContainer dragContainer2 = new DojoDragContainer("dragContainer2");
 * 		DojoDragContainer dragContainer3 = new DojoDragContainer("dragContainer3");
 * 		add(dragContainer1);
 * 		add(dragContainer2);
 * 		add(dragContainer3);
 * 		
 * 		
 * 		DojoDragContainer dragContainer4 = new DojoDragContainer("dragContainer4");
 * 		DojoDragContainer dragContainer5 = new DojoDragContainer("dragContainer5");
 * 		dropContainer.add(dragContainer4);
 * 		dropContainer.add(dragContainer5);
 * 		
 * 		dragContainer1.add(new Image("pic1"));
 * 		dragContainer2.add(new Image("pic2"));
 * 		dragContainer3.add(new Image("pic3"));
 * 		dragContainer4.add(new Image("pic4"));
 * 		dragContainer5.add(new Image("pic5"));
 * 	}
 * }
 *  </pre>
 * </p>
 * @author <a href="http://www.demay-fr.net/blog/index.php/en">Vincent Demay</a>
 *
 */
public abstract class DojoDropContainer extends WebMarkupContainer
{

	private String dropId;
	
	/**
	 * Create a DropContainer
	 * @param id Drop container id
	 */
	public DojoDropContainer(String id)
	{
		super(id);
		this.setOutputMarkupId(true);
		//all by default
		dropId = "*";
		add(new DojoDropContainerHandler());
	}
	
	/**
	 * Set the drop pattern. The drop container only accept dragContainer with
	 * the same pattern or all id *
	 * @param pattern drop pattern
	 */
	public void setDropPattern(String pattern){
		this.dropId = pattern;
	}
	
	/**
	 * get The Drop pattern
	 * @return the drop pattern
	 */
	public String getDropPattern(){
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
		onDrop(target, (DojoDragContainer) container, position);  
	}

	/**
	 * Handler to know when a {@link DojoDragContainer} is dropped on the container
	 * @param container the {@link DojoDragContainer} dopped
	 * @param position position
	 */
	public abstract void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position);
}
