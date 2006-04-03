package contrib.wicket.cms.service.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentHistory;
import contrib.wicket.cms.model.ContentType;
import contrib.wicket.cms.service.ContentService;

@Transactional
public class ContentHibernateService extends GenericHibernateService implements
		ContentService {

	public Content getRootFolder() {
		return (Content) session().load(Content.class, Content.ROOT);
	}

	public Content getContent(Integer contentId) {
		return (Content) session().load(Content.class, contentId);
	}

	public List<Content> getContents(Integer folderId) {
		return session().createCriteria(Content.class).add(
				Restrictions.eq("folderId", folderId)).list();
	}

	public Content findFolderByPath(String[] path) {

		Criteria criteria = session().createCriteria(Content.class);

		Criteria appendingCriteria = criteria;
		for (int i = 0; i < path.length; i++) {
			appendingCriteria = appendingCriteria.add(Restrictions.eq("name",
					path[i]));
		}

		return (Content) criteria.uniqueResult();
	}

	public Content findContentByName(Content folder, String name) {
		Criteria criteria = session().createCriteria(Content.class);
		criteria.add(Restrictions.eq("name", name)).add(
				Restrictions.eq("folder", folder));
		return (Content) criteria.uniqueResult();
	}

	public ContentType getContentType(Integer contentTypeId) {
		return (ContentType) session().load(ContentType.class, contentTypeId);
	}

	public void saveContent(Content content) {
		boolean isNew = false;

		if (content.getId() == null) {
			isNew = true;
		}

		save(content);

		if (!isNew) {
			ContentHistory contentHistory = new ContentHistory();
			contentHistory.setData(content.getData());
			contentHistory.setContentType(content.getContentType());
			// BeanUtils.copyProperties(content, contentHistory);
			contentHistory.setContent(content);
			contentHistory.setFolder(content.getFolder());
			contentHistory.setIsFolder(content.getIsFolder());
			contentHistory.setName(content.getName());
			save(contentHistory);
		}
	}

}
