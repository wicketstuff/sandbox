package wicket.contrib.cometd.examples.pages;

import wicket.PageParameters;
import wicket.contrib.cometd.sharedform.DojoSharedForm;
import wicket.markup.html.WebPage;
import wicket.markup.html.form.TextArea;
import wicket.markup.html.form.TextField;

public class FormSharedSample extends WebPage{

	
	public FormSharedSample(PageParameters parameters)
	{	
		DojoSharedForm form = new DojoSharedForm("myForm");
		form.add(new TextField("text1"));
		form.add(new TextField("text2"));
		form.add(new TextArea("text3"));
		add(form);
	}
}
