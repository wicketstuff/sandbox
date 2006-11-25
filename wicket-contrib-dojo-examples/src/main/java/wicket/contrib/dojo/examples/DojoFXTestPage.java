package wicket.contrib.dojo.examples;



import wicket.PageParameters;
import wicket.contrib.dojo.dojofx.FXOnClickFader;
import wicket.contrib.dojo.dojofx.FXOnClickSlider;
import wicket.contrib.dojo.dojofx.FXOnClickWiper;
import wicket.contrib.dojo.dojofx.FXOnMouseOverFader;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.image.Image;
import wicket.model.Model;
import wicket.contrib.dojo.dojofx.FXOnClickExploder;
import wicket.contrib.dojo.dojofx.FXOnMouseOverHighlighter;

public class DojoFXTestPage extends WebPage{

	private Label menuItem1;
	private Label menuItem2;
	private Label menuItem3;
	private WebMarkupContainer submenu1;
	private WebMarkupContainer submenu2;
	private WebMarkupContainer submenu3;
	private WebMarkupContainer submenu31x;
	
	
	private Label submenu11;
	private Label submenu21;
	private Label submenu31;
	private Label submenu32;
	
	private Label submenu311;
	private Label submenu312;
	
	private int duration = 500;
	
	private WebMarkupContainer table1;
	private Image explodeimg;
	private Image explodedimg;
	private Label close;
	
	private WebMarkupContainer table2;
	
	private Label slidetrigger;
	private Label sliderdiv;
	private Label slidetrigger2;
	private Label sliderdiv2;
	private Label slidetrigger3;
	private Label sliderdiv3;
	
	private WebMarkupContainer imagecontainer;
	private Image pic1;
	private Image pic2;
	private Image pic3;
	private Image pic4;
	private Image pic5;
	
	private WebMarkupContainer menubar;
	private Label filemenu;
	private Label editmenu;
	private Label viewmenu;
	private Label insertmenu;
	private Label modifymenu;
	
