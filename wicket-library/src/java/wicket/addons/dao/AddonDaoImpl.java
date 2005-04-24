/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * @author Juergen Donnerstag
 */
public final class AddonDaoImpl extends HibernateDaoSupport
{
    public final List getCountByCategory() 
    {
        final List categories = getCategories();
        
        List data = getHibernateTemplate().find(
            	"select a.category.id, count(a) from Addon as a group by a.category.id");

        final Iterator iterCat = categories.iterator();
        while (iterCat.hasNext())
        {
            final Category cat = (Category) iterCat.next();

            boolean hit = false;
	        final Iterator iter = data.iterator();
	        while (iter.hasNext())
	        {
		        final Object[] elem = (Object[])iter.next();
		        final int categoryId = ((Integer)elem[0]).intValue();
		        final int count = ((Integer)elem[1]).intValue();

		        if (cat.getId() == categoryId)
	            {
	    	        cat.setCount(count);
	    	        hit = true;
	    	        break;
	            }
	        }
	        
	        if (hit == false)
	        {
    	        cat.setCount(0);
	        }
        }
        
        return categories;
    }
    
    public final List getCategories()
    {
        return getHibernateTemplate().find("from Category c where c.deleted is null order by c.name");
    }
    
    public final List getRatingsByAddon(final Addon addon)
    {
        return getHibernateTemplate().find("from Rating r where r.addon=?", addon);
    }
    
    public final List getLicenseNames()
    {
        return getHibernateTemplate().find("select distinct a.license from Addon a where a.enabled != 0");
    }
    
    public final List getAllCategories()
    {
        return getHibernateTemplate().find("from Category c order by c.name");
    }
    
    public final Integer getAddonCount()
    {
        return (Integer)getHibernateTemplate().find("select count(a) from Addon a where a.enabled != 0").get(0);
    }
    
    public final Integer getCommentCount(final Addon addon)
    {
        return (Integer)getHibernateTemplate().find("select count(a) from Comment a where a.addon=?", addon).get(0);
    }
    
    public final List getMyRatings(final User user)
    {
        return getHibernateTemplate().find("from Rating r where r.user=? order by r.createDate desc", user);
    }
    
    public final List getAddonsByRating(final int minRatings, final int categoryId)
    {
        // Hibernate and MySql are argueing whos fault it is. Hibernate does not support attribute aliases and
        // MySql does not support aggreate functions in order by. Using x1_0_ is just a work around. x1_0_ is the
        // attribute alias automatically assigned by hibernate.
        //final Query query = session.createQuery("select c.addon, count(c) from Click c group by c.addon order by count(c) desc");
        if (categoryId < 0)
        {
            return getHibernateTemplate().find("from Addon a where (a.enabled != 0) order by a.averageRating desc");
        }

        return getHibernateTemplate().find("from Addon a where (a.enabled != 0) and (a.category.id = ?) order by a.averageRating desc", new Integer(categoryId));
    }
    
    /**
     * 
     * @param minRatings
     * @param categoryId
     * @param sortOrder 0 - 24H, 1 - week, 2 - month, 3 - all
     * @return
     */
    public final List getAddonsByClicks(final int minRatings, final int categoryId, final int sortOrder)
    {
        // Hibernate and MySql are argueing whos fault it is. Hibernate does not support attribute aliases and
        // MySql does not support aggreate functions in order by. Using x1_0_ is just a work around. x1_0_ is the
        // attribute alias automatically assigned by hibernate.
        //final Query query = session.createQuery("select c.addon, count(c) from Click c group by c.addon order by count(c) desc");
        if (categoryId < 0)
        {
            return getHibernateTemplate().find("from Addon a where (a.enabled != 0) order by a.clicksLast24H desc");
        }

        return getHibernateTemplate().find("from Addon a where (a.enabled != 0) and (a.category.id = ?) order by a.clicksLast24H desc", new Integer(categoryId));
    }
    
    public final Object[] getRatingCountAndAverage(final Addon addon)
    {
        return (Object[])getHibernateTemplate().find("select count(r), avg(r.rating) from Rating r where r.addon=?", addon).get(0);
    }
    
