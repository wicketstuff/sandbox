package contrib.wicket.cms.model;

import java.util.Date;

public interface Updatable {
	public void setUpdatedDate(Date updatedDate);

	public Date getUpdatedDate();
}
