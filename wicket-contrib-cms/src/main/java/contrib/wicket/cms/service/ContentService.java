package contrib.wicket.cms.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.model.ContentType;

@Transactional
public interface ContentService extends GenericService {

	public final static String BEAN_NAME = "contentService";
	
	public Content getRootFolder();
	
	public Content getContent(Integer contentId);

	public List<Content> getContents(Integer contentId);

	public Content findContentByName(Content folder, String name);
	
	public Content findFolderByPath(String[] path);
	
	public ContentType getContentType(Integer contentTypeId);
	
	public void saveContent(Content content);
	
}