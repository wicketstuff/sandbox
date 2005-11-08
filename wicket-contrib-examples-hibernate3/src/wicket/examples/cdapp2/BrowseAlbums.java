package wicket.examples.cdapp2;

import java.util.List;

import wicket.contrib.data.model.OrderedPageableList;
import wicket.contrib.data.model.bind.GridPanel;
import wicket.contrib.data.model.bind.IListDataSource;
import wicket.examples.cdapp2.page.CdPage;
import wicket.examples.cdapp2.page.NewAlbum;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.IModel;
import wicket.util.value.ValueMap;

/**
 * The main and entry page for the application.
 * 
 * @author Phil Kulak
 */
public class BrowseAlbums extends CdPage {
	private GridPanel gridPanel;

	public BrowseAlbums(String feedbackMessage) {
		this();
		info(feedbackMessage);
	}

	public BrowseAlbums() {
		super("Browse Albums");

		List list = getDao().findAllAlbums();
		IListDataSource ds = new IListDataSource() {
			public OrderedPageableList getList() {
				return new OrderedPageableList() {
					@Override
					protected List getItems(int start, int max, List ordering) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					protected int getCount() {
						// TODO Auto-generated method stub
						return 0;
					}
				};
			}

			public List getFields() {
				// TODO Auto-generated method stub
				return null;
			}

			public void merge(Object entity) {
				merge(entity);
			}

			public void delete(Object entity) {
				delete(entity);
			}

			public List findAll(Class c) {
				return getDao().findAllAlbums();
			}

			public IModel wrap(Object entity) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		// IModel model = newModel(Album.class, new Long(1));
		// Album album = (Album) model.getObject(null);
		// album.toString();

		// We have a feedback panel for our grid and our search form, each of
		// which is contained in it's own form.
		FeedbackPanel pageFeedback = new FeedbackPanel("pageFeedback");
		FeedbackPanel gridFeedback = new FeedbackPanel("gridFeedback");
		add(pageFeedback);
		add(gridFeedback);

		add(new SearchForm());
		add(gridPanel = new GridPanel("allCustomers", ds, 5, null));
		add(new Link("newAlbum") {
			@Override
			public void onClick() {
				setResponsePage(newPage(NewAlbum.class));
			}
		});
	}

	private class SearchForm extends Form {
		public SearchForm() {
			super("searchForm"); // , new CompoundPropertyModel(new
			// ValueMap()), null);
			add(new TextField("term"));

			// Replaces the backing list with one that only contains the results
			// of the search.
			add(new Button("submit") {
				@Override
				public void onSubmit() {
					ValueMap model = (ValueMap) getParent().getModelObject();
					gridPanel.setList(getDao().searchAlbums(
							model.getString("term")));
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
