package org.wicketstuff.yui.markup.html.menu2;


import org.apache.wicket.markup.html.WebPage;


public class MenuTest extends WebPage {

	public MenuTest() {
		
		initMenuGroup();
		initMenu();
		initMenuBar();
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
