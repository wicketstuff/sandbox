package org.wicketstuff.dojo.examples.combobox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.form.DojoDropDownChoice;
import org.wicketstuff.dojo.markup.html.form.suggestionlist.DojoRequestSuggestionList;
import org.wicketstuff.dojo.markup.html.form.suggestionlist.SuggestionList;

public class SuggestionListSample extends WicketExamplePage {

	public static SuggestionList allItems = new SuggestionList();

	public SuggestionListSample(PageParameters parameters) {
		allItems.put("Alabama", "Alabama");
		allItems.put("Alaska", "Alaska");
		allItems.put("American Samoa", "American Samoa");
		allItems.put("Arizona", "Arizona");
		allItems.put("Arkansas", "Arkansas");
		allItems.put("California", "California");
		allItems.put("Colorado", "Colorado");
		allItems.put("Connecticut", "Connecticut");
		allItems.put("Delaware", "Delaware");
		allItems.put("Columbia", "Columbia");
		allItems.put("Florida", "Florida");
		allItems.put("Georgia", "Georgia");
		allItems.put("Guam", "Guam");
		allItems.put("Hawaii", "Hawaii");
		allItems.put("Idaho", "Idaho");
		allItems.put("Illinois", "Illinois");
		allItems.put("Indiana", "Indiana");
		allItems.put("Iowa", "Iowa");
		allItems.put("Kansas", "Kansas");
		allItems.put("Kentucky", "Kentucky");
		allItems.put("Louisiana", "Louisiana");
		allItems.put("Maine", "Maine");
		allItems.put("Marshall Islands", "Marshall Islands");

		new DojoRequestSuggestionList("list1") {

			public SuggestionList getMatchingValues(String pattern) {
				if (pattern.equals(""))
					return allItems;
				SuggestionList list = new SuggestionList();
				Iterator it = allItems.entrySet().iterator();
				while (it.hasNext()) {
					Entry item = (Entry) it.next();
					if (((String) item.getValue()).toLowerCase().startsWith(pattern.toLowerCase())) {
						list.put(item.getKey(), item.getValue());
					}
				}
				return list;
			}
		};

		ArrayList personList = new ArrayList();

		personList.add(new Person("JBQ", 1));
		personList.add(new Person("Eelco", 2));
		personList.add(new Person("Igor", 3));
		personList.add(new Person("Vincent", 4));

		final Label selectedPerson = new Label("selectedPerson");
		selectedPerson.setOutputMarkupId(true);
		DojoDropDownChoice choice = new DojoDropDownChoice("dropdown", personList, new ChoiceRenderer("name", "id")) {
			@Override
			public void onSetValue(AjaxRequestTarget target) {
				System.err.println("New value is " + getInput());
				selectedPerson.setModel(new Model(getInput()));
				target.addComponent(selectedPerson);
			}
		};
		choice.setHandleSelectionChange(true);
		add(choice);
		add(selectedPerson);
	}

	public class Person implements Serializable {
		private String name;

		private int id;

		public Person(String name, int id) {
			super();
			this.name = name;
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