    public final List getRatingChartData(final Addon addon)
    {
        return getHibernateTemplate().find("select r.rating, count(r) from Rating r where r.addon=? group by r.rating order by r.rating", addon);
    }
    
    public final List getTop5AddonsByRating()
    {
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());

        return (List) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
            	        final Query query = session.createQuery("from Addon a order by a.averageRating desc");
            	        query.setMaxResults(5);
                        return query.list();
                    }
                }
            );
    }
    
    public final List getTop5AddonsByClicks()
    {
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());

        return (List) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
                        // Hibernate and MySql are argueing whos fault it is. Hibernate does not support attribute aliases and
                        // MySql does not support aggreate functions in order by. Using x1_0_ is just a work around. x1_0_ is the
                        // attribute alias automatically assigned by hibernate.
            	        //final Query query = session.createQuery("select c.addon, count(c) from Click c group by c.addon order by count(c) desc");
            	        final Query query = session.createQuery("from Addon a order by a.clicksLastWeek desc");
            	        query.setMaxResults(5);
                        return query.list();
                    }
                }
            );
    }
    
    public void updateAddonAvgRating()
    {
        final List data = getHibernateTemplate().find("select r.addon, avg(r.rating) from Rating r group by r.addon");
        final Iterator iter = data.iterator();
        while (iter.hasNext())
        {
            final Object[] obj = (Object[])iter.next();
            final Addon addon = (Addon) obj[0];
            final double rating = ((Float) obj[1]).doubleValue();
            addon.setAverageRating(rating);
            saveOrUpdate(addon);
        }
    }
    
    public void updateAddonClicksStatistics()
    {
        // SELECT c.addonid, c.timestamp, timediff(now(), c.timestamp) FROM clicks c where hour(timediff(now(), c.timestamp)) < 24;
        List data24H = getHibernateTemplate().find("select c.addon.id, count(c) from Click c where hour(timediff(now(), c.timestamp)) < 24 group by c.addon order by c.addon.id");
        List dataWeek = getHibernateTemplate().find("select c.addon.id, count(c) from Click c where hour(timediff(now(), c.timestamp)) < (24*7) group by c.addon order by c.addon.id");
        List dataMonth = getHibernateTemplate().find("select c.addon.id, count(c) from Click c where hour(timediff(now(), c.timestamp)) < (24*30) group by c.addon order by c.addon.id");
        List dataAll = getHibernateTemplate().find("select c.addon.id, count(c) from Click c group by c.addon order by c.addon.id");
        List addons = getHibernateTemplate().find("from Addon a order by a.id");
        
        int i24H = 0;
        int iWeek = 0;
        int iMonth = 0;
        int iAll = 0;
        
        final Iterator iter = addons.iterator();
        while (iter.hasNext())
        {
            final Addon addon = (Addon)iter.next();
            
            if (i24H < data24H.size())
            {
                final Object[] obj = (Object[]) data24H.get(i24H);
                final int id = ((Integer)obj[0]).intValue();
                final int count = ((Integer)obj[1]).intValue();
                
                if (addon.getId() == id)
                {
                    if (addon.getClicksLast24H() != count)
                    {
                        addon.setClicksLast24H(count);
                    }
                    i24H ++;
                }
                else
                {
                    addon.setClicksLast24H(0);
                }
            }
            
            if (iWeek < dataWeek.size())
            {
                final Object[] obj = (Object[]) dataWeek.get(iWeek);
                final int id = ((Integer)obj[0]).intValue();
                final int count = ((Integer)obj[1]).intValue();
                
                if (addon.getId() == id)
                {
                    if (addon.getClicksLastWeek() != count)
                    {
                        addon.setClicksLastWeek(count);
                    }
                    iWeek ++;
                }
                else
                {
                    addon.setClicksLastWeek(0);
                }
            }
            
            if (iMonth < dataMonth.size())
            {
                final Object[] obj = (Object[]) dataMonth.get(iMonth);
                final int id = ((Integer)obj[0]).intValue();
                final int count = ((Integer)obj[1]).intValue();
                
                if (addon.getId() == id)
                {
                    if (addon.getClicksLastMonth() != count)
                    {
                        addon.setClicksLastMonth(count);
                    }
                    iMonth ++;
                }
                else
                {
                    addon.setClicksLastMonth(0);
                }
            }
            
            if (iAll < dataMonth.size())
            {
                final Object[] obj = (Object[]) dataAll.get(iAll);
                final int id = ((Integer)obj[0]).intValue();
                final int count = ((Integer)obj[1]).intValue();
                
                if (addon.getId() == id)
                {
                    if (addon.getClicksAll() != count)
                    {
                        addon.setClicksAll(count);
                    }
                    iAll ++;
                }
                else
                {
                    addon.setClicksAll(0);
                }
            }
            
            saveOrUpdate(addon);
        }
    }
    
    public final List getNews(final int pos, final int anz)
    {
        return getSubSequence("from News n order by n.lastModified desc", pos, anz);
    }

    public final Integer getAddonCountByCategory(final int categoryId)
    {
        String sql = "select count(a) from Addon a where (a.enabled != 0)";
        
        if (categoryId > 0)
        {
            sql += " and (a.category.id=?)";
            return (Integer)getHibernateTemplate().find(sql, new Integer(categoryId)).get(0);
        }
        else
        {
            return (Integer)getHibernateTemplate().find(sql).get(0);
        }
    }
    
    public final List getUpdatedAddons(final int pos, final int anz, final int categoryId, final int sortOrder)
    {
        String sql = "from Addon a where (a.enabled != 0)";
        
        if (categoryId >= 0)
        {
            sql += " and (a.category.id=?)";
        }
        
        if (sortOrder >= 0)
        {
            sql += " order by a.";
            switch (sortOrder)
            {
            	case 0: sql += "name"; break;
            	case 1: sql += "license"; break;
            	case 2: sql += "createTime desc"; break;
            	case 3: sql += "lastModified desc"; break;
            	case 4: sql += "rating desc"; break;
            	case 5: sql += "popularity desc"; break;
            }
        }
        
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());
        final String sqlStmt = sql;
        
        return (List) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
            	        final Query query = session.createQuery(sqlStmt);
            	        if (categoryId >= 0)
            	        {
            	            query.setInteger(0, categoryId);
            	        }
        
            	        query.setFirstResult(pos);
            	        query.setMaxResults(anz);
            	        
                        return query.list();
                    }
                }
            );
    }
    
    private final List getSubSequence(final String sql, final int pos, final int anz)
    {
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(this.getSessionFactory());

        return (List) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
            	        final Query query = session.createQuery(sql);
            	        query.setFetchSize(anz);
            	        query.setFirstResult(pos);
                        return query.list();
                    }
                }
            );
    }
    
    public void delete(final Object object)
    {
        if (object instanceof IDeleted)
        {
            ((IDeleted)object).setDeleted(new Timestamp(System.currentTimeMillis()));

            // LastModified nur updaten wenn NULL
            if ((object instanceof ILastModified) && (((ILastModified)object).getLastModified() == null))
            {
                ((ILastModified)object).setLastModified(new Timestamp(System.currentTimeMillis()));
            }

            getHibernateTemplate().saveOrUpdate(object);            
            return;
        }
            
        if (object instanceof ILastModified)
        {
            ((ILastModified)object).setLastModified(new Timestamp(System.currentTimeMillis()));
        }
        
        getHibernateTemplate().delete(object);
    }
    
    public void saveOrUpdate(final Object object)
    {
        if (object instanceof ILastModified)
        {
            ((ILastModified)object).setLastModified(new Timestamp(System.currentTimeMillis()));
        }
        
        getHibernateTemplate().saveOrUpdate(object);
    }
    
    public Object load(final Class clazz, final Serializable id)
    {
        return getHibernateTemplate().load(clazz, id);
    }
    
    public Object load(final IIdentifiable obj)
    {
        return getHibernateTemplate().load(obj.getClass(), new Integer(obj.getId()));
    }

    public void update(final Object object)
    {
        getHibernateTemplate().update(object);
    }
    
    public void initialize(final Object object)
    {
        getHibernateTemplate().initialize(object);
    }
    
    public void merge(final Object object)
    {
        getHibernateTemplate().merge(object);
    }
}
