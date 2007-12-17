package org.wicketstuff.dojo.examples.dialog;

import org.apache.wicket.PageParameters;
import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.dialog.DojoDialog;
import org.wicketstuff.dojo.markup.html.dialog.DojoDialogCloser;
import org.wicketstuff.dojo.markup.html.dialog.DojoDialogOpener;
import org.wicketstuff.dojo.toggle.DojoWipeToggle;

public class DialogShower extends WicketExamplePage {
	
	public DialogShower(PageParameters parameters){
		DojoDialog dialog = new DojoDialog("dialogPanel");
		add(dialog);
		dialog.setToggle(new DojoWipeToggle(500));
		add(new DojoDialogOpener("openner", dialog));
		dialog.add(new DojoDialogCloser("closer", dialog));
	}
}
