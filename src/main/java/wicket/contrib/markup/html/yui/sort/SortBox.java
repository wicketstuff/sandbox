package wicket.contrib.markup.html.yui.sort;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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

public class SortBox extends AbstractYuiPanel {

	private final class Box extends FormComponent implements Serializable {
		private static final long serialVersionUID = 1L;

		public Box(final String id, final int count, final String name,
				YuiImage yuiImage) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject(Component component) {
					return name + count + "_" + javaScriptId;
				}
			}));
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getObject(Component component) {
							if (name.equals("SortImg")) {
								List<InlineStyle> aInlineStyleList = settings
										.getImgStyleList();
								InlineStyle aInlineStyle = aInlineStyleList
										.get(count);
								return aInlineStyle.getStyle();
							} else {
								return new String("");
							}
						}
					}));
		}
	}

	private final class ImgStyle extends FormComponent implements Serializable {
		private static final long serialVersionUID = 1L;

		public ImgStyle(final String id) {
			super(id);
			add(new AttributeModifier("style", true,
					new AbstractReadOnlyModel() {
						private static final long serialVersionUID = 1L;

						@Override
						public Object getObject(Component component) {
							return "width:" + settings.getWidth()
									+ "px; height:" + settings.getHeight()
									+ "px";
						}
					}));
		}
	}

	private static final long serialVersionUID = 1L;

	private String javaScriptId;

	private String mode;

	private SortSettings settings;

	public SortBox(String id, final int index, YuiImage image,
			SortSettings settings) {
		super(id);
		this.settings = settings;
		mode = settings.getMode();

		ImgStyle style = new ImgStyle("imgStyle");
		add(style);
		style.add(new Box("sortImg", index, "SortImg", image));

		Label sortLabel = new Label("sortScript", new AbstractReadOnlyModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getObject(Component component) {
				return getAnimSelectInitializationScript(index);
			}
		});
		sortLabel.setEscapeModelStrings(false);
		add(sortLabel);

	}

	protected String getAnimSelectInitializationScript(int boxId) {
		PackagedTextTemplate template = new PackagedTextTemplate(SortBox.class,
				"sort.js");
		Map<String, Object> variables = new HashMap<String, Object>(5);
		variables.put("childJavaScriptId", javaScriptId);
		variables.put("id", new Integer(boxId));
		variables.put("classId", "'SortImg" + boxId + "_" + javaScriptId + "'");

		if (mode.equals("INTERSECT")) {
			variables.put("isIntersect", "_i");
			variables.put("groupId", "");
		} else if (mode.equals("POINT")) {
			variables.put("isIntersect", "");
			variables.put("groupId", ", 'groupA'");
		}

		template.interpolate(variables);
		return template.getString();
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		javaScriptId = findParent(SortGroup.class).getMarkupId();
	}
}
