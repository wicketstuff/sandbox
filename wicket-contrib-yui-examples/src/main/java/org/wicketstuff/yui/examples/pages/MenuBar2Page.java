package org.wicketstuff.yui.examples.pages;


import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.AbstractYuiMenuItem;
import org.wicketstuff.yui.markup.html.menu2.IAction;
import org.wicketstuff.yui.markup.html.menu2.YuiMenu;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBar;
import org.wicketstuff.yui.markup.html.menu2.YuiMenuBarItem;


public class MenuBar2Page extends WicketExamplePage {

	public MenuBar2Page() {

		initMenuBar();
		
		add(new FeedbackPanel("feedback"));
	}
	
	private void initMenuBar() {
		AbstractYuiMenuItem mi = null;
		YuiMenu subMenu = null;
		
		YuiMenuBar mb = new YuiMenuBar( "menuBar", "menuBar");
		
		YuiMenuBarItem firstMenu = mb.addMenu( "First Menu" );
		
		subMenu = firstMenu.addSubMenu( "mb_firstMenu");
		
		subMenu.add(new TestAction("M1 : L1"));
		subMenu.add(new TestAction("M1 : L2"));
		mi = subMenu.add(new TestAction("M1 : L3"));
		subMenu.add(new TestAction("M1 : L4"));
		subMenu.add(new TestAction("M1 : L5"));
		
		subMenu = mi.addSubMenu( "subMenu1" );
		subMenu.add(new TestAction("Label 1"));
		subMenu.add(new TestAction("Label 2"));
		
		YuiMenuBarItem secondMenu = mb.addMenu( "Second Menu");
		
		subMenu = secondMenu.addSubMenu( "mb_secondMenu");
		subMenu.add(new TestAction("M2 : L1"));
		subMenu.add(new TestAction("M2 : L2"));
		subMenu.add(new TestAction("M2 : L3"));
		subMenu.add(new TestAction("M2 : L4"));
		subMenu.add(new TestAction("M2 : L5"));
		
		add( mb );
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
