package org.wicketstuff.pickwick.frontend.pages;

import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.pickwick.bean.DisplaySequence;

public class DateModel extends AbstractReadOnlyModel {
	Component c;
	public DateModel(Component c) {
		this.c = c;
	}
	@Override
	public Object getObject() {
		DateFormat f = DateFormat.getDateInstance(DateFormat.FULL, Session.get().getLocale());
		DisplaySequence o = ((DisplaySequence) c.getDefaultModelObject());
		if (o == null)
			return null;
		Date date = o.getDate();
		if (date == null){
			return null;
		}
		return f.format(date);
	}
}
