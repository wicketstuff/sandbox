package wicket.contrib.data.model.sandbox;

import wicket.markup.html.list.PageableListView;

/**
 * A convenience class for deleting an item from a
 * {@link wicket.markup.html.list.PageableListView} using it's
 * primary key.
 * 
 * @author Phil Kulak
 */
public abstract class ListViewDeleteLink extends PrimaryKeyLink
{
	private PageableListView listView;
	
	/**
	 * Constuctor.
	 * 
	 * @param id the id of the link
	 * @param primaryKey the primary key of the object to delete later
	 * @param listView the list view the object is being displayed in
	 */
	public ListViewDeleteLink(String id, int primaryKey,
			PageableListView listView) {
		super(id, primaryKey);
		this.listView = listView;
	}
	
	/**
	 * @see wicket.contrib.data.model.sandbox.PrimaryKeyLink#onClick(int)
	 */
	public void onClick(int primaryKey) {
		deleteItem(primaryKey);
		
		// Refresh the page of the list view in case the one we are
		// looking at is no longer valid.
		listView.setCurrentPage(listView.getCurrentPage());
	}
	
	protected abstract void deleteItem(int primaryKey);
}
