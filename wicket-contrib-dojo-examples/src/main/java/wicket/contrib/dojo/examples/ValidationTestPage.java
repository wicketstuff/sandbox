package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.form.validation.FXFeedbackIndicator;
import wicket.contrib.dojo.markup.html.form.validation.FXValidationAjaxHandler;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.validation.IntegerValidator;
import wicket.markup.html.form.validation.LengthValidator;
import wicket.markup.html.form.validation.NumberValidator;
import wicket.markup.html.form.validation.StringValidator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.PropertyModel;

public class ValidationTestPage extends WebPage{

	private static final long serialVersionUID = 1L;
	private Form form;
	//private TextField testfield;
	private TextArea testfield;
	private String textValue;
	private	FormComponent tx;
	private int integerInRangeProperty;
	private int integerInRangeProperty2;
	
	
	
	public ValidationTestPage(PageParameters parameters)
	{
		 
			form = new Form("testform");
			add(form);
			//testfield = new RequiredTextField("text", new PropertyModel(ValidationTestPage.this, "textValue"));
			testfield =  new TextArea("text",new PropertyModel(ValidationTestPage.this, "textValue"));
			testfield.add(StringValidator.maximumLength(5));
			testfield.add(new FXValidationAjaxHandler("onblur"));
			form.add(testfield);
			
			FXFeedbackIndicator indicator = new FXFeedbackIndicator("indicator");
	        form.add(indicator);

			
	        FormComponent tx = new RequiredTextField("integerInRangeProperty", new PropertyModel(ValidationTestPage.this, "integerInRangeProperty"),Integer.class).add(NumberValidator.range(0, 100));
	        form.add(tx);
	        tx.add(new FXValidationAjaxHandler("onblur"));
			

			//test multiple ajax textfields
			FormComponent tx2 = new RequiredTextField("integerInRangeProperty2", new PropertyModel(ValidationTestPage.this, "integerInRangeProperty2"),Integer.class).add(NumberValidator.range(0, 200));
			form.add(tx2);
			tx2.add(new FXValidationAjaxHandler("onblur"));

	        
	}
	public String getTextValue()
	{
		return this.textValue;
	}
	public void setTextValue(String v)
	{
		this.textValue = v;
	}
	public int getIntegerInRangeProperty()
	{
		return this.integerInRangeProperty;
	}
	public int getIntegerInRangeProperty2()
	{
		return this.integerInRangeProperty2;
	}
	public void setIntegerInRangeProperty(int i)
	{
		this.integerInRangeProperty = i;
	}
	public void setIntegerInRangeProperty2(int i)
	{
		this.integerInRangeProperty2 = i;
	}
}
