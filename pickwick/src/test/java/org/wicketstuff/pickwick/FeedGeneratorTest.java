package org.wicketstuff.pickwick;

import java.io.File;
import java.io.FileOutputStream;

import org.wicketstuff.pickwick.backend.Settings;

import junit.framework.TestCase;

public class FeedGeneratorTest extends TestCase {
	public void testGenerate() throws Exception {
		Settings settings = new Settings() {
			public File getImageDirectoryRoot() {
				return new File("/tmp/a");
			}
			public String getBaseURL() {
				return "http://localhost:8080";
			}
		};
		new FeedGenerator().generate(new File("/tmp/a"), "atom_1.0", new FileOutputStream("/tmp/top10.atom"));
		new FeedGenerator().generate(new File("/tmp/a"), "rss_0.9", new FileOutputStream("/tmp/top10.rss"));
	}
}
