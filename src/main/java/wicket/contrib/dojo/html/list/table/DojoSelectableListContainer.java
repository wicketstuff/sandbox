package wicket.contrib.dojo.html.list.table;

import java.util.List;

import wicket.Component;
import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.link.ILinkListener;
import wicket.markup.html.list.ListView;
import wicket.model.IModel;

/**
 * Selectable List container
 * <pre>
 * 		DojoSelectableListContainer container = new DojoSelectableListContainer("container");
 * 		ListView list = new ListView("list"){
 * 			[...]
 * 		};
 * 		container.add(list);
 * </pre>
 * <b>The html (wicket:id="container" in the previous example) tag should be a &lt;table&gt;</b>
 * @author Vincent Demay
 *
 */
public class DojoSelectableListContainer extends StylingWebMarkupContainer implements ILinkListener
{
	/**
	 * List of selected objects
	 */
	private List selected;

	private String enableMultipleSelect;
	private String cssClass;
	
	/**
	 * flag to know if on choose meke a ajax request or not
	 */
	private boolean ajaxModeOnChoose;
	
	/**
	 * Allow user to set another css to overwrite the default one
	 */
	private ResourceReference overrideCssReference;
	
	//child
	private ListView listView;

	/**
	 * Construct the selectable list container
	 * @param id container id
	 */
	public DojoSelectableListContainer(String id)
	{
		this(id, null);
	}

	/**
	 * Construct the selectable list container
	 * @param id container id
	 * @param model model
	 */
	public DojoSelectableListContainer(String id, IModel model)
	{
		super(id, model);
		enableMultipleSelect = "true";
		cssClass = "dojoSelectableList";
		ajaxModeOnChoose = true;
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, "SelectableTable");
		tag.put("enableMultipleSelect", enableMultipleSelect);
		tag.put("enableAlternateRows", "true");
		tag.put("rowAlternateClass", "alternateRow");
		tag.put("class", cssClass);
	}

	protected void onBeforeRender()
	{
		super.onBeforeRender();
		if (!"table".equals(getMarkupStream().getTag().getName())){
			throw new WicketRuntimeException("Encountered tag name: '" + getMarkupStream().getTag().getName() + "', should be 'table'");
		}
	}
	
	/**
	 * Happen when dblclick and ajax enabled
	 * <b>by default ajax is enabled</b>
	 */
	public final void onLinkClicked()
	{
		int selectIndex = Integer.parseInt(getRequest().getParameter("select"));
		onNonAjaxChoose(this.listView.getList().get(this.listView.getList().size()- selectIndex - 1));
		
	}
	
	protected void onAttach()
	{
		super.onAttach();
		this.listView = getListView();
		add(new DojoSelectableListContainerHandler(listView));
	}

	/**
	 * Find the list view in children
	 * if none or more than one throw an exception!
	 * 
	 * @return the child ListView of this container
	 */
	private ListView getListView()
	{
		ListViewFinder visitor = new ListViewFinder();
		visitChildren(visitor);
		return visitor.getListView();
	}
	
	/*																									  *\
	 * ---------------------------------------------------------------------------------------------------*
	\*																								      */
	
	/**
	 * Enable or not multipleSelection on items
	 * @param enableMultipleSelect true to enable multiple selection on items, false otherwise
	 */
	public void enableMultipleSelect(boolean enableMultipleSelect){
		if (enableMultipleSelect){
			this.enableMultipleSelect = "true";
		}else{
			this.enableMultipleSelect = "false";
		}
	}
	
	/**
	 * return true if multiple selection is enabled and false otherwise
	 * @return  true if multiple selection is enabled and false otherwise
	 */
	public boolean multipleSelectEnabled(){
		if ("true".equals(enableMultipleSelect)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Get the CSS class for the table, defaults to "dojoSelectableList".
	 * It is used to render the table, selection and onMouse over.
	 * TODO : more explanation
	 * @return the table's CSS class
	 */
	public String getCssClass()
	{
		return cssClass;
	}

	/**
	 * Override the default CSS class "dojoSelectableList" for the table
	 * @param tbodyClass the new table body class
	 */
	public void setCssClass(String tbodyClass)
	{
		this.cssClass = tbodyClass;
	}

	/**
	 * return a list containing all selected items
	 * @return a list containing all selected items
	 */
	public List getSelected()
	{
		return selected;
	}

	/**
	 * change the selected List
	 * @param selected the new selected List
	 */
	public void setSelected(List selected)
	{
		this.selected = selected;
	}
	
	/**
	 * return boolean to know if ajax is enable on the choose(dblclick)
	 * @return true if ajax is active on choose
	 */
	public boolean isAjaxModeOnChoose()
	{
		return ajaxModeOnChoose;
	}
	
	/**
	 * return the used css to overwrite the default one
	 * @return the used css to overwrite the default one or null if none ios defined
	 */
	public ResourceReference getOverrideCssReference()
	{
		return overrideCssReference;
	}

	/**
	 * set a css reference to overwrite the default one
	 * @param overwriteCss  a css reference to overwrite the default one
	 */
	public void setOverrideCssReference(ResourceReference overwriteCss)
	{
		this.overrideCssReference = overwriteCss;
	}

	/**
	 * set boolean to know if ajax is enable on the choose(dblclick)
	 * @param ajaxModeOnChoose true if ajax is enable on the choose(dblclick)
	 */
	public void setAjaxModeOnChoose(boolean ajaxModeOnChoose)
	{
		this.ajaxModeOnChoose = ajaxModeOnChoose;
	}
	
	/**
	 * Triggered when selection change
	 * @param target ajax target
	 * @param selected List of selected item
	 */
	public void onSelection(AjaxRequestTarget target, List selected)
	{
		
	}

	/**
	 * Triggered when double click on a table row and ajaxOnChoose is enabled
	 * <b>by default ajax is enabled</b>
	 * @param target ajax target
	 * @param selected the object corresponding to the table row that has been choosen
	 */
	public void onChoose(AjaxRequestTarget target, Object selected)
	{
		
	}
	
	/**
	 * Triggered when double click on a table row and ajaxOnChoose is disabled
	 * <b>by default ajax is enabled</b>
	 * @param selected selected item
	 */
	public void onNonAjaxChoose(Object selected)
	{
		
	}

	/***************************************************************************/
	
	private class ListViewFinder implements IVisitor{
		private ListView listView = null;
		private int listViewNumber = 0;
		
		public Object component(Component component)
		{
			if (component instanceof wicket.markup.html.list.ListView){
				listView = (ListView)component;
				listViewNumber ++;
			}
			return CONTINUE_TRAVERSAL_BUT_DONT_GO_DEEPER;
		}
		
		public ListView getListView(){
			if (listViewNumber != 1 ){
				throw new WicketRuntimeException("A DojoSelectableListContainer should contain exactly one ListView as directly child");
			}
			//FIXME check for TR
			/*if (!"tr".equals(listView.getMarkupStream().getTag().getName())){
				throw new WicketRuntimeException("Tag name for a DojoSelectableListContinaner listView should be 'tr'");
			}*/
			return listView;
		}
	}


	
}
