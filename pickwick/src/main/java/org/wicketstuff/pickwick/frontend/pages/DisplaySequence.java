package org.wicketstuff.pickwick.frontend.pages;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.wicketstuff.pickwick.bean.Sequence;

public class DisplaySequence implements Serializable {
	Sequence sequence;

	File imageDirectory;

	public DisplaySequence(Sequence sequence, File imageDirectory) {
		this.sequence = sequence;
		this.imageDirectory = imageDirectory;
	}

	public Date getDate() {
		Date d = sequence.getDate();
		if (d != null)
			return null;
		return new Date(imageDirectory.lastModified());
	}

	/**
	 * @return the sequence's description or null if there is none
	 */
	public String getDescription() {
		return sequence.getDescription();
	}

	public String getTitle() {
		String t = sequence.getTitle();
		if (t != null)
			return t;
		return imageDirectory.getName();
	}
}
