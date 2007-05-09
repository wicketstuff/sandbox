package wicket.contrib.markup.html.yui.selection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;

import wicket.contrib.ImageResourceInfo;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiAttribute;
import wicket.contrib.YuiImage;
import wicket.contrib.YuiProperty;
import wicket.contrib.YuiTextBox;

public class SelectionSettings implements Serializable {
	private static final long serialVersionUID = 1L;

	/* Image */
	public static SelectionSettings getDefault(YuiAttribute yuiAttribute,
			String easing, double duration, String event, int maxSelection,
			ArrayList list) {
		SelectionSettings settings = new SelectionSettings();
		settings.setResources(yuiAttribute, easing, duration, event,
				maxSelection, list);
		return settings;
	}

	/* Image + MaxSelection Message */
	public static SelectionSettings getDefault(YuiAttribute yuiAttribute,
			String easing, double duration, String event, int maxSelection,
			String message, ArrayList list) {
		SelectionSettings settings = new SelectionSettings();
		settings.setResources(yuiAttribute, easing, duration, event,
				maxSelection, message, list);
		return settings;
	}

	public static String jsToCss(String js) {
		String css = "";
		for (int i = 0; i < js.length(); i++) {
			StringBuffer aStringBuffer = new StringBuffer("");
			aStringBuffer.append(js.charAt(i));
			String aString = aStringBuffer.toString();
			if (aString.equals(aString.toUpperCase())) {
				css = css + "-" + aString.toLowerCase();
			} else {
				css = css + aString;
			}
		}
		return css;
	}

	public static String removeQuote(String js) {
		String css = "";
		for (int i = 0; i < js.length(); i++) {
			StringBuffer aStringBuffer = new StringBuffer("");
			aStringBuffer.append(js.charAt(i));
			String aString = aStringBuffer.toString();
			if (!aString.equals("'")) {
				css = css + aString;
			}
		}
		return css;
	}

	private String background;

	private double duration;

	private String easing;

	private String event;

	private int height;

	private List<InlineStyle> inlineStyleList = new ArrayList<InlineStyle>();

	private List list;

	private int maxSelection;

	private String message;

	private int thickness;

	private int width;

	private YuiAttribute yuiAttribute;

	public SelectionSettings() {

	}

	public String getBackground() {
		return background;
	}

	public double getDuration() {
		return duration;
	}

	public String getEasing() {
		return easing;
	}

	public String getEvent() {
		return event;
	}

	public int getHeight() {
		return height;
	}

	public List<InlineStyle> getInlineStyleList() {
		return inlineStyleList;
	}

	public List getList() {
		return list;
	}

	public int getMaxSelection() {
		return maxSelection;
	}

	public String getMessage() {
		return message;
	}

	public int getThickness() {
		return thickness;
	}

	public int getWidth() {
		return width;
	}

	public YuiAttribute getYuiAttribute() {
		return yuiAttribute;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setEasing(String easing) {
		this.easing = easing;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setImageResources(YuiAttribute yuiAttribute, List list) {
		setYuiAttribute(yuiAttribute);
		setThickness(thickness);
		setList(list);

		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).getClass().getSimpleName().equals("YuiImage")) {
				YuiImage aImage = (YuiImage) list.get(i);
				ResourceReference aResourceReference = new ResourceReference(
						Selection.class, aImage.getFileName());

				ImageResourceInfo backgroundInfo = new ImageResourceInfo(
						aResourceReference);
				int width = backgroundInfo.getWidth();
				int height = backgroundInfo.getHeight();

				InlineStyle aInlineStyle = new InlineStyle();

				aInlineStyle.add("background", "url("
						+ RequestCycle.get().urlFor(aResourceReference) + ")");
				aInlineStyle.add("width", width + "px");
				aInlineStyle.add("height", height + "px");

				Map propertyMap = yuiAttribute.getPropertyMap();
				Set keySet = propertyMap.keySet();
				Iterator iter = keySet.iterator();
				while (iter.hasNext()) {
					String aKey = (String) iter.next();
					YuiProperty aYuiProperty = (YuiProperty) propertyMap
							.get(aKey);
					aInlineStyle.add("border", "solid "
							+ removeQuote(aYuiProperty.getFrom()));
				}
				inlineStyleList.add(aInlineStyle);
			} else {
				YuiTextBox aTextBox = (YuiTextBox) list.get(i);

				InlineStyle aInlineStyle = new InlineStyle();

				aInlineStyle.add("background", aTextBox.getBackground());
				aInlineStyle.add("width", aTextBox.getWidth() + "px");
				aInlineStyle.add("height", aTextBox.getHeight() + "px");

				Map propertyMap = yuiAttribute.getPropertyMap();
				Set keySet = propertyMap.keySet();
				Iterator iter = keySet.iterator();
				while (iter.hasNext()) {
					String aKey = (String) iter.next();
					YuiProperty aYuiProperty = (YuiProperty) propertyMap
							.get(aKey);
					aInlineStyle.add("border", "solid "
							+ removeQuote(aYuiProperty.getFrom()));
				}
				inlineStyleList.add(aInlineStyle);
			}
		}
	}

	public void setInlineStyleList(List<InlineStyle> inlineStyleList) {
		this.inlineStyleList = inlineStyleList;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResources(YuiAttribute yuiAttribute, String easing,
			double duration, String event, int maxSelection, ArrayList list) {
		setEasing("YAHOO.util.Easing." + easing);
		setDuration(duration);
		setEvent(event);
		setMaxSelection(maxSelection);
		setImageResources(yuiAttribute, list);
	}

	public void setResources(YuiAttribute yuiAttribute, String easing,
			double duration, String event, int maxSelection, String message,
			ArrayList list) {
		setEasing("YAHOO.util.Easing." + easing);
		setDuration(duration);
		setEvent(event);
		setMaxSelection(maxSelection);
		setMessage("'" + message + "'");
		setImageResources(yuiAttribute, list);
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setYuiAttribute(YuiAttribute yuiAttribute) {
		this.yuiAttribute = yuiAttribute;
	}
}
