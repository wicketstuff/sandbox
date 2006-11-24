package wicket.contrib.dojo.html.list.table;

import java.util.List;

import wicket.ResourceReference;
import wicket.WicketRuntimeException;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.DojoIdConstants;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.markup.ComponentTag;
import wicket.markup.html.link.ILinkListener;
import wicket.model.IModel;

/**
 * Selectable List container
 * <pre>
 * 		DojoSelectableListContainer container = new DojoSelectableListContainer("container");
 * 		DojoSelectableList list = new DojoSelectableList("list"){
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
	private String tbodyClass;
	
	/**
	 * flag to know if on choose meke a ajax request or not
	 */
	private boolean ajaxModeOnChoose;
	
	/**
	 * Allow user to set another css to overwrite the default one
	 */
	private ResourceReference overwriteCss;

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
		tbodyClass = "scrollContent";
		ajaxModeOnChoose = true;
	}

	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DojoIdConstants.DOJO_TYPE, "SelectableTable");
		tag.put("enableMultipleSelect", enableMultipleSelect);
		tag.put("tbodyClass", tbodyClass);
		
		//Is they need to be configured?
		tag.put("enableAlternateRows","true");
		tag.put("rowAlternateClass", "alternateRow");
		tag.put("headClass","fixedHeader");
	}

	protected void onBeforeRender()
	{
		super.onBeforeRender();
		if (!"table".equals(getMarkupStream().getTag().getName())){
			throw new WicketRuntimeException("Tag name for a DojoSelectableListContainer should be 'table'");
		}
	}
	
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
	 * Get the class used to render table, selection and onMouse over
	 * TODO : more explanation
	 * @return the body table class
	 */
	public String getTbodyClass()
	{
		return tbodyClass;
	}

	/**
	 * Change the default table body class
	 * @param tbodyClass the new table body class
	 */
	public void setTbodyClass(String tbodyClass)
	{
		this.tbodyClass = tbodyClass;
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
	public ResourceReference getOverwriteCss()
	{
		return overwriteCss;
	}

	/**
	 * set a css reference to overwrite the default one
	 * @param overwriteCss  a css reference to overwrite the default one
	 */
	public void setOverwriteCss(ResourceReference overwriteCss)
	{
		this.overwriteCss = overwriteCss;
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
	 * Triggered when double click on an item and ajaxOnChoose is enabled
	 * <b>by default ajax is enabled</b>
	 * @param target ajax target
	 */
	public void onChoose(AjaxRequestTarget target, Object selected)
	{
		
	}

	/**
	 * Triggered when double click on an item and ajaxOnChoose is disabled
	 * <b>by default ajax is enabled</b>
	 */
	public void onLinkClicked()
	{
	}
}
