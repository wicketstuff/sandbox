/*
 * $Id$
 * $Revision$ $Date$
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.markup.html.form;

import wicket.Response;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.form.CheckBox;
import wicket.model.IModel;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;
import wicket.util.value.ValueMap;

/**
 * Checkbox that updates the server side model using AJAX whenever it is
 * clicked. After updating, method onAjaxModelUpdated is called to allow users
 * to do custom handling like persisting the change to a database.
 * <p>
 * An example:
 * 
 * <pre>
 *         	addTicketOptionForm.add(new ListView(&quot;ticketOptionsList&quot;,
 *         			new PropertyModel(activityModel, &quot;ticketOptions&quot;)) {
 *         
 *         		protected void populateItem(ListItem item) {
 *         			final TicketOption ticketOption = (TicketOption) item
 *         					.getModelObject();
 *         			...
 *         			item.add(new ImmediateCheckBox(&quot;available&quot;) {
 *         				@Override
 *         				protected void onAjaxModelUpdated() {
 *         					Activity activity = (Activity)ActivityDetailsPage.this.getModelObject();
 *         					getActivityDao().update(activity);
 *         				}
 *         			});
 *         		...
 * </pre>
 * 
 * </p>
 * 
 * @author Eelco Hillenius
 * @author Igor Vaynberg (ivaynberg)
 */
public class ImmediateCheckBox extends CheckBox
{
	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public ImmediateCheckBox(String id)
	{
		super(id);
		add(new ImmediateUpdateAjaxHandler());
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public ImmediateCheckBox(String id, IModel model)
	{
		super(id, model);
		add(new ImmediateUpdateAjaxHandler());
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
	 *    getJsCallbackFunctionName() {return(&quot;handleit&quot;);}
	 *    
	 *    in javascript:
	 *    
	 *    function handleit(type, data, evt) { alert(data); } 
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
	 * @see ImmediateCheckBox#getJSCallbackFunctionName()
	 * @see IResourceStream
	 * @see StringBufferResourceStream
	 * 
	 * @return resource stream used as <code>data</code> argument in
	 *         client-side javascript callback handler
	 */
	protected IResourceStream getResponseResourceStream()
	{
		return new StringBufferResourceStream();
	}

	/**
	 * Ajax handler that immediately updates the attached component when the
	 * onclick event happens.
	 */
	public static class ImmediateUpdateAjaxHandler extends DojoAjaxHandler
	{
		/** checkbox this handler is attached to. */
		private ImmediateCheckBox checkBox;

		/**
		 * Construct.
		 */
		public ImmediateUpdateAjaxHandler()
		{
		}

		/**
		 * @see AbstractAjaxBehavior#onRenderHeadInitContribution(Response response)
		 */
		public final void onRenderHeadInitContribution(Response response)
		{
			super.onRenderHeadInitContribution(response);
			StringBuffer s = new StringBuffer(
					"\t<script language=\"JavaScript\" type=\"text/javascript\">\n").append(
					"\tfunction immediateCheckBox(componentUrl, componentPath, val) { \n").append(
					"\t\tdojo.io.bind({\n").append(
					"\t\t\turl: componentUrl + '&' + componentPath + '=' + val,\n").append(
					"\t\t\tmimetype: \"text/plain\",\n").append(
					"\t\t\tload: function(type, data, evt) {");

			if (checkBox.getJSCallbackFunctionName() != null) {
				s.append(checkBox.getJSCallbackFunctionName()).append("(type, data, evt);");
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
			final ValueMap attributes = tag.getAttributes();
			final String attributeValue = new StringBuffer("javascript:immediateCheckBox('")
					.append(getCallbackUrl()).append("', '").append(checkBox.getInputName())
					.append("', this.checked);").toString();
			attributes.put("onclick", attributeValue);
		}

		/**
		 * @see wicket.AjaxHandler#onBind()
		 */
		protected void onBind()
		{
			this.checkBox = (ImmediateCheckBox)getComponent();
		}

		/**
		 * Gets the resource to render to the requester.
		 * 
		 * @return the resource to render to the requester
		 */
		protected final IResourceStream getResponse()
		{
			// let the form component update its model
			checkBox.convert();
			checkBox.updateModel();
			checkBox.onAjaxModelUpdated();
			return checkBox.getResponseResourceStream();
		}
	}

}
