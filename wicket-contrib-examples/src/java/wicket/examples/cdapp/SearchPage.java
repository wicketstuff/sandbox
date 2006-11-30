/*
 * $Id$ $Revision$ $Date$
 * 
 * ==================================================================== Licensed
 * under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.examples.cdapp;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import wicket.MarkupContainer;
import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.examples.cdapp.model.CD;
import wicket.examples.cdapp.model.CdDao;
import wicket.extensions.behavior.SimpleAttributeModifier;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.navigation.paging.PagingNavigation;
import wicket.markup.html.navigation.paging.PagingNavigationLink;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.model.PropertyModel;


/**
 * Page that nests a search form and a pageable and sortable results table.
 * 
 * @author Eelco Hillenius
 */
public class SearchPage extends CdAppBasePage
{
	/**
	 * Custom table navigation class that adds extra labels.
	 */
	private static class CDTableNavigation extends PagingNavigation
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param id
		 *            the name of the component
		 * @param table
		 *            the table
		 */
		public CDTableNavigation(MarkupContainer parent, String id, PageableListView table)
		{
			super(parent, id, table);
		}

		/**
		 * @see wicket.markup.html.list.Loop#populateItem(wicket.markup.html.list.Loop.LoopItem)
		 */
		@Override
		protected void populateItem(final LoopItem iteration)
		{
			final PagingNavigationLink link = new PagingNavigationLink(iteration, "pageLink",
					pageable, iteration.getIteration());

			if (iteration.getIteration() > 0)
			{
				new Label(iteration, "separator", "|");
			}
			else
			{
				new Label(iteration, "separator", "");
			}
			new Label(link, "pageNumber", String.valueOf(iteration.getIteration() + 1));
			new Label(link, "pageLabel", "page");
		}
	}

	/** Link for deleting a row. */
	private final class DeleteLink extends Link<Long>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param name
		 *            name of the component
		 * @param cd
		 *            the cd
		 */
		public DeleteLink(MarkupContainer parent, String name, CD cd)
		{
			super(parent, name, new Model<Long>(cd.getId()));
			add(new SimpleAttributeModifier("onclick", "if(!confirm('delete cd " + cd.getTitle()
					+ " ?')) return false;"));
		}

		/**
		 * @see wicket.markup.html.link.Link#onClick()
		 */
		@Override
		public void onClick()
		{
			final Long id = getModelObject();

			CdDao cdDao = getCdDao();
			CD cd = null;
			try
			{
				cd = cdDao.load(id);
			}
			catch (HibernateException e)
			{
				// For some reason (back button, concurrent acces?) the object
				// does not exist
				// anymore. Report an error and return
				SearchPage.this.error("could not delete cd with id " + id
						+ "; it was not found in the database");
				return;
			}

			// inform the list component that a change in its model is about to
			// happen
			resultsListView.modelChanging();

			getCdDao().delete(cd);

			// infor the list component that a change in its model has happened
			resultsListView.modelChanged();

			SearchPage.this.info(" cd deleted");

		}
	}

	/** link to detail edit page. */
	private final class DetailLink extends Link<Long>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param name
		 *            name of the component
		 * @param id
		 *            the id of the cd
		 */
		public DetailLink(MarkupContainer parent, String name, Long id)
		{
			super(parent, name, new Model<Long>(id));
		}

		/**
		 * @see wicket.markup.html.link.Link#onClick()
		 */
		@Override
		public void onClick()
		{
			final RequestCycle requestCycle = getRequestCycle();
			final Long id = getModelObject();
			requestCycle.setResponsePage(new EditPage(SearchPage.this, id));
		}
	}

	/**
	 * Table for displaying search results.
	 */
	private class SearchCDResultsListView extends PageableListView<CD>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param id
		 *            id of the component
		 * @param model
		 *            the model
		 * @param pageSizeInCells
		 *            page size
		 */
		public SearchCDResultsListView(MarkupContainer parent, String id, IModel<List<CD>> model,
				int pageSizeInCells)
		{
			super(parent, id, model, pageSizeInCells);
		}

		/**
		 * @see wicket.Component#isVersioned()
		 */
		@Override
		public boolean isVersioned()
		{
			return true;
		}

		/**
		 * @see PageableListView#populateItem(ListItem)
		 * @param item
		 */
		@Override
		public void populateItem(final ListItem<CD> item)
		{
			final CD cd = item.getModelObject();
			final Long id = cd.getId();

			// add links to the details
			new Label(new DetailLink(item, "title", id), "title", cd.getTitle());
			new Label(new DetailLink(item, "performers", id), "performers", cd.getPerformers());
			new Label(new DetailLink(item, "label", id), "label", cd.getLabel());
			new Label(new DetailLink(item, "year", id), "year", (cd.getYear() != null) ? cd
					.getYear().toString() : "");

			// add a delete link for each found record
			DeleteLink deleteLink = new DeleteLink(item, "delete", cd);
		}
	}

	/**
	 * Form for search actions.
	 */
	private class SearchForm extends Form
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/** search property to set. */
		private String search;

		/**
		 * Constructor
		 * 
		 * @param parent
		 *            The parent
		 * @param id
		 *            id of the form component
		 */
		public SearchForm(MarkupContainer parent, final String id)
		{
			super(parent, id);
			new TextField<String>(this, "search", new PropertyModel<String>(this, "search"));
		}

		/**
		 * Gets search property.
		 * 
		 * @return search property
		 */
		public final String getSearch()
		{
			return search;
		}

		/**
		 * @see wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit()
		{
			searchModel.setSearchString(search); // set search query on model
			setCurrentResultPageToFirst(); // start with first page
			// SearchPage.this.modelChangedStructure();

			if (search != null && (!search.trim().equals("")))
			{
				info(getNumberOfResults() + " results found for query '" + search + "'");
			}
		}

		/**
		 * Sets search property.
		 * 
		 * @param search
		 *            search property
		 */
		public final void setSearch(String search)
		{
			this.search = search;
		}
	}

	/** Link for sorting on a column. */
	private final class SortLink extends Link
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/** order by field. */
		private final String field;

		/**
		 * Construct.
		 * 
		 * @param parent
		 *            The parent
		 * @param id
		 *            name of component
		 * @param field
		 *            order by field
		 */
		public SortLink(MarkupContainer parent, String id, String field)
		{
			super(parent, id);
			this.field = field;
		}

		/**
		 * Add order by field to query of list.
		 * 
		 * @see wicket.markup.html.link.Link#onClick()
		 */
		@Override
		public void onClick()
		{
			searchModel.addOrdering(field);
			// SearchPage.this.modelChangedStructure();
		}
	}

	/** Logger. */
	private static Log log = LogFactory.getLog(SearchPage.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** list view for search results. */
	private SearchCDResultsListView resultsListView;

	/** search form. */
	private final SearchForm searchForm;

	/** model for searching. */
	private final SearchModel searchModel;

	/**
	 * Construct.
	 */
	public SearchPage()
	{
		this(null);
	}

	/**
	 * Construct.
	 * 
	 * @param pageParameters
	 *            parameters for this page
	 */
	public SearchPage(PageParameters pageParameters)
	{
		super();
		final int rowsPerPage = 8;
		searchModel = new SearchModel(rowsPerPage);

		FeedbackPanel pageFeedback = new FeedbackPanel(this, "feedback");
		searchForm = new SearchForm(this, "searchForm");
		resultsListView = new SearchCDResultsListView(this, "results", searchModel, rowsPerPage);
		WebMarkupContainer resultsTableHeader = new WebMarkupContainer(this, "resultsHeader")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible()
			{
				return searchModel.hasResults();
			}
		};
		new SortLink(resultsTableHeader, "sortOnArtist", "performers");
		new SortLink(resultsTableHeader, "sortOnTitle", "title");
		new SortLink(resultsTableHeader, "sortOnYear", "year");
		new SortLink(resultsTableHeader, "sortOnLabel", "label");
		resultsTableHeader.setVisible(false); // non-visible as there are no
		new DetailLink(this, "newCdLink", null); // add with null; the model
		// and
		// the detail page are smart enough to know we want a new one then
		new CDTableNavigation(this, "navigation", resultsListView);
	}

	/**
	 * Sets the result page to the first page.
	 */
	public void setCurrentResultPageToFirst()
	{
		resultsListView.setCurrentPage(0);
	}

	/**
	 * Gets the current number of results.
	 * 
	 * @return the current number of results
	 */
	private int getNumberOfResults()
	{
		return ((List)resultsListView.getModelObject()).size();
	}
}