package wicket.contrib.mootools.validators;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.FormComponent;

import wicket.contrib.mootools.AbstractRequireMooBehavior;
import wicket.contrib.mootools.effects.MFXStyle;

public class MFXValidationHandler extends AbstractRequireMooBehavior {
	private static final long serialVersionUID = 1L;
	private String validColor;
	private String invalidColor;
	private FormComponent formComponent;
	private Boolean inline;
	private String jsEvent = "onblur";
	
	public MFXValidationHandler(String validColor,String invalidColor) {
		this.validColor = validColor;
		this.invalidColor = invalidColor;
		this.inline = false;
	}
	
	public MFXValidationHandler(String validColor,String invalidColor,Boolean inline) {
		this.validColor = validColor;
		this.invalidColor = invalidColor;
		this.inline = inline;
	}
	
	public String mooFunction() {
		return null;
	}

	private String invalidFunction() {
		final String compid = getComponent().getMarkupId();
		MFXStyle validToInvalid = new MFXStyle("background-color",validColor,invalidColor);
		if(jsEvent.equalsIgnoreCase("onchange"))
			validToInvalid.setOnComplete("$('"+compid+"').blur();");
		validToInvalid.setTarget(compid).setDuration(1000);
		return compid + "=" + validToInvalid.getMooFunction();
	}
	
	private String validFunction() {
		final String compid = getComponent().getMarkupId();
		MFXStyle invalidToValid = new MFXStyle("background-color",invalidColor,validColor);
		if(jsEvent.equalsIgnoreCase("onchange"))
			invalidToValid.setOnComplete("$('"+compid+"').blur();");
		invalidToValid.setTarget(compid).setDuration(500);
		return compid + "=" + invalidToValid.getMooFunction();
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		
		if(!inline) {
			addMooDomFunction(invalidFunction());
			addMooDomFunction(validFunction());	
		}
		super.renderHead(response);
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag) {
		// TODO Auto-generated method stub
		super.onComponentTag(tag);
		tag.put("id", getComponent().getMarkupId());
		
		Component c = getComponent();
		if (!(c instanceof FormComponent))
		{
			throw new WicketRuntimeException("This handler must be bound to FormComponents");
		}
		formComponent = (FormComponent)c;
		tag.put(jsEvent, "javascript:" + "var wcall=wicketAjaxGet('" + getCallbackUrl() + "&amp;" + formComponent.getInputName() + "=' + this.value, function() { }, function() { });return !wcall;");
	}
	
	
	protected void respond(AjaxRequestTarget target)
	{
		formComponent.validate();
		String componentId = getComponent().getMarkupId();
		
		if (!formComponent.isValid())
		{
			if(inline)
				target.prependJavascript(invalidFunction());
			target.appendJavascript(componentId+".start('"+validColor+"','"+invalidColor+"')");
		}
		else{
			if(inline)
				target.prependJavascript(validFunction());
			target.appendJavascript(componentId+".start('"+validColor+"','"+validColor+"')");
		}
	}

	public String getAjaxIndicatorMarkupId() {
		// TODO Auto-generated method stub
		return null;
	}

	public MFXValidationHandler setJsEvent(String jsEvent) {
		this.jsEvent = jsEvent;
		return this;
	}

	public String getJsEvent() {
		return jsEvent;
	}

}
