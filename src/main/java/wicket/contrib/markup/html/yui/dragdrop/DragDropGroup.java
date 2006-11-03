package wicket.contrib.markup.html.yui.dragdrop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.behavior.StringHeaderContributor;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.form.FormComponent;
import wicket.model.AbstractReadOnlyModel;

public class DragDropGroup extends WebMarkupContainer{
	private static final long serialVersionUID = 1L;
	
	private List dragableList;
	private List targetList;
	
	private int dragableHeight;
	private int dragableWidth;
	private List dragableImgList;
	
	private int targetHeight;
	private int targetWidth;
	private List targetImgList;
	
	private String javaScriptId;
	
	public DragDropGroup(String id, DragDropSettings settings, final FormComponent dragableElement, final FormComponent targetElement){
		super(id);
		this.dragableList= settings.getDragableList();
		this.dragableHeight= settings.getDragableHeight();
		this.dragableWidth= settings.getDragableWidth();
		this.dragableImgList= settings.getDragableImgList();
		
		this.targetList= settings.getTargetList();
		this.targetHeight= settings.getTargetHeight();
		this.targetWidth= settings.getTargetWidth();
		this.targetImgList= settings.getTargetImgList();
		
		if (dragableElement != null) {
			dragableElement.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject(Component component) {
							return dragableElement.getId() + "_" + javaScriptId;
						}
					}));
		}
		add(dragableElement);
		
		if (targetElement != null) {
			targetElement.add(new AttributeModifier("id", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject(Component component) {
							return targetElement.getId() + "_" + javaScriptId;
						}
					}));
		}
		add(targetElement);
	}
	
	protected void onAttach() {
		super.onAttach();
		javaScriptId = getMarkupId();

		String js = "\n<script type=\"text/javascript\">"
				+ getJavaScriptComponentInitializationScript()
				+ "\n</script>\n";
		add(new StringHeaderContributor(js));
	}
	
	protected String getJavaScriptComponentInitializationScript() {
		//targetSlot 	[empty, empty, empty, empty];
		String targetSlot="";
		for(int i=0; i<targetList.size(); i++){
			if(targetSlot.equals("") || targetSlot == ""){
				targetSlot = "empty";
			}
			else{
				targetSlot= targetSlot +", empty";
			}
		}
			
		//targetSlotId 	["t1","t2","t3","t4"];
		String targetSlotId="";
		for(int i=0; i<targetList.size(); i++){
			TargetSlot target = (TargetSlot)targetList.get(i);
			if(targetSlotId.equals("") || targetSlotId == ""){
				targetSlotId = target.getSlot().getDesc();
			}
			else{
				targetSlotId= targetSlotId+", "+ target.getSlot().getDesc();
			}
		}
		
		//dragSlot 		["p1", "p2", "p3", "p4"];
		String dragSlot="";
		for(int i=0; i<dragableList.size(); i++){
			DragableSlot dragable = (DragableSlot) dragableList.get(i);
			if(dragSlot.equals("") || dragSlot == ""){
				dragSlot= dragable.getImage().getDesc();
			}
			else{
				dragSlot= dragSlot +", " + dragable.getImage().getDesc();
			}
		}
		
		//dragSlotId 	["b1", "b2", "b3", "b4"];
		String dragSlotId="";
		for(int i=0; i<dragableList.size(); i++){
			DragableSlot dragable = (DragableSlot) dragableList.get(i);
			if(dragSlotId.equals("") || dragSlotId == ""){
				dragSlotId= dragable.getSlot().getDesc();
			}
			else{
				dragSlotId= dragSlotId +", " + dragable.getSlot().getDesc();
			}
		}
		
		PackagedTextTemplate template = new PackagedTextTemplate(DragDropGroup.class, "init.js");
		Map variables = new HashMap(5);
		variables.put("javaScriptId", javaScriptId);
		variables.put("targetSlot", targetSlot);
		variables.put("targetSlotId", targetSlotId);
		variables.put("dragSlot", dragSlot);
		variables.put("dragSlotId", dragSlotId);
		template.interpolate(variables);
		return template.getString();
	}
	
}
