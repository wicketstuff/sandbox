package wicket.contrib.markup.html.form;

/*
 * 
 */

import java.util.List;

import wicket.Response;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.RadioChoice;
import wicket.model.IModel;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;
import wicket.util.value.ValueMap;


/**
 * @author Ruud Booltink
 * @author Marco van de Haar
 */
public class ImmediateRadioChoice extends RadioChoice
{

	private final int numItems;


	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 * @param list
	 */
	public ImmediateRadioChoice(String id, IModel model, List list)
	{
		super(id, model, list);
		this.numItems = list.size();
		add(new ImmediateUpdateAjaxHandler());
	}

	/**
	 * @return number of items
	 */
	public int getNumItems()
	{
		return this.numItems;
	}


	/**
	 * Called after the model is updated. Use this method to e.g. update the
	 * persistent model. Does nothing by default.
	 */
	protected void onAjaxModelUpdated()
	{
	}

	/**
	 * Returns the name of the javascript method that will be invoked when the
	 * processing of the ajax callback is complete. The method must have the
	 * following signature: <code>function(type, data, evt)</code> where the
	 * data argument will be the value of the resouce stream provided by
	 * <code>getResponseResourceStream</code> method.
	 * 
	 * For example if we want to echo the value returned by
	 * getResponseResourceStream stream we can implement it as follows: <code>
	 * <pre>
	 *      
	 *      getJsCallbackFunctionName() {return(&quot;handleit&quot;);}
	 *      
	 *      in javascript:
	 *      
	 *      function handleit(type, data, evt) { alert(data); } 
	 * </pre>
	 * </code>
	 * 
	 * @see ImmediateCheckBox#getResponseResourceStream()
	 * @return name of the client-side javascript callback handler
	 */
	protected String getJSCallbackFunctionName()
	{
		return null;
	}

	/**
	 * returns the resource stream whose value will become the value of the
	 * <code>data</code> argument in the defined client-side javascript
	 * callback handler.
	 * 
	 * 
	 * @return resource stream used as <code>data</code> argument in
	 *         client-side javascript callback handler
	 */
	protected IResourceStream getResponseResourceStream()
	{
		return new StringBufferResourceStream();
	}

	/**
	 * Gets the default choice.
	 * 
	 * @param selected
	 * @return The default choice
	 * @see wicket.markup.html.form.AbstractSingleSelectChoice#getDefaultChoice(java.lang.Object)
	 */
	protected CharSequence getDefaultChoice(java.lang.Object selected)
	{
		return getChoices().get(0).toString();
	}

	/**
	 * Ajax handler that immediately updates the attached component when the
	 * onclick event happens.
	 */
	public static class ImmediateUpdateAjaxHandler extends DojoAjaxHandler
	{
		/** checkbox this handler is attached to. */
		private ImmediateRadioChoice radioButton;

		private int size;

		/**
		 * Construct.
		 */
		public ImmediateUpdateAjaxHandler()
		{
		}

		/**
		 * @see AbstractAjaxBehavior#onRenderHeadContribution(wicket.Response)
		 */
		public final void onRenderHeadContribution(Response response)
		{
			super.onRenderHeadContribution(response);
			StringBuffer s = new StringBuffer(
					"\t<script language=\"JavaScript\" type=\"text/javascript\">\n").append(
					"\tfunction getSelectedRadio(nodeId, numItems)\n").append("\t{\n").append(
					"\t\tvar value = 'NOT_SET';\n").append("\t\tvar i = 0;\n").append(
					"\t\twhile(value=='NOT_SET')\n").append("\t\t{\n").append(
					"\t\t\tvar itemId = nodeId + '_' + i;\n").append(
					"\t\t\tvar item = document.getElementById(itemId);\n").append(
					"\t\t\tif(item.checked)\n").append("\t\t\t{\n").append(
					"\t\t\t\tvalue = item.value;\n").append("\t\t\t}\n").append("\t\t\t++i;\n")
					.append("\t\t}\n").append("\t\treturn value;\n").append(

					"\t}\n\n").append(


					"\tfunction immediateRadioButton(componentUrl, componentPath, val) { \n")
					.append("\t\tdojo.io.bind({\n").append(
							"\t\t\turl: componentUrl + '&' + componentPath + '=' + val,\n").append(
							"\t\t\tmimetype: \"text/plain\",\n").append(
							"\t\t\tload: function(type, data, evt) {");

			if (radioButton.getJSCallbackFunctionName() != null)
			{
				s.append(radioButton.getJSCallbackFunctionName()).append("(type, data, evt);");
			}

			s.append("}\n\t\t});").append("\n\t}\n").append("\t</script>\n");

			response.write(s.toString());
		}

		/**
		 * Attaches the event handler for the given component to the given tag.
		 * 
		 * @param tag
		 *            The tag to attach
		 */
		public final void onComponentTag(final ComponentTag tag)
		{
			// List l = getChoices();
			final ValueMap attributes = tag.getAttributes();
			final String attributeValue = new StringBuffer("javascript:immediateRadioButton('")
					.append(getCallbackUrl())
					.append("', '")
					.append(radioButton.getInputName())
					.append(
							"',  getSelectedRadio('" + radioButton.getInputName() + "' ,"
									+ ((ImmediateRadioChoice)(getComponent())).getNumItems() + "))")
					.toString();
			attributes.put("onclick", attributeValue);
		}


		/**
		 * @see wicket.behavior.AjaxHandler#onBind()
		 */
		protected void onBind()
		{
			this.radioButton = (ImmediateRadioChoice)getComponent();
		}

		/**
		 * Gets the resource to render to the requester.
		 * 
		 * @return the resource to render to the requester
		 */
		protected final IResourceStream getResponse()
		{
			// let the form component update its model
			radioButton.convert();
			radioButton.updateModel();
			radioButton.onAjaxModelUpdated();
			return radioButton.getResponseResourceStream();
		}
	}

}
