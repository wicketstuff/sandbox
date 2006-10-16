package wicket.contrib.markup.html.yui.sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.RequestCycle;
import wicket.ResourceReference;
import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;

/**
 * Allows the user to define the sort settings
 * 
 * @author cptan
 * 
 */
public class SortSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	public static SortSettings getDefault(String mode, ArrayList sortList) {
		SortSettings settings = new SortSettings();
		settings.setResources(mode, sortList);
		return settings;
	}

	private int height;

	private List<InlineStyle> imgStyleList = new ArrayList<InlineStyle>();

	private String mode;

	private ArrayList sortList;

	private int width;

	public SortSettings() {
	}

	public int getHeight() {
		return height;
	}

	public List<InlineStyle> getImgStyleList() {
		return imgStyleList;
	}

	public String getMode() {
		return mode;
	}

	public ArrayList getSortList() {
		return sortList;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setImageResources(ArrayList sortList) {
		for (int i = sortList.size() - 1; i >= 0; i--) {
			YuiImage img = (YuiImage) sortList.get(i);

			ResourceReference imgRR = new ResourceReference(SortSettings.class,
					img.getFileName());

			ImageResourceInfo imgInfo = new ImageResourceInfo(imgRR);
			int imgWidth = imgInfo.getWidth();
			int imgHeight = imgInfo.getHeight();

			InlineStyle imgStyle = new InlineStyle();
			imgStyle.add("background", "url("
					+ RequestCycle.get().urlFor(imgRR) + ")");
			imgStyle.add("width", imgWidth + "px");
			imgStyle.add("height", imgHeight + "px");

			imgStyle.add("top", img.getSortTop() + "px");
			imgStyle.add("left", img.getSortLeft() + "px");

			imgStyleList.add(imgStyle);

			this.width = imgWidth;
			this.height = imgHeight;
		}
	}

	public void setImgStyleList(List<InlineStyle> imgStyleList) {
		this.imgStyleList = imgStyleList;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setResources(String mode, ArrayList sortList) {
		setMode(mode);
		setSortList(sortList);
		setImageResources(sortList);
	}

	public void setSortList(ArrayList sortList) {
		this.sortList = sortList;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
