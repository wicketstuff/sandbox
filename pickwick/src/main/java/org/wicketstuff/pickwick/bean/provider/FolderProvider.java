package org.wicketstuff.pickwick.bean.provider;

import java.io.File;
import java.util.ArrayList;

import org.wicketstuff.pickwick.Settings;
import org.wicketstuff.pickwick.bean.Folder;


/**
 * Construct a object representing the imageDirectoryRoot folder architecture 
 * @author Vincent Demay
 *
 */
public class FolderProvider {
	private Settings settings;

	public FolderProvider(Settings settings) {
		super();
		this.settings = settings;
	}
	
	/**
	 * return a tree representation of the folders in imageDirectoryRoot
	 * @return  a tree representation of the folders in imageDirectoryRoot
	 */
	public Folder getFolder(){
		return new Folder(settings.getImageDirectoryRoot(),getSubFolder(this.settings.getImageDirectoryRoot(), null), null);
	}
	
	private ArrayList<Folder> getSubFolder(File folder, Folder parent){
		if (folder.isDirectory()){
			ArrayList<Folder> toReturn = new ArrayList<Folder>();
			for (File file : folder.listFiles()){
				if (file.isDirectory()){
					Folder current = new Folder(file);
					current.setSubFolders(getSubFolder(file, current));
					current.setParent(parent);
					toReturn.add(current);
				}
			}
			return toReturn;
		}
		return null;
	}
}