	//menu2 example
	private WebMarkupContainer menu2; 
	private WebMarkupContainer menuhead;
	private WebMarkupContainer menubody;
	private Label menutitle;
	private Label menutoggle;
	private WebMarkupContainer item1;
	private Label item1label;
	private WebMarkupContainer item2;
	private Label item2label;
	private WebMarkupContainer item3;
	private Label item3label;
	private WebMarkupContainer item4;
	private Label item4label;
	private WebMarkupContainer item5;
	private Label item5label;
	//menu3
	private WebMarkupContainer menu3; 
	private WebMarkupContainer menu3head;
	private WebMarkupContainer menu3body;
	private Label menu3title;
	private Label menu3toggle;
	private WebMarkupContainer item13;
	private Label item1label3;
	private WebMarkupContainer item23;
	private Label item2label3;
	private WebMarkupContainer item33;
	private Label item3label3;
	private WebMarkupContainer item43;
	private Label item4label3;
	private WebMarkupContainer item53;
	private Label item5label3;

	
	
	
	public DojoFXTestPage(PageParameters parameters)
	{
		new Image(this,"topimg", new Model("menu-top.gif"));
		menuItem1 = new Label(this, "menuitem1", new Model("About"));
		menuItem2 = new Label(this, "menuitem2", new Model("Documentation"));
		menuItem3 = new Label(this, "menuitem3", new Model("Tutorials"));
		
		
		submenu1 = new WebMarkupContainer(this, "submenu1");
		submenu2 = new WebMarkupContainer(this, "submenu2");
		submenu3 = new WebMarkupContainer(this, "submenu3");
		
		submenu11 = new Label(submenu1, "submenu11", new Model("About us"));
		submenu21 = new Label(submenu2, "submenu21", new Model("JavaDoc"));
		submenu31 = new Label(submenu3, "submenu31", new Model("Tutorial 1"));
		submenu32 = new Label(submenu3, "submenu32", new Model("Tutoral 2"));
		submenu31x = new WebMarkupContainer(submenu3, "submenu31x");
		submenu311 = new Label(submenu31x, "submenu311","Part 1. Basics");
		submenu312 = new Label(submenu31x, "submenu312","Part 2. Bloody complicated");
		
		new Image(this, "bottomimg", new Model("menu-bottom.gif"));
		
		submenu1.add(new FXOnClickWiper(duration, menuItem1));
		submenu2.add(new FXOnClickWiper(duration, menuItem2));
		submenu3.add(new FXOnClickWiper(duration, menuItem3));
		submenu31x.add(new FXOnClickWiper(duration, submenu31));
		
		
		table1 = new WebMarkupContainer(this, "cdinfo");
		new Label(table1, "title", new Model("Hello nasty"));
		explodeimg = new Image(table1, "explodeimg", "hello_nasty_small.jpg");
		new Label(table1, "artist", new Model("Beasty boys"));
		new Label(table1, "year", new Model("1998"));
		new Label(table1, "genre", new Model("Hip Hop"));
		
		
		table2 = new WebMarkupContainer(this, "infotable");
		explodedimg = new Image(table2, "explodedimg", "hello_nasty.jpg");
		close = new Label(table2, "close", new Model("(X)"));
		FXOnClickExploder d = new FXOnClickExploder(duration, explodeimg);
		table2.add(d);
		d.addTrigger(close);
		
		//slidertest
		slidetrigger = new Label(this, "slidetrigger","click here to slide relative");
		sliderdiv = new Label(this, "sliderdiv","I'm sliding by! I'm sliding by!");
		sliderdiv.add(new FXOnClickSlider(duration, slidetrigger, 200, 200, true));
		
		slidetrigger2 = new Label(this, "slidetrigger2","click here to slide absolute");
		sliderdiv2 = new Label(this, "sliderdiv2","I'm sliding to! I'm sliding to!");
		sliderdiv2.add(new FXOnClickSlider(duration, slidetrigger2, 500, 500, false));
	
		//Fadertest
		
		imagecontainer = new WebMarkupContainer(this, "imgcontainer");
		pic1 = new Image(imagecontainer, "pic1", "pic1.jpg");
		pic2 = new Image(imagecontainer, "pic2", "pic2.jpg");
		pic3 = new Image(imagecontainer, "pic3", "pic3.jpg");
		pic4 = new Image(imagecontainer, "pic4", "pic4.jpg");
		pic5 = new Image(imagecontainer, "pic5", "pic5.jpg");
		pic1.add(new FXOnMouseOverFader(duration, pic1, false, 0.2, 1.0));
		pic2.add(new FXOnMouseOverFader(duration, pic2, false, 0.2, 1.0));
		pic3.add(new FXOnMouseOverFader(duration, pic3, false, 0.2, 1.0));
		pic4.add(new FXOnMouseOverFader(duration, pic4, false, 0.2, 1.0));
		pic5.add(new FXOnMouseOverFader(duration, pic5, false, 0.2, 1.0));
		
		menubar = new WebMarkupContainer(this, "menubar");
		filemenu = new Label(menubar, "filemenu", new Model("File"));
		editmenu = new Label(menubar, "editmenu", new Model("Edit"));
		viewmenu = new Label(menubar, "viewmenu", new Model("View"));
		insertmenu = new Label(menubar, "insertmenu", new Model("Insert"));
		modifymenu = new Label(menubar, "modifymenu", new Model("Modify"));
		filemenu.add(new FXOnMouseOverHighlighter(200, filemenu, 255,102,0));
		editmenu.add(new FXOnMouseOverHighlighter(200, editmenu, 255,102,0));
		viewmenu.add(new FXOnMouseOverHighlighter(200, viewmenu, 255,102,0));
		insertmenu.add(new FXOnMouseOverHighlighter(200, insertmenu, 255,102,0));
		modifymenu.add(new FXOnMouseOverHighlighter(200, modifymenu, 255,102,0));
		
		//menu2 example
		menu2 = new WebMarkupContainer(this, "menu2");
		menuhead = new WebMarkupContainer(menu2, "menuhead");
		menutitle = new Label(menuhead, "menutitle","File menu");
		menutoggle = new Label(menuhead, "menutoggle","(±)");
		menubody = new WebMarkupContainer(menu2, "menubody");
		item1 = new WebMarkupContainer(menubody, "item1");
		item1label = new Label(item1, "item1label","Move file");
		new Image(item1, "item1img", new Model("move.jpg"));
		item2 = new WebMarkupContainer(menubody, "item2");
		item2label = new Label(item2, "item2label","Rename file");
		new Image(item2, "item2img", new Model("rename.gif"));
		item3 = new WebMarkupContainer(menubody, "item3");
		item3label = new Label(item3, "item3label","Mail file");
		new Image(item3, "item3img", new Model("mail.jpg"));
		item4 = new WebMarkupContainer(menubody, "item4");
		item4label = new Label(item4, "item4label","Copy file");
		new Image(item4, "item4img", new Model("copy.jpg"));
		item5 = new WebMarkupContainer(menubody, "item5");
		item5label = new Label(item5, "item5label","Print file");
		new Image(item5, "item5img", new Model("print.jpg")); 

		
		//menu3
		menu3 = new WebMarkupContainer(this, "menu3");
		menu3head = new WebMarkupContainer(menu3, "menu3head");
		menu3title = new Label(menu3head, "menu3title","Other places");
		menu3toggle = new Label(menu3head, "menu3toggle","(±)");
		menu3body = new WebMarkupContainer(menu3, "menu3body");
		item13 = new WebMarkupContainer(menu3body, "item13");
		item1label3 = new Label(item13, "item1label3","Notulen");
		new Image(item13, "item1img3", new Model("Notulen.jpg"));
		item23 = new WebMarkupContainer(menu3body, "item23");
		item2label3 = new Label(item23, "item2label3","My Documents");
		new Image(item23, "item2img3", new Model("documents.jpg"));
		item33 = new WebMarkupContainer(menu3body, "item33");
		item3label3 = new Label(item33, "item3label3","My Computer");
		new Image(item33, "item3img3", new Model("myComputer.jpg"));
		item43 = new WebMarkupContainer(menu3body, "item43");
		item4label3 = new Label(item43, "item4label3","My Network places");
		new Image(item43, "item4img3", new Model("network.jpg"));

		
		//images
		
		
		//wiper + fader
		menubody.add(new FXOnClickWiper(250, menutoggle, true));
		menubody.add(new FXOnClickFader(250, menutoggle, true));
			  
		menu3body.add(new FXOnClickWiper(250, menu3toggle, true));
		menu3body.add(new FXOnClickFader(250, menu3toggle, true));
	}
	
}
