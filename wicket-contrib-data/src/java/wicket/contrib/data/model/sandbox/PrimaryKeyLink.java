package wicket.contrib.data.model.sandbox;

import wicket.markup.html.link.Link;

/**
 * A convenience class that stores the primary key of the object it 
 * will later work with. This avoids storing the entire object
 * between requests.
 * 
 * @author Phil Kulak
 */
public abstract class PrimaryKeyLink extends Link
{
	private int primaryKey;
	
	/**
	 * Constructs a new PrimaryKeyLink using the id and primary key.
	 * 
	 * @param id the id of this link
	 * @param primaryKey the primary key of the object to use later
	 */
	public PrimaryKeyLink(String id, int primaryKey) {
		super(id);
		this.primaryKey = primaryKey;
	}
	
	protected int getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * @see wicket.markup.html.link.Link#onClick()
	 */
	public final void onClick() {
		onClick(getPrimaryKey());
	}
	
	protected abstract void onClick(int primaryKey);
}
