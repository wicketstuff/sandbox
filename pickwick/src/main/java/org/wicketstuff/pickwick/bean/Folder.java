package org.wicketstuff.pickwick.bean;

import java.util.ArrayList;

/**
 * This class represent a folder where image are phisicaly stored
 * @author Vincent Demay
 *
 */
public class Folder
{
	private String name;
	private ArrayList<Folder> subFolders;


	public Folder(String name, ArrayList<Folder> subFolders) {
		super();
		this.name = name;
		this.subFolders = subFolders;
	}

	public Folder(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Folder> getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(ArrayList<Folder> subFolders) {
		this.subFolders = subFolders;
	}
	
	public void addFolder(Folder folder){
		this.subFolders.add(folder);
	}
}