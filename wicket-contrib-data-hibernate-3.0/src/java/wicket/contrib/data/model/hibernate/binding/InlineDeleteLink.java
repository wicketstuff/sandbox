package wicket.contrib.data.model.hibernate.binding;

import org.hibernate.Session;

import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao;
import wicket.contrib.data.model.hibernate.sandbox.IHibernateDao.IHibernateCallback;
import wicket.markup.html.StaticResource;
import wicket.markup.html.image.Image;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;

public class InlineDeleteLink extends Link {
	public static final StaticResource DELETE =
		StaticResource.get(InlineEditLink.class.getPackage(), "delete.gif");
	
	public InlineDeleteLink(String id) {
		super(id);
		Image image = new Image("image");
		image.setImageResource(DELETE);
		add(image);
	}

	public void onClick() {
		final HibernateGridView form = InlineComponent.findForm(this);
		final ListItem item = InlineComponent.findListItem(this);
		final IHibernateDao dao = form.getListView().getHibernateDao();
		
		item.modelChanging();
		
		dao.execute(new IHibernateCallback() {
			public Object execute(Session session) {
				session.delete(item.getModelObject());
				return null;
			}
		});
		
		item.modelChanged();
	}

}
