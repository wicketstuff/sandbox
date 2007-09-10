package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;


public class Menu2Page extends WicketExamplePage {

	public Menu2Page() {
		
		initMenu();
		
		add(new FeedbackPanel("feedback"));

	}

	private void initMenu() {
		AbstractYuiMenuItem mi = null;
		
		YuiMenu menu = new YuiMenu( "menu" );


		menu.add(new TestAction("M : L1"));
		menu.add(new TestAction("M : L2"));
		mi = menu.add(new TestAction("M : L3"));
		menu.add(new TestAction("M : L4"));
		menu.add(new TestAction("M : L5"));
		
		YuiMenu subMenu = mi.addSubMenu( "m_subMenu1" );
		subMenu.add(new TestAction("Label 1"));
		subMenu.add(new TestAction("Label 2"));
		

		add(menu);
	}

	private static class TestAction implements IAction, java.io.Serializable {
		private String id;

		public TestAction(String id) {
			this.id = id;
		}
		
		public String getName() {
			return id;
		}

		public void onClick() {
			System.out.println("Link: " + id);
		}
	}

}
