package org.wicketstuff.push.examples.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.wicketstuff.push.cometd.sharedform.DojoSharedForm;

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
