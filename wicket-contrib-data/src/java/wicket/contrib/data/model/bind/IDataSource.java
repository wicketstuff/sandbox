package wicket.contrib.data.model.bind;

import java.util.List;

import wicket.model.IModel;

/**
 * Provides all the basic functionality needed for interacting with a datastore.
 * 
 * @author Phil Kulak
 */
public interface IDataSource<T>
{
	/**
	 * Saves or updates the persistent state of the given object.
	 */
	public void merge(T entity);

	/**
	 * Deletes the object from the database.
	 * 
	 * @param entity
	 *            the object to delete
	 */
	public void delete(T entity);

	/**
	 * @param c
	 *            the class of the object to search for
	 * @return all persistent instances of the class
	 */
	public List<T> findAll(Class<T> c);

	/**
	 * Wraps the given object in an IModel. The model returned MUST properly
	 * override equals().
	 * 
	 * @param entity
	 *            the object to wrap
	 * @return the object wraped in a detachable model
	 */
	public IModel<T> wrap(T entity);
}
