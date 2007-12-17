package org.wicketstuff.pickwick.frontend.panel;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.wicketstuff.pickwick.auth.PickwickSession;
import org.wicketstuff.pickwick.backend.ImageUtils;
import org.wicketstuff.pickwick.backend.Settings;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Sequence;

import wicket.contrib.gmap.GLatLng;
import wicket.contrib.gmap.GMap;
import wicket.contrib.gmap.GMapPanel;
import wicket.contrib.gmap.GMarker;

import com.google.inject.Inject;

/**
 * Panel displaying the image folder structure
 * @author Vincent Demay
 *
 */
public class GmapLocalizedPanel extends Panel{
	@Inject
	private ImageUtils imageUtils;
	@Inject
	private Settings settings;
	
	private List<Folder> folders;


	/**
	 * There isn't any model because model is auto binded using {@link FolderProvider}
	 * @param id
	 */
	public GmapLocalizedPanel(String id) {
		super(id);
		
		//add gmap
		GMap gmap = new GMap(new GLatLng(40, 10), 3);
		gmap.setTypeControl(true);
		gmap.setSmallMapControl(true);
		
		folders = new ArrayList<Folder>();
		Folder folder = imageUtils.getFolderFor(PickwickSession.get().getUser().getRoles());
		fillFolders(folder);
		String basePath = settings.getImageDirectoryRoot().getAbsolutePath();
		for (Folder current : folders){
			
			Sequence sequence = ImageUtils.readSequence(current.getFile());
			if (sequence != null && sequence.getLatitude() != 0 && sequence.getLongitude() != 0){
				GMarker wicketLibrary = new GMarker(new GLatLng(sequence.getLatitude(), sequence.getLongitude()),
						//new EmptyPanel("gmarkerInfo"));//("gmarkerInfo", sequence, current));
						new DescriptionPanel("gmarkerInfo", current.getFile().getAbsolutePath().substring(settings.getImageDirectoryRoot().getAbsolutePath().length(), current.getFile().getAbsolutePath().length())));
						gmap.addOverlay(wicketLibrary);
			}
			
		}
		
		GMapPanel mapPanel = new GMapPanel("gmap", gmap, 800, 600,
				settings.getGoogleKey());
		/*mapPanel.addClickListener(new GMapClickListener() {

			public void onClick(AjaxRequestTarget target, GLatLng point) {
			}
		});*/
		add(mapPanel);
	
	}

	private void fillFolders(Folder folder) {
		if (folder != null){
			folders.add(folder);
			List<Folder> subFolders = folder.getSubFolders();
			for (Folder current : subFolders){
				fillFolders(current);
			}
		}
	}


}
