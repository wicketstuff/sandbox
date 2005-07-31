package wicket.examples.cdapp2.page;

import static wicket.examples.cdapp2.CdRequestCycle.COMPONENT_DAO;

import wicket.contrib.data.model.bind.*;
import wicket.contrib.data.model.hibernate.HibernateDataSource;
import wicket.examples.cdapp2.model.Album;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.util.value.ValueMap;

/**
 * The main and entry page for the application.
 * 
 * @author Phil Kulak
 */
public class BrowseAlbums extends CdPage {
	GridPanel gridPanel;
	
	public BrowseAlbums(String feedbackMessage) {
		this();
		info(feedbackMessage);
	}
	
	public BrowseAlbums() {
		super("Browse Albums");
		IListDataSource ds = new HibernateDataSource(Album.class, COMPONENT_DAO);
		
        // We have a feedback panel for our grid and our search form, each of
        // which is contained in it's own form.
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
			
            // Replaces the backing list with one that only contains the results
            // of the search.
			add(new Button("submit") {
				@Override
				public void onSubmit() {
					ValueMap model = (ValueMap) getParent().getModelObject();
					gridPanel.setList(getDao().searchAlbums(model.getString("term")));
				}
			});
			
            // Restors the GridPanel to it's origonal state.
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
