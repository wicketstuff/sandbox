package org.wicketstuff.jquery.codepress;

import org.wicketstuff.jquery.Options;

public class CodepressOptions extends Options {
	private String fileType;

	public CodepressOptions() {
		set("path", "/resources/org.wicketstuff.jquery.codepress.CodepressBehaviour/");
	}
	
	public CodepressOptions height(int height) {
		set("height", height);
		return this;
	}

	public CodepressOptions lineNumbers(boolean lineNumbers) {
		set("linenumbers", lineNumbers ? 1 : 0);
		return this;
	}

	public CodepressOptions autoComplete(boolean autoComplete) {
		set("autocomplete", autoComplete ? 1 : 0);
		return this;
	}

	public CodepressOptions fileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getFileType() {
		return fileType;
	}

}
