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
package org.wicketstuff.pickwick.backend;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pickwick.PickWickApplication;
import org.wicketstuff.pickwick.bean.Folder;
import org.wicketstuff.pickwick.bean.Image;
import org.wicketstuff.pickwick.bean.Sequence;

import com.google.inject.Inject;

/**
 * Various utility methods
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class ImageUtils {
	private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);
	
	@Inject
	private Settings settings;

	@Inject
	private FileFilter imageFilter;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List getImageList(File directory) {
		List imageList = new ArrayList();
		File[] files = directory.listFiles(imageFilter);
		if (files == null) {
			throw new RuntimeException("Not a directory: " + directory);
		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				imageList.add(ImageUtils.getImageProperties(file));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return imageList;
	}

	final public static Image getImageProperties(File file) throws FileNotFoundException, IOException {
		Image p = new Image();
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
		return new File(o1, "_sequence.xml");
	}

	public static Sequence getSequence(File dir) {
		File sequence = getSequenceFile(dir);
		if (sequence.exists()) {
			// FIXME
			return null;
		}
		return null;
	}

	/**
	 * Get file's path relative to the root image directory
	 * @param imageFile
	 * @return
	 * @throws IOException
	 */
	public String getRelativePath(File imageFile) throws IOException {
		if (settings.getImageDirectoryRoot().getCanonicalPath().equals(imageFile.getCanonicalPath()))
			return new String();
		return imageFile.getCanonicalPath().substring(
				(int) settings.getImageDirectoryRoot().getCanonicalPath().length() + 1);
	}

	public String buildSequencePath(File dir) throws IOException {
		return PickWickApplication.SEQUENCE_PAGE_PATH + "/" + getRelativePath(dir);
	}

	public String getPreviousImage(String uri) throws IOException {
		File file = new File(settings.getImageDirectoryRoot(), uri);
		File previous = getSiblingImage(file, -1);
		if (previous == null)
			return null;
		return getRelativePath(previous);
	}

	public String getNextImage(String uri) throws IOException {
		File file = new File(settings.getImageDirectoryRoot(), uri);
		File next = getSiblingImage(file, 1);
		if (next == null)
			return null;
		return getRelativePath(next);
	}

	public File getSiblingImage(File file, int offset) {
		File folder = file.getParentFile();
		boolean next = false;
		File[] children = folder.listFiles(imageFilter);
		if (children.length == 0)
			throw new RuntimeException("Nothing in sequence " + folder);
		List list = Arrays.asList(children);
		int index = list.indexOf(file);
		if (index + offset < 0 || index + offset > children.length - 1)
			return null;
		return children[index + offset];
	}

	public String getFirstImage(String uri) throws IOException {
		File file = new File(settings.getImageDirectoryRoot(), uri);
		File first = getFirstImage(file);
		if (first == null)
			return null;
		return getRelativePath(first);
	}

	public String getLastImage(String uri) throws IOException {
		File file = new File(settings.getImageDirectoryRoot(), uri);
		File last = getLastImage(file);
		if (last == null)
			return null;
		return getRelativePath(last);
	}

	public File getFirstImage(File file) {
		File folder = file.getParentFile();
		File[] children = folder.listFiles(imageFilter);
		if (children.length == 0)
			throw new RuntimeException("Nothing in sequence " + folder);
		if (children[0].equals(file))
			return null;
		return children[0];
	}

	public File getLastImage(File file) {
		File folder = file.getParentFile();
		File[] children = folder.listFiles(imageFilter);
		if (children.length == 0)
			throw new RuntimeException("Nothing in sequence " + folder);
		if (children[children.length - 1].equals(file))
			return null;
		return children[children.length - 1];
	}

	/**
	 * return a tree representation of the folders in imageDirectoryRoot
	 * @return  a tree representation of the folders in imageDirectoryRoot
	 */
	public Folder getFolder(){
		return new Folder(settings.getImageDirectoryRoot(), getSubFolder(this.settings.getImageDirectoryRoot()));
	}
	
	private ArrayList<Folder> getSubFolder(File folder){
		if (folder.isDirectory()){
			ArrayList<Folder> toReturn = new ArrayList<Folder>();
			for (File file : folder.listFiles()){
				if (file.isDirectory()){
					Folder current = new Folder(file);
					current.setSubFolders(getSubFolder(file));
					toReturn.add(current);
				}
			}
			return toReturn;
		}
		return null;
	}

	public File toFile(String uri) {
		return new File(settings.getImageDirectoryRoot(), uri);
	}

	/**
	 * Returns a {@link Sequence} or null if the sequence file does not exist
	 * @param imageDirectory the directory containing images and a sequence file
	 * @return
	 */
	public Sequence readSequence(File imageDirectory) {
		XmlBeanMapper<Sequence> mapper = new XmlBeanMapper<Sequence>(Sequence.class);
		Sequence sequence = new Sequence();
		InputStream in = null;
		try {
			in = new FileInputStream(getSequenceFile(imageDirectory));
			sequence = mapper.bindInBean(in);
		} catch (FileNotFoundException e) {	
			return sequence;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return sequence;
	}

	public void writeSequence(Sequence sequence, File imageDirectory) {
		XmlBeanMapper<Sequence> mapper = new XmlBeanMapper<Sequence>(Sequence.class);
		OutputStream out = null;
		try {
			 out = new FileOutputStream(getSequenceFile(imageDirectory));
			 mapper.serializeInXml(sequence, out);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot write sequence for " + imageDirectory, e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
