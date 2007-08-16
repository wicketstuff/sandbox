/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package wicket.contrib.bbcodecomponent.core;

/**
 * holds all bbcode tags.
 * @author Nino Martinez Wael (nino.martinez@jayway.dk)
 *
 */
 public enum Tags {
	Bold("b", "bold", "formats text bold"), Italic("i", "italic",
			"formats text italic"), Underlined("u", "underlined",
			"formats text underlined");

	private final String startBBTag;

	private final String endBBTag;

	private final String startHTMLTag;

	private final String endHTMLTag;

	private final String startBBidentifier = "[";

	private final String endBBidentifier = "]";

	private final String startHTML = "<";

	private final String endHTML = ">";

	private final String end = "/";

	private final String description;

	private final String name;

	private Tags(String bBTag, String name, String description) {
		startBBTag = startBBidentifier + bBTag + endBBidentifier;
		endBBTag = startBBidentifier + end + bBTag + endBBidentifier;
		startHTMLTag = startHTML + bBTag + endHTML;
		endHTMLTag = startHTML + end + bBTag + endHTML;
		this.description = description;
		this.name = name;

	}

	public String getEndBBTag() {
		return endBBTag;
	}

	public String getEndHTMLTag() {
		return endHTMLTag;
	}

	public String getStartBBTag() {
		return startBBTag;
	}

	public String getStartHTMLTag() {
		return startHTMLTag;
	}

	public String getDescription() {
		return description;
	}


	public String getName() {
		return name;
	}


}
