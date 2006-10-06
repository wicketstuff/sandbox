package wicket.contrib.markup.html.yui.animselect;

import java.io.Serializable;
import java.util.ArrayList;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;
import wicket.markup.html.PackageResourceReference;

/**
 * Allows the user to define the anim select settings
 * @author cptan
 *
 */
public class AnimSelectSettings implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String easing;
	private double duration;
	private int maxSelection;
	private String message;
	private ArrayList animSelectList;
	
	
	private ArrayList defaultImgStyleList= new ArrayList();
	private ArrayList defaultImgOverStyleList= new ArrayList();
	private ArrayList selectedImgStyleList= new ArrayList();
	private ArrayList selectedImgOverStyleList= new ArrayList();
	
	private int width;
	private int height;
	
	/**
	 * Constructor
	 *
	 */
	public AnimSelectSettings(){
	}
	
	/**
	 * Get the default settings
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param animSelectGroupOption
	 * @return
	 */
	public static AnimSelectSettings getDefault(String easing, double duration, int maxSelection, ArrayList animSelectList){
		AnimSelectSettings settings= new AnimSelectSettings();
		settings.setResources(easing, duration, maxSelection, animSelectList);
		return settings;
	}
	
	/**
	 * Set the resources
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param animSelectGroupOption
	 */
	public void setResources(String easing, double duration,int maxSelection, ArrayList animSelectList){
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setAnimSelectList(animSelectList);
		setImageResources(animSelectList);
	}
	
	/**
	 * Get the default settings 
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param message
	 * @param animSelectGroupOption
	 * @return
	 */
	public static AnimSelectSettings getDefault(String easing, double duration, int maxSelection, String message, ArrayList animSelectList){
		AnimSelectSettings settings= new AnimSelectSettings();
		settings.setResources(easing, duration, maxSelection, message, animSelectList);
		return settings;
	}
	
	/**
	 * Set the resources
	 * 
	 * @param easing
	 * @param duration
	 * @param maxSelection
	 * @param message
	 * @param animSelectGroupOption
	 */
	public void setResources(String easing, double duration, int maxSelection, String message, ArrayList animSelectList){
		setEasing(easing);
		setDuration(duration);
		setMaxSelection(maxSelection);
		setMessage(message);
		setAnimSelectList(animSelectList);
		setImageResources(animSelectList);
	}
	/**
	 * Set the image resources
	 * 
	 * @param animSelectGroupOption
	 */
	public void setImageResources(ArrayList animSelectList){
		for(int i=animSelectList.size()-1; i>=0; i--){
			YuiImage defaultImg= ((AnimSelectOption)animSelectList.get(i)).getDefaultImg();
			YuiImage defaultImgOver= ((AnimSelectOption)animSelectList.get(i)).getDefaultImgOver();
			YuiImage selectedImg= ((AnimSelectOption)animSelectList.get(i)).getSelectedImg();
			YuiImage selectedImgOver= ((AnimSelectOption)animSelectList.get(i)).getSelectedImgOver();
			
			ResourceReference defaultImgRR = new PackageResourceReference(AnimSelect.class, defaultImg.getFileName());
			ResourceReference defaultImgOverRR = new PackageResourceReference(AnimSelect.class, defaultImgOver.getFileName());
			ResourceReference selectedImgRR = new PackageResourceReference(AnimSelect.class, selectedImg.getFileName());
			ResourceReference selectedImgOverRR = new PackageResourceReference(AnimSelect.class, selectedImgOver.getFileName());
				
			ImageResourceInfo defaultImgInfo = new ImageResourceInfo(defaultImgRR);
			int defaultImgWidth = defaultImgInfo.getWidth();
			int defaultImgHeight= defaultImgInfo.getHeight();
			ImageResourceInfo defaultImgOverInfo = new ImageResourceInfo(defaultImgOverRR);
			int defaultImgOverWidth = defaultImgOverInfo.getWidth();
			int defaultImgOverHeight= defaultImgOverInfo.getHeight();
			ImageResourceInfo selectedImgInfo = new ImageResourceInfo(selectedImgRR);
			int selectedImgWidth = selectedImgInfo.getWidth();
			int selectedImgHeight= selectedImgInfo.getHeight();
			ImageResourceInfo selectedImgOverInfo = new ImageResourceInfo(selectedImgOverRR);
			int selectedImgOverWidth = selectedImgOverInfo.getWidth();
			int selectedImgOverHeight= selectedImgOverInfo.getHeight();
			
			InlineStyle defaultImgStyle =  new InlineStyle();
			defaultImgStyle.add("background", "url("+RequestCycle.get().urlFor(defaultImgRR)+")");
			defaultImgStyle.add("width", defaultImgWidth + "px");
			defaultImgStyle.add("height", defaultImgHeight + "px");
			
			InlineStyle defaultImgOverStyle =  new InlineStyle();
			defaultImgOverStyle.add("background", "url("+RequestCycle.get().urlFor(defaultImgOverRR)+")");
			defaultImgOverStyle.add("width", defaultImgOverWidth + "px");
			defaultImgOverStyle.add("height", defaultImgOverHeight + "px");
			
			InlineStyle selectedImgStyle =  new InlineStyle();
			selectedImgStyle.add("background", "url("+RequestCycle.get().urlFor(selectedImgRR)+")");
			selectedImgStyle.add("width", selectedImgWidth + "px");
			selectedImgStyle.add("height", selectedImgHeight + "px");
			
			InlineStyle selectedImgOverStyle =  new InlineStyle();
			selectedImgOverStyle.add("background", "url("+RequestCycle.get().urlFor(selectedImgOverRR)+")");
			selectedImgOverStyle.add("width", selectedImgOverWidth + "px");
			selectedImgOverStyle.add("height", selectedImgOverHeight + "px");
				
			defaultImgStyleList.add(defaultImgStyle);
			defaultImgOverStyleList.add(defaultImgOverStyle);
			selectedImgStyleList.add(selectedImgStyle);
			selectedImgOverStyleList.add(selectedImgOverStyle);
			
			this.width = defaultImgWidth;
			this.height = defaultImgHeight;
		}
	}
	
	/**
	 * Set the AnimSelectGroupOption
	 * 
	 * @return
	 */
	public ArrayList getAnimSelectList() {
		return animSelectList;
	}

	/**
	 * Set the AnimSelectGroupOption
	 * 
	 * @param animSelectGroupOption
	 */
	public void setAnimSelectList(ArrayList animSelectList) {
		this.animSelectList = animSelectList;
	}

	/**
	 * Get the duration
	 * 
	 * @return
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Set the duration
	 * 
	 * @param duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Get the easing effect
	 * 
	 * @return
	 */
	public String getEasing() {
		return easing;
	}

	/**
	 * Set the easing effects
	 * 
	 * @param easing
	 */
	public void setEasing(String easing) {
		this.easing = easing;
	}

	/**
	 * Get the maximum selection allowed
	 * 
	 * @return
	 */
	public int getMaxSelection() {
		return maxSelection;
	}

	/**
	 * Set the max selection allowed
	 * 
	 * @param maxSelection
	 */
	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	/**
	 * Get the message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the default mouseover image style
	 * 
	 * @return
	 */
	public ArrayList getDefaultImgOverStyleList() {
		return defaultImgOverStyleList;
	}

	/**
	 * Set the default mouseover image style
	 * 
	 * @param defaultImgOverStyleList
	 */
	public void setDefaultImgOverStyleList(ArrayList defaultImgOverStyleList) {
		this.defaultImgOverStyleList = defaultImgOverStyleList;
	}

	/**
	 * Get the default image style
	 * 
	 * @return
	 */
	public ArrayList getDefaultImgStyleList() {
		return defaultImgStyleList;
	}

	/**
	 * Set the default image styles
	 * 
	 * @param defaultImgStyleList
	 */
	public void setDefaultImgStyleList(ArrayList defaultImgStyleList) {
		this.defaultImgStyleList = defaultImgStyleList;
	}

	/**
	 * Get the selected mouseover image styles
	 * 
	 * @return
	 */
	public ArrayList getSelectedImgOverStyleList() {
		return selectedImgOverStyleList;
	}

	/**
	 * Set the selected mouseover image styles
	 * @param selectedImgOverStyleList
	 */
	public void setSelectedImgOverStyleList(ArrayList selectedImgOverStyleList) {
		this.selectedImgOverStyleList = selectedImgOverStyleList;
	}

	/**
	 * Get the selected image styles
	 * 
	 * @return
	 */
	public ArrayList getSelectedImgStyleList() {
		return selectedImgStyleList;
	}

	/**
	 * Set the selected image styles
	 * 
	 * @param selectedImgStyleList
	 */
	public void setSelectedImgStyleList(ArrayList selectedImgStyleList) {
		this.selectedImgStyleList = selectedImgStyleList;
	}

	/**
	 * Get the height
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the height 
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Get the width
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set the width
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
