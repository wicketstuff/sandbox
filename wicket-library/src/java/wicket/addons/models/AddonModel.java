/*
 * $Id$ $Revision$
 * $Date$
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
package wicket.addons.models;

import java.sql.Timestamp;
import java.util.Collection;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import wicket.addons.ServiceLocator;
import wicket.addons.db.Addon;
import wicket.addons.db.Category;
import wicket.addons.db.User;

/**
 * @author Juergen Donnerstag
 */
public class AddonModel
{
	private Addon addon;

	public AddonModel(final Addon addon)
	{
		this.addon = addon;
	}

	public boolean equals(Object obj)
	{
		return addon.equals(obj);
	}

	public Double getAverageRating()
	{
		return addon.getAverageRating();
	}

	public Category getCategory()
	{
		return addon.getCategory();
	}

	public Collection getClicks()
	{
		return addon.getClicks();
	}

	public int getClicksAll()
	{
		return addon.getClicksAll();
	}

	public int getClicksLast24H()
	{
		return addon.getClicksLast24H();
	}

	public int getClicksLastMonth()
	{
		return addon.getClicksLastMonth();
	}

	public int getClicksLastWeek()
	{
		return addon.getClicksLastWeek();
	}

	public Collection getComments()
	{
		if (!Hibernate.isInitialized(addon.getComments()))
		{
			try
			{
				Hibernate.initialize(addon.getComments());
			}
			catch (HibernateException ex)
			{
				this.addon = (Addon)ServiceLocator.instance().getHibernateService().load(
						addon.getClass(), addon.getId());
			}
		}

		return addon.getComments();
	}

	public Timestamp getCreateTime()
	{
		return addon.getCreateTime();
	}

	public String getDescription()
	{
		return addon.getDescription();
	}

	public String getDownloadUrl()
	{
		return addon.getDownloadUrl();
	}

	public Timestamp getEndDate()
	{
		return addon.getEndDate();
	}

	public String getHomepage()
	{
		return addon.getHomepage();
	}

	public Long getId()
	{
		return addon.getId();
	}

	public Timestamp getLastUpdated()
	{
		return addon.getLastUpdated();
	}

	public String getLicense()
	{
		return addon.getLicense();
	}

	public String getName()
	{
		return addon.getName();
	}

	public User getOwner()
	{
		return addon.getOwner();
	}

	public Collection getRatings()
	{
		if (!Hibernate.isInitialized(addon.getRatings()))
		{
			try
			{
				Hibernate.initialize(addon.getRatings());
			}
			catch (HibernateException ex)
			{
				this.addon = (Addon)ServiceLocator.instance().getHibernateService().load(
						addon.getClass(), addon.getId());
			}
		}

		return addon.getRatings();
	}

	public Timestamp getStartDate()
	{
		return addon.getStartDate();
	}

	public String getVersion()
	{
		return addon.getVersion();
	}

	public String getWicketVersion()
	{
		return addon.getWicketVersion();
	}

	public int hashCode()
	{
		return addon.hashCode();
	}

	public boolean isEnable()
	{
		return addon.isEnable();
	}

	public void setAverageRating(Double averageRating)
	{
		addon.setAverageRating(averageRating);
	}

	public void setCategory(Category category)
	{
		addon.setCategory(category);
	}

	public void setClicks(Collection clicks)
	{
		addon.setClicks(clicks);
	}

	public void setClicksAll(int clicksAll)
	{
		addon.setClicksAll(clicksAll);
	}

	public void setClicksLast24H(int clicksLast24H)
	{
		addon.setClicksLast24H(clicksLast24H);
	}

	public void setClicksLastMonth(int clicksLastMonth)
	{
		addon.setClicksLastMonth(clicksLastMonth);
	}

	public void setClicksLastWeek(int clicksLastWeek)
	{
		addon.setClicksLastWeek(clicksLastWeek);
	}

	public void setComments(Collection comments)
	{
		addon.setComments(comments);
	}

	public void setCreateTime(Timestamp createTime)
	{
		addon.setCreateTime(createTime);
	}

	public void setDescription(String description)
	{
		addon.setDescription(description);
	}

	public void setDownloadUrl(String downloadUrl)
	{
		addon.setDownloadUrl(downloadUrl);
	}

	public void setEnable(boolean enable)
	{
		addon.setEnable(enable);
	}

	public void setEndDate(Timestamp endDate)
	{
		addon.setEndDate(endDate);
	}

	public void setHomepage(String homepage)
	{
		addon.setHomepage(homepage);
	}

	public void setId(Long id)
	{
		addon.setId(id);
	}

	public void setLastUpdated(Timestamp lastUpdated)
	{
		addon.setLastUpdated(lastUpdated);
	}

	public void setLicense(String license)
	{
		addon.setLicense(license);
	}

	public void setName(String name)
	{
		addon.setName(name);
	}

	public void setOwner(User owner)
	{
		addon.setOwner(owner);
	}

	public void setRatings(Collection ratings)
	{
		addon.setRatings(ratings);
	}

	public void setStartDate(Timestamp startDate)
	{
		addon.setStartDate(startDate);
	}

	public void setVersion(String version)
	{
		addon.setVersion(version);
	}

	public void setWicketVersion(String wicketVersion)
	{
		addon.setWicketVersion(wicketVersion);
	}

	public String toString()
	{
		return addon.toString();
	}
}
