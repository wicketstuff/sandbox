/*
 * Created on 22.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package wicket.addons.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Juergen Donnerstag
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IAddonDao
{
	public abstract List getCountByCategory();

	public abstract List getCategories();

	public abstract List getRatingsByAddon(final Addon addon);

	public abstract List getLicenseNames();

	public abstract List getAllCategories();

	public abstract Integer getAddonCount();

	public abstract Integer getCommentCount(final Addon addon);

	public abstract List getMyRatings(final User user);

	public abstract List getAddonsByRating(final int minRatings, final int categoryId);

	/**
	 * 
	 * @param minRatings
	 * @param categoryId
	 * @param sortOrder 0 - 24H, 1 - week, 2 - month, 3 - all
	 * @return
	 */
	public abstract List getAddonsByClicks(final int minRatings, final int categoryId,
			final int sortOrder);

	public abstract Object[] getRatingCountAndAverage(final Addon addon);

	public abstract List getRatingChartData(final Addon addon);

	public abstract List getTop5AddonsByRating();

	public abstract List getTop5AddonsByClicks();

	public abstract void updateAddonAvgRating();

	public abstract void updateAddonClicksStatistics();

	public abstract List getNews(final int pos, final int anz);

	public abstract Integer getAddonCountByCategory(final int categoryId);

	public abstract List getUpdatedAddons(final int pos, final int anz, final int categoryId,
			final int sortOrder);

	public abstract void delete(final Object object);

	public abstract void saveOrUpdate(final Object object);

	public abstract Object load(final Class clazz, final Serializable id);

	public abstract Object load(final IIdentifiable obj);

	public abstract void update(final Object object);

	public abstract void initialize(final Object object);

	public abstract void merge(final Object object);

    public abstract void changePassword(final int userId, final String password);

    public abstract List searchAddon(final String searchText, int maxCount);
}