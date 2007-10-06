package org.wicketstuff.pickwick.frontend.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.model.Model;
import org.wicketstuff.pickwick.Utils;
import org.wicketstuff.pickwick.auth.PickwickAuthorization;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.frontend.diaporama.DiaporamaPanel;

import com.google.inject.Inject;


/**
 * Page to display a single image
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class DiaporamaPage extends BasePage {
	@Inject
	Settings settings;
	
	public DiaporamaPage(PageParameters parameters) {
		super();
		String uri = Utils.getUri(parameters);
		//check user authorisation
		if(uri != ""){
			PickwickAuthorization.check(settings.getImageDirectoryRoot() + "/" + uri, PickwickSession.get());
		}
		add(new DiaporamaPanel("diapo", new Model(uri)));
	}
}
