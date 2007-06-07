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
		return new Folder(this.settings.getImageDirectoryRoot().getName(),getSubFolder(this.settings.getImageDirectoryRoot()));
	}
	
	private ArrayList<Folder> getSubFolder(File folder){
		if (folder.isDirectory()){
			ArrayList<Folder> toReturn = new ArrayList<Folder>();
			for (File file : folder.listFiles()){
				if (file.isDirectory()){
					toReturn.add(new Folder(file.getName(),getSubFolder(file)));
				}
			}
			return toReturn;
		}
		return null;
	}
}
