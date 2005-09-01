package wicket.contrib.markup.html.freemarker;

import java.io.Serializable;
import freemarker.template.Configuration;
/**
 * Serializable FreeMarker configuration (since the default one isn't).
 *
 * @author	Jonas Klingstedt
 */
public final class SerializableFreeMarkerConfiguration
	extends Configuration implements Serializable { }
