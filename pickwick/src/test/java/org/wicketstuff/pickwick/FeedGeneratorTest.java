package org.wicketstuff.pickwick;

import java.io.File;

import org.wicketstuff.pickwick.FeedGenerator;
import org.wicketstuff.pickwick.Settings;

import junit.framework.TestCase;

public class FeedGeneratorTest extends TestCase {
	public void testGenerate() throws Exception {
		Settings settings = new Settings();
		settings.setImageDirectoryRoot(new File("/tmp/a"));
		settings.setBaseURL("http://localhost:8080");
		new FeedGenerator(settings, new File("/tmp/a")).generate("atom_1.0", new File("/tmp/top10.atom"));
		new FeedGenerator(settings, new File("/tmp/a")).generate("rss_0.9", new File("/tmp/top10.rss"));
	}
}
