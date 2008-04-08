/**
 * 
 */
package org.wicketstuff.picnik;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.upload.FileItem;

/**
 * Model for files being uploaded from Picnik.
 * <p>
 * Created 01.04.2008 21:38:42
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PicnikFileUpload implements Serializable {

	private String id;
	
	private transient FileUpload fileUpload;
	
	/**
	 * Create a new PicnikFileUpload.
	 * @param item
	 */
	public PicnikFileUpload() {
		super();
	}

	/**
	 * Get the id. This is the imageid sent from Picnik.
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id.
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the (transient) fileItem.
	 * @return Returns the fileItem.
	 */
	public FileUpload getFileUpload() {
		return fileUpload;
	}

	/**
	 * Set the (transient) fileItem.
	 * @param fileItem The fileItem to set.
	 */
	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

}
