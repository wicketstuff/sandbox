package wicket.contrib.data.model.bind;

import wicket.AttributeModifier;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.Loop;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.list.PageableListViewNavigation;
import wicket.markup.html.list.PageableListViewNavigationLink;
import wicket.model.Model;

/**
 * A page navigation that adds some CSS hooks to make it look a bit prettier.
 * 
 * @author Phil Kulak
 */
public class PageNav extends PageableListViewNavigation
{
	public PageNav(String id, PageableListView pageableListView) {
		super(id, pageableListView);
	}

	protected void populateItem(Loop.LoopItem loopItem) {
		int page = loopItem.getIteration();

		PageableListViewNavigationLink link = new PageableListViewNavigationLink(
			"pageLink", pageableListView, page);
		
		// Set the surrounding tags when this link is active.
		link.setBeforeDisabledLink("<span class=\"activePage\">[");
		link.setAfterDisabledLink("]</span>");
		
		// And a class atribute to the anchor tag.
		link.add(new AttributeModifier("class", true, new Model("pageLink")));
		
		link.add(new Label("pageNumber", String.valueOf(page + 1)));
		loopItem.add(link);
	}

	public boolean isVisible() {
		int pageCount = pageableListView.getPageCount();
		int currentPage = pageableListView.getCurrentPage();
		return pageCount > 1 || currentPage > pageCount - 1;
	}
}
