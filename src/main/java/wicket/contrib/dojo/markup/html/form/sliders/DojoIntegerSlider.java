package wicket.contrib.dojo.markup.html.form.sliders;

import wicket.ajax.AjaxEventBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;

/**
 * A Slider a get an Integer
 * <pre>
 * 		DojoIntegerSlider slider = new DojoIntegerSlider(parent, "slider1", model);
		slider.setStart(0);
		slider.setEnd(100);
 * </pre>
 * @author Vincent Demay
 *
 */
public class DojoIntegerSlider extends Panel
{
	
	private TextField value;
	private DojoSlider slider;
	private Label label;
	
	/**
	 * Construct an Integer Slider 
	 * @param id slider id
	 * @param model model
	 */
	public DojoIntegerSlider(String id, IModel model)
	{
		super( id, model);
		createSlider(model);
	}

	/**
	 * Private :  initilize the slider
	 * @param parent 
	 * @param model 
	 */
	private void createSlider(IModel model){
		value = new TextField("value", model);
		value.setOutputMarkupId(true);
		value.add(new AjaxEventBehavior("onchange"){
			protected void onEvent(AjaxRequestTarget target)
			{
				target.appendJavascript("dojo.widget.byId('" + slider.getMarkupId() + "').setValue(document.getElementById('" + value.getMarkupId() + "').value)");			
			}
		});
		this.add(value);
		
		slider = new DojoSlider("slider", value);
		slider.add(new DojoIntegerSliderHandler());
		if (getModelObject() != null){
			slider.setInitialValue(getModelObject().toString());
		}
		this.add(slider);
	}
	
	/**
	 * Set the minimum value for the slider
	 * @param start minimum value for the slider
	 */
	public void setStart(int start){
		slider.setStart(Integer.toString(start));
		setNumberSelection();
	}
	
	/**
	 * Set the maximum value for the slider
	 * @param end maximum value for the slider
	 */
	public void setEnd(int end){
		slider.setEnd(Integer.toString(end));
		setNumberSelection();
	}
	
	/**
	 * Private : calculate the number of possible values
	 */
	private void setNumberSelection(){
		if (slider.getEnd() != null && slider.getStart() != null){
			slider.setSelectableValueNumber(new Integer(Integer.parseInt(slider.getEnd()) + 1 - Integer.parseInt(slider.getStart())));
		}
	}
	
	/**
	 * Set the html slider lenght
	 * Warning : choose a lenght at least as big as the range of the slider
	 * @param length
	 */
	public void setLength(int length)
	{
		slider.setLength(length);
	}
	
	/**
	 * Set true to see the value of false otherwise
	 * @param visible true to see the value of false otherwise
	 */
	public void setValueVisible(boolean visible){
		slider.setValueVisible(visible);
	}
	
	
	
	

}
