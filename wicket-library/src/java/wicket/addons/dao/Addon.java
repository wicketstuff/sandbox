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
import java.util.List;

/**
 * @author Juergen Donnerstag
 */
public class Addon implements Serializable
{
	private int id;

	private String name;

	private Category category;

	private String license;

	private Timestamp createTime;

	private Timestamp lastModified;

	private String homepage;

	private String downloadUrl;

	private User owner;

	private String version;

	private String wicketVersion;

	private String description;

	private int enabled;

	// Summe aller Clicks
	private int clickCount;

	private List comments;

	private List ratings;
	private List clicks;

	private int clicksLast24H;
	private int clicksLastWeek;
	private int clicksLastMonth;
	private int clicksAll;

	private double averageRating;

	/**
	 * @return Returns the categoryId.
	 */
	public Category getCategory()
	{
		return category;
	}

	/**
	 * @param categoryId
	 *           The categoryId to set.
	 */
	public void setCategory(Category category)
	{
		this.category = category;
	}

	/**
	 * @return Returns the createTime.
	 */
	public Timestamp getCreateTime()
	{
		if (createTime == null)
		{
			return new Timestamp(0);
		}
		return createTime;
	}

	/**
	 * @param createTime
	 *           The createTime to set.
	 */
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *           The description to set.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return Returns the homepage.
	 */
	public String getHomepage()
	{
		return homepage;
	}

	/**
	 * @param homepage
	 *           The homepage to set.
	 */
	public void setHomepage(String homepage)
	{
		this.homepage = homepage;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id
	 *           The id to set.
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return Returns the lastModified.
	 */
	public Timestamp getLastModified()
	{
		if (lastModified == null)
		{
			return new Timestamp(0);
		}
		return lastModified;
	}

	/**
	 * @param lastModified
	 *           The lastModified to set.
	 */
	public void setLastModified(Timestamp lastModified)
	{
		this.lastModified = lastModified;
	}

	/**
	 * @return Returns the license.
	 */
	public String getLicense()
	{
		return license;
	}

	/**
	 * @param license
	 *           The license to set.
	 */
	public void setLicense(String license)
	{
		this.license = license;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the ownerId.
	 */
	public User getOwner()
	{
		return owner;
	}

	/**
	 * @param ownerId
	 *           The ownerId to set.
	 */
	public void setOwner(final User user)
	{
		this.owner = user;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version
	 *           The version to set.
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}

	/**
	 * @return Returns the wicketVersion.
	 */
	public String getWicketVersion()
	{
		return wicketVersion;
	}

	/**
	 * @param wicketVersion
	 *           The wicketVersion to set.
	 */
	public void setWicketVersion(String wicketVersion)
	{
		this.wicketVersion = wicketVersion;
	}

	public Addon()
	{
		; // empty
	}

	/**
	 * @return Returns the enabled.
	 */
	public int getEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled
	 *           The enabled to set.
	 */
	public void setEnabled(int enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * @return Returns the downloadUrl.
	 */
	public String getDownloadUrl()
	{
		return downloadUrl;
	}

	/**
	 * @param downloadUrl
	 *           The downloadUrl to set.
	 */
	public void setDownloadUrl(String downloadUrl)
	{
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return Returns the comments.
	 */
	public List getComments()
	{
		return comments;
	}

	/**
	 * @param comments
	 *           The comments to set.
	 */
	public void setComments(List comments)
	{
		this.comments = comments;
	}

	/**
	 * @return Returns the ratings.
	 */
	public List getRatings()
	{
		return ratings;
	}

	/**
	 * @param ratings
	 *           The ratings to set.
	 */
	public void setRatings(List ratings)
	{
		this.ratings = ratings;
	}

	/**
	 * @return Returns the averageRating.
	 */
	public double getAverageRating()
	{
		return averageRating;
	}

	/**
	 * @param averageRating
	 *           The averageRating to set.
	 */
	public void setAverageRating(double averageRating)
	{
		this.averageRating = averageRating;
	}

	/**
	 * @return Returns the clickCount.
	 */
	public int getClickCount()
	{
		return clickCount;
	}

	/**
	 * @param clickCount
	 *           The clickCount to set.
	 */
	public void setClickCount(int clickCount)
	{
		this.clickCount = clickCount;
	}

	/**
	 * @return Returns the clicks.
	 */
	public List getClicks()
	{
		return clicks;
	}

	/**
	 * @param clicks
	 *           The clicks to set.
	 */
	public void setClicks(List clicks)
	{
		this.clicks = clicks;
	}

	/**
	 * @return Returns the clicksAll.
	 */
	public int getClicksAll()
	{
		return clicksAll;
	}

	/**
	 * @param clicksAll
	 *           The clicksAll to set.
	 */
	public void setClicksAll(int clicksAll)
	{
		this.clicksAll = clicksAll;
	}

	/**
	 * @return Returns the clicksLast24H.
	 */
	public int getClicksLast24H()
	{
		return clicksLast24H;
	}

	/**
	 * @param clicksLast24H
	 *           The clicksLast24H to set.
	 */
	public void setClicksLast24H(int clicksLast24H)
	{
		this.clicksLast24H = clicksLast24H;
	}

	/**
	 * @return Returns the clicksLastMonth.
	 */
	public int getClicksLastMonth()
	{
		return clicksLastMonth;
	}

	/**
	 * @param clicksLastMonth
	 *           The clicksLastMonth to set.
	 */
	public void setClicksLastMonth(int clicksLastMonth)
	{
		this.clicksLastMonth = clicksLastMonth;
	}

	/**
	 * @return Returns the clicksLastWeek.
	 */
	public int getClicksLastWeek()
	{
		return clicksLastWeek;
	}

	/**
	 * @param clicksLastWeek
	 *           The clicksLastWeek to set.
	 */
	public void setClicksLastWeek(int clicksLastWeek)
	{
		this.clicksLastWeek = clicksLastWeek;
	}
}
