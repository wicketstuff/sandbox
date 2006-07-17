package wicket.contrib.data.model.bind;

import wicket.AttributeModifier;
import wicket.MarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.Loop;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.navigation.paging.PagingNavigation;
import wicket.markup.html.navigation.paging.PagingNavigationLink;
import wicket.model.Model;

/**
 * A page navigation that adds some CSS hooks to make it look a bit prettier.
 * 
 * @author Phil Kulak
 */
public class PageNav extends PagingNavigation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PageNav(MarkupContainer parent, String id, PageableListView pageableListView)
	{
		super(parent, id, pageableListView);
	}

	@Override
	protected void populateItem(Loop.LoopItem loopItem)
	{
		int page = loopItem.getIteration();

		PagingNavigationLink link = new PagingNavigationLink(loopItem, "pageLink",
				pageable, page);

		// Set the surrounding tags when this link is active.
		link.setBeforeDisabledLink("<span class=\"activePage\">[");
		link.setAfterDisabledLink("]</span>");

		// And a class atribute to the anchor tag.
		link.add(new AttributeModifier("class", true, new Model<String>("pageLink")));

		new Label(link, "pageNumber", String.valueOf(page + 1));
	}

	@Override
	public boolean isVisible()
	{
		int pageCount = pageable.getPageCount();
		int currentPage = pageable.getCurrentPage();
		return pageCount > 1 || currentPage > pageCount - 1;
	}
}
