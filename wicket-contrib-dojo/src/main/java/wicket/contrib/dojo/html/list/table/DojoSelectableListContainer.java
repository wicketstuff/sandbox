package wicket.contrib.dojo.html.list.table;

import java.util.List;

import wicket.MarkupContainer;
import wicket.WicketRuntimeException;
import static wicket.contrib.dojo.DojoIdConstants.*;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.widgets.StylingWebMarkupContainer;
import wicket.markup.ComponentTag;

/**
 * Selectable List container
 * <pre>
 * 		DojoSelectableListContainer container = new DojoSelectableListContainer(parent, "container");
 * 		DojoSelectableList list = new DojoSelectableList(container, "list"){
 * 			[...]
 * 		};
 * </pre>
 * <b>The html (wicket:id="container" in the previous example) tag should be a &lt;table&gt;</b>
 * @author Vincent Demay
 *
 */
public class DojoSelectableListContainer extends StylingWebMarkupContainer
{
	/**
	 * List of selected objects
	 */
	private List selected;

	private String enableMultipleSelect;
	private String tbodyClass;
	
	/**
	 * Construct the selectable list containre
	 * @param parent prent where DojoSelectableListContainer will be added
	 * @param id container id
	 * TODO remove childId????
	 */
	public DojoSelectableListContainer(MarkupContainer parent, String id)
	{
		super(parent, id);
		enableMultipleSelect = "true";
		tbodyClass = "scrollContent";
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.put(DOJO_TYPE, "SelectableTable");
		tag.put("enableMultipleSelect", enableMultipleSelect);
		tag.put("tbodyClass", tbodyClass);
		
		//Is they need to be configured?
		tag.put("enableAlternateRows","true");
		tag.put("rowAlternateClass", "alternateRow");
		tag.put("headClass","fixedHeader");
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		if (!"table".equals(getMarkupFragment().getTag().getName())){
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
	 * Triggered when selection change
	 * @param target ajax target
	 * @param selected List of selected item
	 */
	public void onSelection(AjaxRequestTarget target, List selected)
	{
		
	}

	/**
	 * Triggered when double click on an item
	 * @param target ajax target
	 */
	public void onChoose(AjaxRequestTarget target, Object selected)
	{
		
	}


}
