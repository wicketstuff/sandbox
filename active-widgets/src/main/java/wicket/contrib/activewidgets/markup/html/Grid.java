package wicket.contrib.activewidgets.markup.html;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.template.PackagedTextTemplate;

import wicket.contrib.activewidgets.AWHeaderContributor;

public class Grid extends Panel  implements IHeaderContributor {

	/**
	 * The DOM id of the element that hosts the javascript component.
	 */
	private String elementId;

	/**
	 * The JavaScript variable name of the grid component.
	 */
	private String javaScriptId;


	@SuppressWarnings("unused")
	private GirdElement gridElement;

	/**
	 * The container/ receiver of the javascript component.
	 */
	final class GirdElement extends FormComponent {

		private static final long serialVersionUID = 1L;

		public GirdElement(String id, IModel model) {
			super(id, model);
			// TODO Auto-generated constructor stub
		}

		public GirdElement(String id) {
			super(id);
			add(new AttributeModifier("id", true, new AbstractReadOnlyModel()
			{
				private static final long serialVersionUID = 1L;

				@Override
				public Object getObject()
				{
					return elementId;
				}
			}));
		}

	}


	private void constructorInit(String id) {
		
		add(new AWHeaderContributor());
		Label initialization = new Label("initialization", new AbstractReadOnlyModel()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see wicket.model.IModel#getObject(wicket.Component)
			 */
			@Override
			public Object getObject()
			{
				return getJavaScriptComponentInitializationScript();
			}
		});
		initialization.setEscapeModelStrings(false);
		add(initialization);
		add(gridElement = new GirdElement("gridContainer"));
	}

	
	public Grid(String id, IModel model) {
		super(id, model);
		constructorInit(id);
	}

	public Grid(String id) {
		super(id);
		constructorInit(id);
	}

	public void renderHead(IHeaderResponse response) {
		response.renderOnLoadJavascript("init" + javaScriptId + "();");
	}

	/**
	 * Gets the initilization script for the javascript component.
	 * 
	 * @return the initilization script
	 */
	protected String getJavaScriptComponentInitializationScript()
	{
		Map<String, Object> variables = new HashMap<String, Object>(2);
		variables.put("javaScriptId", javaScriptId);
		variables.put("elementId", elementId);

		PackagedTextTemplate template = new PackagedTextTemplate(Grid.class, "init.js");
		template.interpolate(variables);

		return template.getString();
	}

	/**
	 * @see wicket.Component#onAttach()
	 */
	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		// initialize lazily
		if (elementId == null) {
			// assign the markup id
			String id = getMarkupId();
			elementId = "AWG_" + id;
			javaScriptId = elementId + "_JS";
		}
	}
}
