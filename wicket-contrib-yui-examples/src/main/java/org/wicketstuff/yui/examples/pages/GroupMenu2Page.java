package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBarItem;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBar;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuGroup;


public class GroupMenu2Page extends WicketExamplePage {

	public GroupMenu2Page() {
		
		initMenuGroup();
		
		add(new FeedbackPanel("feedback"));

	}
	

	private void initMenuGroup() {
		AbstractYuiMenuItem mi = null;
		
		YuiMenuGroup menuGroup = new YuiMenuGroup( "groupMenu", "groupMenu" );
		YuiMenu menu1 = menuGroup.addMenu();


		menu1.add(new TestAction("M1 : L1"));
		menu1.add(new TestAction("M1 : L2"));
		mi = menu1.add(new TestAction("M1 : L3"));
		menu1.add(new TestAction("M1 : L4"));
		menu1.add(new TestAction("M1 : L5"));
		
		YuiMenu subMenu = mi.addSubMenu( "gm_subMenu1" );
		subMenu.add(new TestAction("Label 1"));
		subMenu.add(new TestAction("Label 2"));
		
		YuiMenu menu2 = menuGroup.addMenu();
		menu2.add(new TestAction("M2 : L1"));
		menu2.add(new TestAction("M2 : L2"));
		menu2.add(new TestAction("M2 : L3"));
		menu2.add(new TestAction("M2 : L4"));
		menu2.add(new TestAction("M2 : L5"));

		add(menuGroup);
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
