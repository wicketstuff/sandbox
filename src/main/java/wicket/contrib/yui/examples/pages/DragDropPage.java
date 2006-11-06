package wicket.contrib.yui.examples.pages;

import java.util.ArrayList;
import java.util.List;

import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.dragdrop.DragDropGroup;
import wicket.contrib.markup.html.yui.dragdrop.DragDropSettings;
import wicket.contrib.markup.html.yui.dragdrop.DragableSlot;
import wicket.contrib.markup.html.yui.dragdrop.TargetSlot;
import wicket.contrib.yui.examples.WicketExamplePage;
import wicket.markup.html.form.TextField;

/**
 * This class demostrates how you can use the wicket.contrib.markup.html.yui.dragDrop package
 * to selection and sorting of images. 
 * <p>
 * 
 * @author cptan
 *
 */
public class DragDropPage extends WicketExamplePage {
	
	/**
	 * Defines a DragDropPage object
	 *
	 */
	public DragDropPage() {
	
		//Define the dragable slot with the group name
		YuiImage dSlotA = new YuiImage("style/yellow.bmp", "dragable");
		YuiImage dSlotB = new YuiImage("style/yellow.bmp", "dragable");
		YuiImage dSlotC = new YuiImage("style/yellow.bmp", "dragable");
		
		//Define the image that is used to be swapped between the target slot and the dragable slot
		YuiImage imageA = new YuiImage("style/blue.bmp", "blue");
		YuiImage imageB = new YuiImage("style/green.bmp", "green");
		YuiImage imageC = new YuiImage("style/pink.bmp", "pink");
		
		//Define the position of the dragable slot
		DragableSlot dragableA = new DragableSlot(dSlotA, imageA, 350, 164);
		DragableSlot dragableB = new DragableSlot(dSlotB, imageB, 350, 238);
		DragableSlot dragableC = new DragableSlot(dSlotC, imageC, 350, 312);
		
		//Define the target slot with the group name
		YuiImage tSlotA= new YuiImage("style/yellow.bmp", "target");
		YuiImage tSlotB= new YuiImage("style/yellow.bmp", "target");
		YuiImage tSlotC= new YuiImage("style/yellow.bmp", "target");
		
		//Define the position of the target slot
		TargetSlot targetA = new TargetSlot(tSlotA, 110, 164);
		TargetSlot targetB = new TargetSlot(tSlotB, 110, 238);
		TargetSlot targetC = new TargetSlot(tSlotC, 110, 312);

		//Store the dragable slots in a List
		List dList= new ArrayList();
		dList.add(dSlotA);
		dList.add(dSlotB);
		dList.add(dSlotC);
		
		//Store the target slots in a List
		List tList= new ArrayList();
		tList.add(tSlotA);
		tList.add(tSlotB);
		tList.add(tSlotC);
		
		DragDropSettings settings= DragDropSettings.getDefault(dList, tList);
		
		//Linking the two lists together?? 
		//Should the linking be done this way?
		//The SlotGroup will implements IWebContainer and add the list separately?
		//How to display them using the SlotGroup, ListView can only use ArrayList using the same wicket id(s)
		TextField dragableElement = new TextField("dragableElement");
		TextField targetElement = new TextField("targetElement");
		DragDropGroup slotGroup = new DragDropGroup("dragDrop", settings, dragableElement, targetElement);
		add(slotGroup);
	}
}