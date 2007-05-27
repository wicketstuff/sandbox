package org.wicketstuff.dojo.examples.lfx;



import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.dojofx.FXOnClickFader;
import org.wicketstuff.dojo.dojofx.FXOnClickSlider;
import org.wicketstuff.dojo.dojofx.FXOnClickWiper;
import org.wicketstuff.dojo.dojofx.FXOnMouseOverFader;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.wicketstuff.dojo.dojofx.FXOnClickExploder;
import org.wicketstuff.dojo.dojofx.FXOnMouseOverHighlighter;
import org.wicketstuff.dojo.examples.WicketExamplePage;

public class DojoFXTestPage extends WicketExamplePage{

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
		add(new Image("topimg", new Model("menu-top.gif") ));
		add(menuItem1 = new Label("menuitem1", new Model("About")));
		add(menuItem2 = new Label("menuitem2", new Model("Documentation")));
		add(menuItem3 = new Label("menuitem3", new Model("Tutorials")));
		
		
		add(submenu1 = new WebMarkupContainer("submenu1"));
		add(submenu2 = new WebMarkupContainer("submenu2"));
		add(submenu3 = new WebMarkupContainer("submenu3"));
		
		submenu1.add(submenu11 = new Label("submenu11", new Model("About us")));
		submenu2.add(submenu21 = new Label("submenu21", new Model("JavaDoc")));
		submenu3.add(submenu31 = new Label("submenu31", new Model("Tutorial 1")));
		submenu3.add(submenu32 = new Label("submenu32", new Model("Tutoral 2")));
		submenu3.add(submenu31x = new WebMarkupContainer("submenu31x"));
		submenu31x.add(submenu311 = new Label("submenu311","Part 1. Basics"));
		submenu31x.add(submenu312 = new Label("submenu312","Part 2. Bloody complicated"));
		
		add(new Image("bottomimg", new Model("menu-bottom.gif") ));
		
		submenu1.add(new FXOnClickWiper(duration, menuItem1));
		submenu2.add(new FXOnClickWiper(duration, menuItem2));
		submenu3.add(new FXOnClickWiper(duration, menuItem3));
		submenu31x.add(new FXOnClickWiper(duration, submenu31));
		
		
		add(table1 = new WebMarkupContainer("cdinfo"));
		table1.add(new Label("title", new Model("Hello nasty")));
		table1.add(explodeimg = new Image("explodeimg", "hello_nasty_small.jpg"));
		table1.add(new Label("artist", new Model("Beasty boys")));
		table1.add(new Label("year", new Model("1998")));
		table1.add(new Label("genre", new Model("Hip Hop")));
		
		
		add(table2 = new WebMarkupContainer("infotable"));
		table2.add(explodedimg = new Image("explodedimg", "hello_nasty.jpg"));
		table2.add(close = new Label("close", new Model("(X)")));
		FXOnClickExploder d = new FXOnClickExploder(duration, explodeimg);
		table2.add(d);
		d.addTrigger(close);
		
		//slidertest
		add(slidetrigger = new Label("slidetrigger","click here to slide relative"));
		add(sliderdiv = new Label("sliderdiv","I'm sliding by! I'm sliding by!"));
		sliderdiv.add(new FXOnClickSlider(duration, slidetrigger, 200, 200, true));
		
		add(slidetrigger2 = new Label("slidetrigger2","click here to slide absolute"));
		add(sliderdiv2 = new Label("sliderdiv2","I'm sliding to! I'm sliding to!"));
		sliderdiv2.add(new FXOnClickSlider(duration, slidetrigger2, 500, 500, false));
		
		//Fadertest
		
		add(imagecontainer = new WebMarkupContainer("imgcontainer"));
		imagecontainer.add(pic1 = new Image("pic1", "pic1.jpg"));
		imagecontainer.add(pic2 = new Image("pic2", "pic2.jpg"));
		imagecontainer.add(pic3 = new Image("pic3", "pic3.jpg"));
		imagecontainer.add(pic4 = new Image("pic4", "pic4.jpg"));
		imagecontainer.add(pic5 = new Image("pic5", "pic5.jpg"));
		pic1.add(new FXOnMouseOverFader(duration, pic1, false, 0.2, 1.0));
		pic2.add(new FXOnMouseOverFader(duration, pic2, false, 0.2, 1.0));
		pic3.add(new FXOnMouseOverFader(duration, pic3, false, 0.2, 1.0));
		pic4.add(new FXOnMouseOverFader(duration, pic4, false, 0.2, 1.0));
		pic5.add(new FXOnMouseOverFader(duration, pic5, false, 0.2, 1.0));
		
