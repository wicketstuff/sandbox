package wicket.contrib.markup.html.yui.dragdrop;

import java.io.Serializable;
import java.util.List;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;

/**
 * A SortSettings allows the user to define the sort settings
 * 
 * @author cptan
 * 
 */
public class DragDropSettings implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List dragableList;
	private List targetList;
	
	private List dragableImgList;
	private int dragableWidth;
	private int dragableHeight;
	
	private List targetImgList;
	private int targetWidth;
	private int targetHeight;

	public DragDropSettings() {
	}


	public static DragDropSettings getDefault(List dragableList, List targetList) {
		DragDropSettings settings = new DragDropSettings();
		settings.setResources(dragableList, targetList);
		return settings;
	}
	
	public void setResources(List dragableList, List targetList) {
		setDragableList(dragableList);
		setTargetList(targetList);
		setDragableImageResources(dragableList);
		setTargetImageResources(targetList);
	}
	
	public void setDragableImageResources(List dragableList){
		for (int i = 0; i < dragableList.size(); i++) {
			YuiImage img = (YuiImage) dragableList.get(i);

			ResourceReference imgRR = new ResourceReference(DragDropSettings.class,img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			InlineStyle imgStyle = new InlineStyle();
			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getTop() + "px");
			imgStyle.add("left", img.getLeft() + "px");

			dragableImgList.add(imgStyle);

			this.dragableWidth = imgWidth;
			this.dragableHeight = imgHeight;
		}
	}

	public void setTargetImageResources(List targetList) {
		for (int i = 0; i < targetList.size(); i++) {
			YuiImage img = (YuiImage) targetList.get(i);

			ResourceReference imgRR = new ResourceReference(DragDropSettings.class,img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			InlineStyle imgStyle = new InlineStyle();
			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getTop() + "px");
			imgStyle.add("left", img.getLeft() + "px");

			targetImgList.add(imgStyle);

			this.targetWidth = imgWidth;
			this.targetHeight = imgHeight;
		}
	}


	/**
	 * @return the dragableList
	 */
	public List getDragableList() {
		return dragableList;
	}


	/**
	 * @param dragableList the dragableList to set
	 */
	public void setDragableList(List dragableList) {
		this.dragableList = dragableList;
	}


	/**
	 * @return the targetList
	 */
	public List getTargetList() {
		return targetList;
	}


	/**
	 * @param targetList the targetList to set
	 */
	public void setTargetList(List targetList) {
		this.targetList = targetList;
	}


	/**
	 * @return the dragableHeight
	 */
	public int getDragableHeight() {
		return dragableHeight;
	}


	/**
	 * @param dragableHeight the dragableHeight to set
	 */
	public void setDragableHeight(int dragableHeight) {
		this.dragableHeight = dragableHeight;
	}


	/**
	 * @return the dragableImgList
	 */
	public List getDragableImgList() {
		return dragableImgList;
	}


	/**
	 * @param dragableImgList the dragableImgList to set
	 */
	public void setDragableImgList(List dragableImgList) {
		this.dragableImgList = dragableImgList;
	}


	/**
	 * @return the dragableWidth
	 */
	public int getDragableWidth() {
		return dragableWidth;
	}


	/**
	 * @param dragableWidth the dragableWidth to set
	 */
	public void setDragableWidth(int dragableWidth) {
		this.dragableWidth = dragableWidth;
	}


	/**
	 * @return the targetHeight
	 */
	public int getTargetHeight() {
		return targetHeight;
	}


	/**
	 * @param targetHeight the targetHeight to set
	 */
	public void setTargetHeight(int targetHeight) {
		this.targetHeight = targetHeight;
	}


	/**
	 * @return the targetImgList
	 */
	public List getTargetImgList() {
		return targetImgList;
	}


	/**
	 * @param targetImgList the targetImgList to set
	 */
	public void setTargetImgList(List targetImgList) {
		this.targetImgList = targetImgList;
	}


	/**
	 * @return the targetWidth
	 */
	public int getTargetWidth() {
		return targetWidth;
	}


	/**
	 * @param targetWidth the targetWidth to set
	 */
	public void setTargetWidth(int targetWidth) {
		this.targetWidth = targetWidth;
	}
}
