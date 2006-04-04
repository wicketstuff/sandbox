package contrib.wicket.cms.panel;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.extensions.markup.html.repeater.util.SortParam;
import wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.Link;
import wicket.markup.html.panel.Panel;
import wicket.model.IModel;
import wicket.spring.injection.annot.SpringBean;
import contrib.wicket.cms.model.Content;
import contrib.wicket.cms.service.ContentService;
import contrib.wicket.cms.util.HibernateObjectModel;

abstract public class ContentNavigatorPanel extends Panel {

	@SpringBean
	private ContentService contentService;

	Content folder;

	public ContentNavigatorPanel(final String id) {
		this(id, null);
	}

	public ContentNavigatorPanel(final String id, final Content folder) {
		super(id);

		this.folder = folder;

		// if (folder == null) {
		// folder = contentService.getRootFolder();
		// }

		SortableDataProvider dataProvider = new SortableDataProvider() {

			public Iterator iterator(int first, int count) {

				Criteria criteria = contentService.session().createCriteria(
						Content.class);

				SortParam sp = (SortParam) getSort();

				if (sp.isAscending()) {
					criteria.addOrder(Order.asc(sp.getProperty()));
				} else {
					criteria.addOrder(Order.desc(sp.getProperty()));
				}

				criteria.add(Restrictions.eq("folder", folder));

				criteria.setFetchSize(count);
				criteria.setFirstResult(first);

				return criteria.list().iterator();
			}

			public IModel model(Object object) {
				return new HibernateObjectModel(object);
			}

			public int size() {
				return (Integer) contentService.session().createCriteria(
						Content.class).setProjection(Projections.rowCount())
						.uniqueResult();
			}

		};

		dataProvider.setSort("name", true);

		add(new DataView("data", dataProvider, getPageSize()) {

			@Override
			protected void populateItem(final Item item) {

				final Content content = (Content) item.getModelObject();

				Link link = new Link("nameLink") {
					public void onClick() {
						ContentNavigatorPanel.this.onClick(content);
					}
				};
				link.add(new Label("name", content.getName()));
				item.add(link);

				String updatedDate = content.getUpdatedDate() == null ? ""
						: content.getUpdatedDate().toString();
				item.add(new Label("updatedDate", updatedDate));
			}
		});

	}

	public void setFolder(Content folder) {
		this.folder = folder;
	}

	abstract public void onClick(Content content);

	abstract public int getPageSize();
}