		add(menubar = new WebMarkupContainer("menubar"));
		menubar.add(filemenu = new Label("filemenu", new Model("File")));
		menubar.add(editmenu = new Label("editmenu", new Model("Edit")));
		menubar.add(viewmenu = new Label("viewmenu", new Model("View")));
		menubar.add(insertmenu = new Label("insertmenu", new Model("Insert")));
		menubar.add(modifymenu = new Label("modifymenu", new Model("Modify")));
		filemenu.add(new FXOnMouseOverHighlighter(200, filemenu, 255,102,0));
		editmenu.add(new FXOnMouseOverHighlighter(200, editmenu, 255,102,0));
		viewmenu.add(new FXOnMouseOverHighlighter(200, viewmenu, 255,102,0));
		insertmenu.add(new FXOnMouseOverHighlighter(200, insertmenu, 255,102,0));
		modifymenu.add(new FXOnMouseOverHighlighter(200, modifymenu, 255,102,0));
		
		//menu2 example
		menu2 = new WebMarkupContainer("menu2");
		menuhead = new WebMarkupContainer("menuhead");
		menutitle = new Label("menutitle","File menu");
		menutoggle = new Label("menutoggle","(±)");
		menubody = new WebMarkupContainer("menubody");
		item1 = new WebMarkupContainer("item1");
		item1label = new Label("item1label","Move file");
		item2 = new WebMarkupContainer("item2");
		item2label = new Label("item2label","Rename file");
		item3 = new WebMarkupContainer("item3");
		item3label = new Label("item3label","Mail file");
		item4 = new WebMarkupContainer("item4");
		item4label = new Label("item4label","Copy file");
		item5 = new WebMarkupContainer("item5");
		item5label = new Label("item5label","Print file");

		
		//menu3
		menu3 = new WebMarkupContainer("menu3");
		menu3head = new WebMarkupContainer("menu3head");
		menu3title = new Label("menu3title","Other places");
		menu3toggle = new Label("menu3toggle","(±)");
		menu3body = new WebMarkupContainer("menu3body");
		item13 = new WebMarkupContainer("item13");
		item1label3 = new Label("item1label3","Notulen");
		item23 = new WebMarkupContainer("item23");
		item2label3 = new Label("item2label3","My Documents");
		item33 = new WebMarkupContainer("item33");
		item3label3 = new Label("item3label3","My Computer");
		item43 = new WebMarkupContainer("item43");
		item4label3 = new Label("item4label3","My Network places");

		
		//images
		
		
		//wiper + fader
		menubody.add(new FXOnClickWiper(250, menutoggle, true));
		menubody.add(new FXOnClickFader(250, menutoggle, true));
		
		
		
		add(menu2);
		 menu2.add(menuhead);
		  menuhead.add(menutitle);
		  menuhead.add(menutoggle);
		 menu2.add(menubody);
		  menubody.add(item1);
		   item1.add(item1label);
		   item1.add(new Image("item1img", new Model("move.jpg")));
		  menubody.add(item2);
		   item2.add(item2label);
		   item2.add(new Image("item2img", new Model("rename.gif")));
		  menubody.add(item3);
		   item3.add(item3label);
		   item3.add(new Image("item3img", new Model("mail.jpg")));
		  menubody.add(item4);
		   item4.add(item4label);
		   item4.add(new Image("item4img", new Model("copy.jpg")));
		  menubody.add(item5);
		   item5.add(item5label);
		   item5.add(new Image("item5img", new Model("print.jpg")));
		   
		   //menu3
			add(menu3);
			 menu3.add(menu3head);
			  menu3head.add(menu3title);
			  menu3head.add(menu3toggle);
			 menu3.add(menu3body);
			  menu3body.add(item13);
			   item13.add(item1label3);
			   item13.add(new Image("item1img3", new Model("Notulen.jpg")));
			  menu3body.add(item23);
			   item23.add(item2label3);
			   item23.add(new Image("item2img3", new Model("documents.jpg")));
			  menu3body.add(item33);
			   item33.add(item3label3);
			   item33.add(new Image("item3img3", new Model("myComputer.jpg")));
			  menu3body.add(item43);
			   item43.add(item4label3);
			   item43.add(new Image("item4img3", new Model("network.jpg")));
			  
			menu3body.add(new FXOnClickWiper(250, menu3toggle, true));
			menu3body.add(new FXOnClickFader(250, menu3toggle, true));
	}
	
}
