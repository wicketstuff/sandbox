package wicket.contrib.markup.html.form.validation;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.WicketRuntimeException;
import wicket.contrib.dojo.DojoAjaxHandler;
import wicket.markup.ComponentTag;
import wicket.markup.html.HtmlHeaderContainer;
import wicket.markup.html.form.FormComponent;
import wicket.model.Model;
import wicket.util.resource.IResourceStream;
import wicket.util.resource.StringBufferResourceStream;
import wicket.util.value.ValueMap;

public class FXValidationAjaxHandler extends DojoAjaxHandler
{
	/** name event, like onblur. */
	private final String eventName;

	/** component this handler is attached to. */
	private FormComponent formComponent;

	/**
	 * Construct.
	 * @param eventName name of the event to attach to, e.g. 'onchange'
	 */
	
	public FXValidationAjaxHandler(String eventName)
	{
		if (eventName == null)
		{
			throw new NullPointerException("argument eventName must be not null");
		}
		this.eventName = eventName;
	}
	
	public final void renderHeadContribution(HtmlHeaderContainer container)
	{
		String s =

			"\t<script language=\"JavaScript\" type=\"text/javascript\">\n" +
			"\tfunction validate(componentUrl, componentPath, field) { \n" +
			"\t\tdojo.io.bind({\n" +
			"\t\t\turl: componentUrl + '&' + componentPath + '=' + field.value,\n" +
			"\t\t\tmimetype: \"text/plain\",\n" +
			"\t\t\tload: function(type, data, evt) {\n" +
			"\t\t\t\tif(data == 'valid')\n" +
			"\t\t\t\t{\n" +
			"\t\t\t\t\tdojo.fx.html.colorFadeOut(field, [152,194,125], 500, 0);\n" +
			"\t\t\t\t\treturn true;\n" +
			"\t\t\t\t}\n" +
			"\t\t\t\telse if(data == 'invalid')\n"+
			"\t\t\t\t{\n" +
			"\t\t\t\tdojo.fx.html.colorFadeTo(field, [252,134,130], 500, 0);\n" +
			"\t\t\t\t\treturn false;\n" +
			"\t\t\t\t}\n" +
			"\t\t\t\telse\n" +
			"\t\t\t\t{\n" +
			"\t\t\t\t\treturn false;\n" +
			"\t\t\t\t}\n" +
			"\t\t\t}\n" +
			"\t\t});\n" +
			
			"\t}\n" +
			"\t</script>\n";

		container.getResponse().write(s);
	}
	
	/**
	 * Attaches the event handler for the given component to the given tag.
	 * @param tag
	 *            The tag to attach
	 */
	public final void onComponentTag(final ComponentTag tag)
	{
		final ValueMap attributes = tag.getAttributes();
		final String attributeValue =
			"javascript:validate('" + getCallbackUrl() + "', '" + formComponent.getInputName() + "', this);";
		attributes.put(eventName, attributeValue);
	}

	/**
	 * @see wicket.AjaxHandler#onBind()
	 */
	protected void onBind()
	{
		Component c = getComponent();
		if (!(c instanceof FormComponent))
		{
			throw new WicketRuntimeException("this handler must be bound to FormComponents");
		}
		this.formComponent = (FormComponent)c;
		this.formComponent.add(new AttributeModifier("id", true, new Model(this.formComponent.getId())));
	
	}

	/**
	 * @see wicket.EventRequestHandler#getResourceStream()
	 */
	/**
	 * Gets the resource to render to the requester.
	 * @return the resource to render to the requester
	 */
	protected final IResourceStream getResponse()
	{
		StringBufferResourceStream s = new StringBufferResourceStream();

		formComponent.validate();

		// When validation failed...
		if (!formComponent.isValid())
		{
			//TODO finish
			// The plan here is the visit all feedback components, re-render them, and
			// return the render results to the browser with the components (top level)
			// ids attached. We could then use this information to replace the dom
			// elements in the browser

			// We need a couple of things for this to work first:
			// 1) The ability to let a component render on its' own
			// 2) Trap that render result somewhere. Either by setting the response to
			//			render to on that component, or passing a response as a parameter
			//			of the render call
			// Furthermore, we need to have the javascript side covered. That could
			// be tricky too, but the cool thing about that is that if we would fix
			// that in a generic fashion, our ajax support would be pretty usable at once

			/*formComponent.getPage().visitChildren(IFeedback.class, new Component.IVisitor()
			{
				public Object component(Component component)
				{
					// this doesn't work yet.
					//component.render();
					return Component.IVisitor.CONTINUE_TRAVERSAL;
				}
			});*/
			
			s.append("invalid");
		}
		else
		{
			s.append("valid");
		}

		// for now, just display a simple message
		//s.append("ajax validation executed");

		return s;
	}
}
