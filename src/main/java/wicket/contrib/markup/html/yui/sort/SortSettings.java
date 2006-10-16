package wicket.contrib.markup.html.yui.sort;

import java.io.Serializable;
import java.util.ArrayList;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;
import wicket.markup.html.PackageResourceReference;

/**
 * Allows the user to define the sort settings
 * @author cptan
 *
 */
public class SortSettings implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mode;
	private ArrayList sortList;
	private ArrayList imgStyleList= new ArrayList();
	
	private int width;
	private int height;

	public SortSettings(){
	}
	
	public static SortSettings getDefault(String mode, ArrayList sortList){
		SortSettings settings= new SortSettings();
		settings.setResources(mode, sortList);
		return settings;
	}
	
	public void setResources(String mode, ArrayList sortList){
		setMode(mode);
		setSortList(sortList);
		setImageResources(sortList);
	}

	public void setImageResources(ArrayList sortList){
		for(int i=sortList.size()-1; i>=0; i--){
			YuiImage img= (YuiImage)sortList.get(i);
			
			ResourceReference imgRR = new PackageResourceReference(SortSettings.class, img.getFileName());
				
			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight= imgInfo.getHeight();
			
			InlineStyle imgStyle =  new InlineStyle();
			imgStyle.add("background", "url("+RequestCycle.get().urlFor(imgRR)+")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");
			
			imgStyle.add("top", img.getSortTop()+"px");
			imgStyle.add("left", img.getSortLeft()+"px");
				
			imgStyleList.add(imgStyle);
			
			this.width = imgWidth;
			this.height = imgHeight;
		}
	}
	

	public ArrayList getSortList() {
		return sortList;
	}

	public void setSortList(ArrayList sortList) {
		this.sortList = sortList;
	}

	public ArrayList getImgStyleList() {
		return imgStyleList;
	}

	public void setImgStyleList(ArrayList imgStyleList) {
		this.imgStyleList = imgStyleList;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
