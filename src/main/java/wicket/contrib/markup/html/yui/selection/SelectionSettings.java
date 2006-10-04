package wicket.contrib.markup.html.yui.selection;

import java.io.Serializable;

import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;
import wicket.contrib.YuiTextBox;
import wicket.ResourceReference;
import wicket.markup.html.PackageResourceReference;
import wicket.contrib.ImageResourceInfo;
import java.util.ArrayList;
import wicket.RequestCycle;
import wicket.contrib.YuiAttribute;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import wicket.contrib.YuiProperty;

public class SelectionSettings implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private YuiAttribute yuiAttribute;
	private String easing;
	private double duration;
	private String event;
	private int maxSelection;
	
	private String background;
	private int width;
	private int height;
	private int thickness;
	private String message;
	
	private ArrayList list;
	private ArrayList inlineStyleList= new ArrayList();
	
	public SelectionSettings(){
		
	}
	
	/* Image */
	public static SelectionSettings getDefault(YuiAttribute yuiAttribute, String easing, double duration, String event, int maxSelection, ArrayList list){
		SelectionSettings settings= new SelectionSettings();
		settings.setResources(yuiAttribute, easing, duration, event, maxSelection, list);
		return settings;
	}
	
	public void setResources(YuiAttribute yuiAttribute, String easing, double duration, String event, int maxSelection, ArrayList list){
		setEasing("YAHOO.util.Easing."+easing);
		setDuration(duration);
		setEvent(event);
		setMaxSelection(maxSelection);
		setImageResources(yuiAttribute, list);
	}
	
	/* Image + MaxSelection Message */
	public static SelectionSettings getDefault(YuiAttribute yuiAttribute, String easing, double duration, String event, int maxSelection, String message, ArrayList list){
		SelectionSettings settings= new SelectionSettings();
		settings.setResources(yuiAttribute, easing, duration, event, maxSelection, message, list);
		return settings;
	}
	
	public void setResources(YuiAttribute yuiAttribute, String easing, double duration, String event, int maxSelection, String message, ArrayList list){
		setEasing("YAHOO.util.Easing."+easing);
		setDuration(duration);
		setEvent(event);
		setMaxSelection(maxSelection);
		setMessage("'"+message+"'");
		setImageResources(yuiAttribute, list);
	}

	public void setImageResources(YuiAttribute yuiAttribute, ArrayList list){
		setYuiAttribute(yuiAttribute);
		setThickness(thickness);
		setList(list);
		
		for(int i=list.size()-1; i>=0; i--){
			if(list.get(i).getClass().getSimpleName().equals("YuiImage")){
				YuiImage aImage= (YuiImage) list.get(i);
				ResourceReference aResourceReference= new PackageResourceReference(Selection.class, aImage.getFileName());
				
				ImageResourceInfo backgroundInfo = new ImageResourceInfo(aResourceReference);
				int width= backgroundInfo.getWidth();
				int height= backgroundInfo.getHeight();
				
				InlineStyle aInlineStyle = new InlineStyle();
				
				aInlineStyle.add("background", "url("+ RequestCycle.get().urlFor(aResourceReference)+")");
				aInlineStyle.add("width", width + "px");
				aInlineStyle.add("height", height + "px");
				
				Map propertyMap= yuiAttribute.getPropertyMap();
				Set keySet = propertyMap.keySet();
				Iterator iter= keySet.iterator();
				while(iter.hasNext()){
					String aKey= (String)iter.next();
					YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
					aInlineStyle.add("border", "solid "+ removeQuote(aYuiProperty.getFrom()));
				}
				inlineStyleList.add(aInlineStyle);
			}
			else{
				YuiTextBox aTextBox= (YuiTextBox) list.get(i);
				
				InlineStyle aInlineStyle = new InlineStyle();
				
				aInlineStyle.add("background", aTextBox.getBackground());
				aInlineStyle.add("width", aTextBox.getWidth() + "px");
				aInlineStyle.add("height", aTextBox.getHeight() + "px");
				
				Map propertyMap= yuiAttribute.getPropertyMap();
				Set keySet = propertyMap.keySet();
				Iterator iter= keySet.iterator();
				while(iter.hasNext()){
					String aKey= (String)iter.next();
					YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
					aInlineStyle.add("border",  "solid "+ removeQuote(aYuiProperty.getFrom()));
				}
				inlineStyleList.add(aInlineStyle);
			}
		}
	}
	
	public YuiAttribute getYuiAttribute(){
		return yuiAttribute;
	}
	
	public void setYuiAttribute(YuiAttribute yuiAttribute){
		this.yuiAttribute= yuiAttribute;
	}
	
	public String getEasing(){
		return easing;
	}
	
	public void setEasing(String easing){
		this.easing= easing;
	}
	
	public double getDuration(){
		return duration;
	}
	
	public void setDuration(double duration){
		this.duration= duration;
	}
	
	public String getEvent(){
		return event;
	}
	
	public void setEvent(String event){
		this.event= event;
	}
	
	public int getMaxSelection(){
		return maxSelection;
	}
	
	public void setMaxSelection(int maxSelection){
		this.maxSelection= maxSelection;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message= message;
	}

	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background= background;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width= width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height= height;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	public void setThickness(int thickness){
		this.thickness= thickness;
	}
	
	public ArrayList getList(){
		return list;
	}
	
	public void setList(ArrayList list){
		this.list= list;
	}
	
	public ArrayList getInlineStyleList(){
		return inlineStyleList;
	}
	
	public void setInlineStyleList(ArrayList inlineStyleList){
		this.inlineStyleList= inlineStyleList;
	}
	
	public static String jsToCss(String js){
		String css="";
		for(int i=0; i< js.length(); i++){
			StringBuffer aStringBuffer= new StringBuffer("");
			aStringBuffer.append(js.charAt(i));
			String aString= aStringBuffer.toString();
			if(aString.equals(aString.toUpperCase())){
				css= css+"-"+ aString.toLowerCase();
			}
			else{
				css= css + aString; 
			}
		}
		return css;
	}
	
	public static String removeQuote(String js){
		String css="";
		for(int i=0; i< js.length(); i++){
			StringBuffer aStringBuffer= new StringBuffer("");
			aStringBuffer.append(js.charAt(i));
			String aString= aStringBuffer.toString();
			if(!aString.equals("'")){
				css= css + aString; 
			}
		}
		return css;
	}
}
	