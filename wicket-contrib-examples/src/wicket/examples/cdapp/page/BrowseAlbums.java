package wicket.examples.cdapp.page;

import static wicket.examples.cdapp.CdRequestCycle.COMPONENT_DAO;

import wicket.contrib.data.model.bind.*;
import wicket.contrib.data.model.hibernate.HibernateDataSource;
import org.apache.wicket.examples.cdapp.model.Album;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class BrowseAlbums extends CdPage {
	GridPanel gridPanel;
	
	public BrowseAlbums(String feedbackMessage) {
		this();
		info(feedbackMessage);
	}
	
	public BrowseAlbums() {
		super("Browse Albums");
		IListDataSource ds = new HibernateDataSource(Album.class, COMPONENT_DAO);
		
		FeedbackPanel pageFeedback = new FeedbackPanel("pageFeedback");
		FeedbackPanel gridFeedback = new FeedbackPanel("gridFeedback");
		add(pageFeedback);
		add(gridFeedback);
		
		add(new SearchForm());
		add(gridPanel = new GridPanel("allCustomers", ds, 5, null, gridFeedback));
		add(new Link("newAlbum") {
			@Override
			public void onClick() {
				setResponsePage(newPage(NewAlbum.class));
			}
		});
	}
	
	private class SearchForm extends Form {		
		public SearchForm() {
			super("searchForm", new CompoundPropertyModel(new ValueMap()), null);
			add(new TextField("term"));
			
			add(new Button("submit") {
				@Override
				public void onSubmit() {
					ValueMap model = (ValueMap) getParent().getModelObject();
					gridPanel.setList(getDao().searchAlbums(model.getString("term")));
				}
			});
			
			add(new Button("clear") {
				@Override
				public void onSubmit() {
					ValueMap model = (ValueMap) getParent().getModelObject();
					model.remove("term");
					gridPanel.setList(getDao().findAllAlbums());
				}
			});
		}
	}
}
