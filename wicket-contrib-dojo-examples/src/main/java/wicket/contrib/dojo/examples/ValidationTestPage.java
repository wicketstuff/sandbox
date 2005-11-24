package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.markup.html.form.validation.FXFeedbackIndicator;
import wicket.contrib.markup.html.form.validation.FXValidationAjaxHandler;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.IntegerValidator;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

public class ValidationTestPage extends WebPage{

	private static final long serialVersionUID = 1L;
	private Form form;
	private TextField testfield;
	private String textValue;
	private	FormComponent tx;

	public ValidationTestPage(PageParameters parameters)
	{
		 
			form = new Form("testform");
			testfield = new RequiredTextField("text", new PropertyModel(ValidationTestPage.this, "textValue"));
			testfield.add(LengthValidator.max(5));

			
			
			
			add(form);
			form.add(testfield);
			form.add(tx = new RequiredTextField("integerInRangeProperty", Integer.class).add(IntegerValidator.range(0, 100)));
			tx.add(new FXValidationAjaxHandler("onblur"));
			
	        FXFeedbackIndicator indicator = new FXFeedbackIndicator("indicator");
	        indicator.setIndicatorFor(testfield);
	        form.add(indicator);
	        final FeedbackPanel feedback = new FeedbackPanel("feedback");
			add(feedback);
	        
	}
	public String getTextValue()
	{
		return this.textValue;
	}
	public void setTextValue(String v)
	{
		this.textValue = v;
	}
}
