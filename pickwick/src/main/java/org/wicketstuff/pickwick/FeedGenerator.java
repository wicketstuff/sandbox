package org.wicketstuff.pickwick;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.wicketstuff.pickwick.bean.Sequence;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

public class FeedGenerator {
	File imagesDir;
	Settings settings;

	public FeedGenerator(Settings settings, final File imagesDir) {
		this.settings = settings;
		this.imagesDir = imagesDir;
	}

	public void generate() throws IOException {
		List<File> results;
		results = new SequenceWalker().generate();
		System.out.println(results);
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");

		int n = 10;
		feed.setTitle("Last " + n + " Sequences");
		// FIXME set feed link
		// feed.setLink("http://rome.dev.java.net");
		// feed
		// .setDescription("This feed has been created using ROME (Java
		// syndication utilities");

		List entries = new ArrayList();
		SyndEntry entry;
		SyndContent description;

		for (File dir : results) {
			entry = new SyndEntryImpl();
			Sequence sequence = ImageUtils.getSequence(dir);
			if (sequence == null) {
				entry.setTitle(dir.getName());
				entry.setUpdatedDate(new Date(dir.lastModified()));
			} else {
				entry.setTitle(sequence.getTitle());
			}

			entry.setLink(settings.getBaseURL() + PickWickApplication.SEQUENCE_PAGE_PATH + ImageUtils.getRelativePath(settings, dir));
			entry.setLink(settings.getBaseURL() + "/" + ImageUtils.buildSequencePath(settings, dir));
			// entry.setPublishedDate(DATE_PARSER.parse("2004-06-08"));

			// FIXME add a description == sequence caption
/*			description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue("Initial release of ROME");
			entry.setDescription(description);*/
			entries.add(entry);
		}
		System.out.println(entries);
	}

	public class SequenceWalker extends DirectoryWalker {
		List<File> results;

		boolean sequence;

		public List generate() throws IOException {
			results = new ArrayList<File>();
			walk(imagesDir, results);
			Collections.sort(results, new SequenceComparator());
			return results;
		}

		@Override
		protected void handleDirectoryStart(File arg0, int arg1, Collection arg2)
				throws IOException {
			sequence = false;
		}

		@Override
		protected void handleFile(File arg0, int arg1, Collection arg2)
				throws IOException {
			if (ImageUtils.isImage(arg0))
				sequence = true;
		}

		@Override
		protected void handleDirectoryEnd(File arg0, int arg1, Collection arg2)
				throws IOException {
			if (sequence)
				results.add(arg0);
		}
	}

	public class SequenceComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			return (int) (ImageUtils.getSequenceDateMillis(o2) - ImageUtils
					.getSequenceDateMillis(o1));
		}
	}
}
