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
package org.wicketstuff.pickwick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.wicket.util.string.Strings;
import org.wicketstuff.pickwick.bean.Sequence;

/**
 * FIXME remove this class, we don't need it at all in fact
 */
final public class ImageUtils {

	final public static ImageProperties getImageProperties(File file)
			throws FileNotFoundException, IOException {
		ImageProperties p = new ImageProperties();
		p.setFile(file);
		return p;
		// TODO load sequence information: image title, caption, date, ...
	}

	public static boolean isImage(File arg0) {
		return arg0.getName().toLowerCase().endsWith(".jpg");
	}

	public static long getSequenceDateMillis(File o1) {
		File sequence = getSequenceFile(o1);
		if (sequence.exists())
			// FIXME get date from sequence file
			return 0;
		else
			return o1.lastModified();
	}

	private static File getSequenceFile(File o1) {
		return new File(o1, "sequence.xml");
	}

	public static Sequence getSequence(File dir) {
		File sequence = getSequenceFile(dir);
		if (sequence.exists()) {
			// FIXME
			return null;
		}
		return null;
	}

	public static String getRelativePath(Settings settings, File imageFile)
			throws IOException {
		if (settings.getImageDirectoryRoot().getCanonicalPath().equals(
				imageFile.getCanonicalPath()))
			return new String();
		return imageFile.getCanonicalPath().substring(
				(int) settings.getImageDirectoryRoot().getCanonicalPath()
						.length() + 1);
	}

	public static String buildSequencePath(Settings settings, File dir) throws IOException {
		return PickWickApplication.SEQUENCE_PAGE_PATH + "/"
				+ ImageUtils.getRelativePath(settings, dir);
	}
}
