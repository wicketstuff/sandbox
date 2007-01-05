package wicket.contrib.dojo.examples;

import wicket.PageParameters;
import wicket.contrib.dojo.markup.html.dialog.DojoDialog;
import wicket.contrib.dojo.markup.html.dialog.DojoDialogCloser;
import wicket.contrib.dojo.markup.html.dialog.DojoDialogOpener;
import wicket.contrib.dojo.toggle.DojoFadeToggle;
import wicket.contrib.dojo.toggle.DojoWipeToggle;
import wicket.markup.html.WebPage;

public class DialogShower extends WebPage {
	
	public DialogShower(PageParameters parameters){
		DojoDialog dialog = new DojoDialog("dialogPanel");
		add(dialog);
		dialog.setToggle(new DojoWipeToggle(500));
		add(new DojoDialogOpener("openner", dialog));
		dialog.add(new DojoDialogCloser("closer", dialog));
	}
}
