package wicket.contrib.markup.html.yui.anim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.contrib.InlineStyle;
import wicket.contrib.YuiImage;
import wicket.contrib.markup.html.yui.AbstractYuiPanel;
import wicket.extensions.util.resource.PackagedTextTemplate;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.FormComponent;
import wicket.model.AbstractReadOnlyModel;
import wicket.util.collections.MiniMap;

/**
 * An AnimBox is a component which consists of four images:
 * <p> - the unselected image
 * <p> - the unselected mouseover image
 * <p> - the selected image
 * <p> - the selected mouseover image
 * 
 * @author cptan
 * 
 */
public class AnimBox extends AbstractYuiPanel {
	private AnimSettings settings;

	private String javaScriptId;

	private String easing;

	private double duration;

	private int maxSelection;

	private String message;

	/**
	 * Creates an AnimBox
	 * 
	 * @param id -
	 *            wicket id
	 * @param index -
	 *            auto-generated through the listview
	 * @param animSelectOption -
	 *            defines the four images
	 * @param settings -
	 *            defines the animation settings
	 */
	public AnimBox(String id, final int index, AnimOption animSelectOption,
			AnimSettings settings) {
		super(id);
		this.settings = settings;
		this.easing = settings.getEasing();
		this.duration = settings.getDuration();
		this.maxSelection = settings.getMaxSelection();
		this.message = settings.getMessage();

		ImgStyle style = new ImgStyle("imgStyle");
		add(style);
		style.add(new AnimSelectBox("defaultImg", index, "DefaultImg",
				animSelectOption.getDefaultImg()));
		style.add(new AnimSelectBox("defaultImgOver", index, "DefaultImgOver",
				animSelectOption.getDefaultImgOver()));
		style.add(new AnimSelectBox("selectedImg", index, "SelectedImg",
				animSelectOption.getSelectedImg()));
		style.add(new AnimSelectBox("selectedImgOver", index,
				"SelectedImgOver", animSelectOption.getSelectedImgOver()));

		Label animSelect = new Label("animSelectScript",
				new AbstractReadOnlyModel() {
					private static final long serialVersionUID = 1L;

					public Object getObject(Component component) {
						return getAnimSelectInitializationScript(index);
					}
				});
		animSelect.setEscapeModelStrings(false);
		add(animSelect);
	}

	/**
	 * Initialize the anim.js for each option
	 * 
	 * @param index -
	 *            auto-generated through the listview
	 * @return a String representation of the anim.js
	 */
	protected String getAnimSelectInitializationScript(int index) {
		PackagedTextTemplate template = new PackagedTextTemplate(AnimBox.class,
				"anim.js");
		Map variables = new MiniMap(7);
		variables.put("javaScriptId", javaScriptId);
		variables.put("boxId", new Integer(index));
		variables.put("easing", "YAHOO.util.Easing." + easing);
		variables.put("duration", new Double(duration));
		variables.put("maxSelection", new Integer(maxSelection));
		variables.put("noOfBoxes", new Integer(settings
				.getAnimOptionList().size()));
		if (message == null || message.equals("")) {
			message = "Up to " + maxSelection + " selections allowed!";
		}
		variables.put("message", message);
		template.interpolate(variables);
		return template.getString();
	}

	/**
	 * Get the markup Id of the super parent class on attach
	 */
	protected void onAttach() {
		super.onAttach();
		javaScriptId = findParent(AnimGroup.class).getMarkupId();
	}

	/**
	 * Get the image's width and height
	 * 
	 * @author cptan
	 * 
	 */
	private final class ImgStyle extends FormComponent implements Serializable {
		private static final long serialVersionUID = 1L;

		public ImgStyle(final String id) {
			super(id);
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject(Component component) {
							return "width:" + settings.getWidth()
									+ "px; height:" + settings.getHeight()
									+ "px";
						}
					}));
		}
	}

	/**
	 * Represent one of the images for each option
	 * 
	 * @author cptan
	 * 
	 */
	private final class AnimSelectBox extends FormComponent implements
			Serializable {
		private static final long serialVersionUID = 1L;

		public AnimSelectBox(final String id, final int count,
				final String name, YuiImage yuiImage) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				public Object getObject(Component component) {
					return name + count + "_" + javaScriptId;
				}
			}));
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						public Object getObject(Component component) {
							if (name.equals("DefaultImg")) {
								ArrayList aInlineStyleList = settings
										.getDefaultImgStyleList();
								InlineStyle aInlineStyle = (InlineStyle) aInlineStyleList
										.get(0);
								return aInlineStyle.getStyle();
							} else if (name.equals("DefaultImgOver")) {
								ArrayList aInlineStyleList = settings
										.getDefaultImgOverStyleList();
								InlineStyle aInlineStyle = (InlineStyle) aInlineStyleList
										.get(0);
								return aInlineStyle.getStyle();
							} else if (name.equals("SelectedImg")) {
								ArrayList aInlineStyleList = settings
										.getSelectedImgStyleList();
								InlineStyle aInlineStyle = (InlineStyle) aInlineStyleList
										.get(0);
								return aInlineStyle.getStyle();
							} else if (name.equals("SelectedImgOver")) {
								ArrayList aInlineStyleList = settings
										.getSelectedImgOverStyleList();
								InlineStyle aInlineStyle = (InlineStyle) aInlineStyleList
										.get(0);
								return aInlineStyle.getStyle();
							} else
								return new String("");
						}
					}));
		}
	}
}
