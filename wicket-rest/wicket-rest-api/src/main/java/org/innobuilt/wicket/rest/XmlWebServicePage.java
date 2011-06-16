package org.innobuilt.wicket.rest;

import java.io.PrintWriter;
import java.io.Serializable;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.Model;

import com.thoughtworks.xstream.XStream;

public abstract class XmlWebServicePage extends AbstractWebServicePage {

	private static final long serialVersionUID = 1L;

	private transient XStream xstream;
	
	public XmlWebServicePage(PageParameters params) {
		super(params);
		  xstream = new XStream();
	}

	protected final void onRender(MarkupStream markupStream) {
	  PrintWriter pw = new PrintWriter(getResponse().getOutputStream());
	  pw.write(getXML().toString());
	  pw.close();
	 }

	 protected XStream getXStream() {
	  return xstream;
	 }

	 private CharSequence getXML() {
	  return xstream.toXML(getDefaultModelObject());
	 }

	 @Override
	 public final String getMarkupType() {
	  return "text/xml";
	 }

	@Override
	protected final void setModelFromBody(String body) {
		setDefaultModel(new Model((Serializable)xstream.fromXML(body)));
	}

}
