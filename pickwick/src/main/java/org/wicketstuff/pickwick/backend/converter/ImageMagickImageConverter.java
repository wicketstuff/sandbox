package org.wicketstuff.pickwick.backend.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wicketstuff.pickwick.ImageConversionException;

import com.google.inject.Singleton;

/**
 * {@link ImageConverter} implemented with ImageMagick. Please make sure
 * environment variable PATH contains path to the "convert" program.
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
@Singleton
public class ImageMagickImageConverter implements ImageConverter {
	private static final Log logger = LogFactory.getLog(ImageMagickImageConverter.class);

	public void scaleImage(File source, File destination, int size, Float quality) throws ImageConversionException {
		File destImageDir = destination.getParentFile();
		if (!destImageDir.exists()) {
			logger.info("Creating dirs " + destImageDir);
			destImageDir.mkdirs();
		} else {
			logger.debug("Directory already exists: " + destImageDir);
		}

		logger.info("Converting image " + source + " to " + destination + " at size " + size);
		String geometry = size + "x" + size + ">";
		List<String> commandArgs = new ArrayList<String>();
		commandArgs.add("convert");
		commandArgs.add("-geometry");
		commandArgs.add(geometry);
		if (quality != null) {
			commandArgs.add("-quality");
			commandArgs.add(Float.toString(quality));
		}
		commandArgs.add(source.getPath());
		commandArgs.add(destination.getPath());
		Process p;
		try {
			p = Runtime.getRuntime().exec((String[]) commandArgs.toArray(new String[0]));
		} catch (IOException e1) {
			throw new ImageConversionException("Error converting " + source + " to " + destination, e1);
		}
		int exitValue;
		try {
			exitValue = p.waitFor();
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while running " + StringUtils.join(commandArgs.iterator(), ' '), e);
		}
		if (exitValue != 0) {
			throw new RuntimeException("Failed to run " + StringUtils.join(commandArgs.iterator(), ' '));
		}

	}
}
