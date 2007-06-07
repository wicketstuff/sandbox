package org.wicketstuff.pickwick;

import java.io.File;

import org.wicketstuff.pickwick.FeedGenerator;
import org.wicketstuff.pickwick.Settings;

import junit.framework.TestCase;

public class FeedGeneratorTest extends TestCase {
    public void testGenerate() throws Exception {
    	Settings settings = new Settings();
    	settings.setImageDirectoryRoot(new File("/tmp/a"));
        new FeedGenerator(settings, new File("/tmp/a")).generate();
    }
}
