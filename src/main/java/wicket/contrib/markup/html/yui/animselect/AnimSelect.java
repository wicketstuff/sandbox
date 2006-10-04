package wicket.contrib.markup.html.yui.animselect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.model.AbstractReadOnlyModel;
import wicket.util.collections.MiniMap;

/**
 * AnimSelect obtains the settings through the AnimSelectSettings objects.
 * Based on the settings, it generates the javascript required to produce the animation effects.
 * 
 * @author cptan
 *
 */
public class AnimSelect extends AbstractYuiPanel{
	private static final long serialVersionUID = 1L;
	private String javaScriptId;
	private final List animSelectScriptList = new Vector();
	private final ListView animSelectScriptListView;
	private final List animSelectList = new Vector();
	private final ListView animSelectListView;
	
	private String boxElementId;
	private int count=0;
	
	private String easing;
	private double duration;
	private int maxSelection;
	private String message;
	private AnimSelectGroupOption animSelectGroupOption;
	private AnimSelectSettings settings;
	
	private int width;
	private int height;
	
	private String selectedValues="";
	private boolean isHideLabel;
	
	/**
	 * Constructor 
	 * 
	 * @param id 
	 * @param settings
	 */
	public AnimSelect(String id, final AnimSelectSettings settings){
		super(id);
		
		this.boxElementId= id;
		this.easing = settings.getEasing();
		this.duration= settings.getDuration();
		this.maxSelection= settings.getMaxSelection();
		this.message= settings.getMessage();
		this.animSelectGroupOption= settings.getAnimSelectGroupOption();
		this.settings= settings;
		
		this.width= settings.getWidth();
		this.height= settings.getHeight();
		
		for(int i=0; i<animSelectGroupOption.getSize(); i++){
			AnimSelectOption animSelectOption = animSelectGroupOption.get(i);
			String value= animSelectOption.getSelectedValue();
			if(selectedValues.equals("")){
				selectedValues = "'"+ value +"'";
			}
			else{
				selectedValues = selectedValues + ",'"+ value +"'";
			}
		}
		
		this.isHideLabel= settings.getAnimSelectGroupOption().isHideLabel();
		
		Label initialization = new Label("initialization", new AbstractReadOnlyModel(){
			private static final long serialVersionUID=1L;
			public Object getObject(Component component){
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);
		
		for(int i=0; i<animSelectGroupOption.getSize(); i++){
			animSelectScriptList.add(i, animSelectGroupOption.get(i));
			animSelectList.add(i, animSelectGroupOption.get(i));
		}
	
		add(animSelectScriptListView = new ListView("animSelectScriptContainer", animSelectScriptList){	
			private static final long serialVersionUID=1L;
			private int boxId=0;
			
			public void populateItem(final ListItem listItem)
			{
				Label animSelect = new Label("animSelectScript", new AbstractReadOnlyModel(){
					private static final long serialVersionUID=1L;
					public Object getObject(Component component){
						return getAnimSelectInitializationScript(boxId++);
					}
				});
				animSelect.setEscapeModelStrings(false);
				listItem.add(animSelect);
			}
		});
		
		//This need to be defined by the user instead. How?
		add(animSelectListView = new ListView("animSelectContainer", animSelectList)
		{	
			private static final long serialVersionUID=1L;
			public void populateItem(final ListItem listItem)
			{
				AnimSelectOption aAnimSelectOption= (AnimSelectOption)listItem.getModelObject();
				ImgStyle style= new ImgStyle("imgStyle");
				listItem.add(style);
				style.add(new AnimSelectBox("defaultImg", boxElementId, count, "DefaultImg", aAnimSelectOption.getDefaultImg()));
				style.add(new AnimSelectBox("defaultImgOver", boxElementId, count, "DefaultImgOver", aAnimSelectOption.getDefaultImgOver()));
				style.add(new AnimSelectBox("selectedImg", boxElementId, count, "SelectedImg", aAnimSelectOption.getSelectedImg()));
				style.add(new AnimSelectBox("selectedImgOver", boxElementId, count, "SelectedImgOver", aAnimSelectOption.getSelectedImgOver()));
			
				if(isHideLabel == true){
					listItem.add(new Label("label", ""));
				}
				else{
					listItem.add(new Label("label", aAnimSelectOption.getSelectedValue()));
				}
				count++;
			}
		});
		
		add(new AnimSelectValue("selectedValue", boxElementId, "selectedValue"));
	}
	
	/**
	 * This will initialize init.js with values
	 * 
	 * @return
	 */
	
	protected String getJavaScriptComponentInitializationScript(){
		PackagedTextTemplate template = new PackagedTextTemplate(AnimSelect.class, "init.js");
		Map variables = new MiniMap(6);
		variables.put("javaScriptId", javaScriptId);
		variables.put("easing", "YAHOO.util.Easing."+easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animSelectGroupOption.getSize()));
		variables.put("selectedValues", selectedValues);
		template.interpolate(variables);
		return template.getString();
	}
	
	/**
	 * This will initialize the animselect.js with values
	 * 
	 * @param boxId
	 * @return
	 */
	protected String getAnimSelectInitializationScript(int boxId){
		PackagedTextTemplate template = new PackagedTextTemplate(AnimSelect.class, "animselect.js");
		Map variables = new MiniMap(7);
		variables.put("javaScriptId", javaScriptId);
		variables.put("boxId", new Integer(boxId));
		variables.put("easing", "YAHOO.util.Easing."+easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(animSelectGroupOption.getSize()));
		if(message == null || message.equals("")){
			message = "Up to "+maxSelection+" selections allowed!";
		}
		variables.put("message", message);
		template.interpolate(variables);
		return template.getString();
		
	}
	
	/**
	 * This will retrieve the value of the markup id to generate the javascript 
	 * required by init.js and animselect.js
	 * 
	 */
	protected void onAttach(){
		super.onAttach();
		javaScriptId = getMarkupId();
	}
	
	/**
	 * This will set the image style
	 * 
	 * @author cptan
	 *
	 */
	private final class ImgStyle extends FormComponent implements Serializable{
		private static final long serialVersionUID = 1L;
		public ImgStyle(final String id){
			super(id);
			add(new AttributeModifier("style", true, new AbstractReadOnlyModel(){
				private static final long serialVersionUID= 1L;
				public Object getObject(Component component){
					return "width:" + width + "px; height:" + height + "px";
				}
			}));
		}
	}
	
	/**
	 * This will add in a textfield to contain the selected value(s)
	 * 
	 * @author cptan
	 *
	 */
	private final class AnimSelectValue extends FormComponent implements Serializable{
		private static final long serialVersionUID= 1L;
		public AnimSelectValue(final String id, final String boxElementId, final String name){
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel(){
				private static final long serialVersionUID= 1L;
				public Object getObject(Component component){
					return name+"_"+boxElementId;
				}
			}));
		}	
	}
	
	/**
	 * This will set each of the 4 images style
	 * 
	 * @author cptan
	 *
	 */
	private final class AnimSelectBox extends FormComponent implements Serializable{
		private static final long serialVersionUID = 1L;
		public AnimSelectBox(final String id, final String boxElementId, final int count, final String name, YuiImage yuiImage){
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel(){
				private static final long serialVersionUID= 1L;
				public Object getObject(Component component){
					return name+count+"_"+boxElementId;
				}
			}));
			add(new AttributeModifier("style", true, new AbstractReadOnlyModel(){
				private static final long serialVersionUID= 1L;
				public Object getObject(Component component){
					if(name.equals("DefaultImg")){
						ArrayList aInlineStyleList= settings.getDefaultImgStyleList();
						InlineStyle aInlineStyle = (InlineStyle)aInlineStyleList.get(0);
						return aInlineStyle.getStyle();
					}
					else if(name.equals("DefaultImgOver")){
						ArrayList aInlineStyleList= settings.getDefaultImgOverStyleList();
						InlineStyle aInlineStyle = (InlineStyle)aInlineStyleList.get(0);
						return aInlineStyle.getStyle();
					}
					else if(name.equals("SelectedImg")){
						ArrayList aInlineStyleList= settings.getSelectedImgStyleList();
						InlineStyle aInlineStyle = (InlineStyle)aInlineStyleList.get(0);
						return aInlineStyle.getStyle();
					}
					else if(name.equals("SelectedImgOver")){
						ArrayList aInlineStyleList= settings.getSelectedImgOverStyleList();
						InlineStyle aInlineStyle = (InlineStyle)aInlineStyleList.get(0);
						return aInlineStyle.getStyle();
					}
					else
						return new String("");
				}
			}));
		}
	}
}
