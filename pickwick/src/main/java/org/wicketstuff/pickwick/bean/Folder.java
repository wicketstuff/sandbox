package org.wicketstuff.pickwick.bean;

import java.io.File;
import java.util.ArrayList;

/**
 * This class represent a folder where image are physically stored
 * 
 * @author Vincent Demay
 * 
 */
public class Folder {
	private File file;

	private ArrayList<Folder> subFolders;

	private Folder parent;

	public Folder(File file, ArrayList<Folder> subFolders, Folder parent) {
		super();
		this.file = file;
		this.subFolders = subFolders;
		this.parent = parent;
	}

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

	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}