package org.wicketstuff.pickwick.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent a folder where image are physically stored
 * 
 * @author Vincent Demay
 * 
 */
public class Folder implements Serializable{
	private File file;

	private ArrayList<Folder> subFolders;

	public Folder(File file, ArrayList<Folder> subFolders) {
		super();
		this.file = file;
		this.subFolders = subFolders;
	}

	public Folder(File file) {
		super();
		this.file = file;
	}

	public ArrayList<Folder> getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(ArrayList<Folder> subFolders) {
		this.subFolders = subFolders;
	}

	public void addFolder(Folder folder) {
		this.subFolders.add(folder);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}