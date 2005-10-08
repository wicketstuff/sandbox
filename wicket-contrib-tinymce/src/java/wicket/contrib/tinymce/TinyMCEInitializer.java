package wicket.contrib.tinymce;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import wicket.Application;
import wicket.IInitializer;
import wicket.markup.html.PackageResource;
import wicket.util.string.Strings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author Iulian-Corneliu COSTAN
 */
public class TinyMCEInitializer implements IInitializer {

    private static final Log log = LogFactory.getLog(TinyMCEInitializer.class);

    private static final String TINY_MCE = "tiny_mce";

    private Class scope = TinyMCEPanel.class;


    public void init(Application application) {
        String protocol = scope.getResource("").getProtocol();
        if ("file".equals(protocol)) {
            initFromClasses(application);

        } else if ("jar".equals(protocol)) {
            initFromJar(application);

        } else {
            if (log.isErrorEnabled()) {
                log.error("unknown protocol, only file and jar are implemented");
            }
            throw new UnsupportedOperationException("protocol " + protocol + "not implemented");
        }
    }

    private void initFromJar(Application application) {
        ZipFile zipFile = null;
        try {
            String basePath = scope.getResource(scope.getSimpleName() + ".class").getPath();
            String jarFilePath = new URL(Strings.beforeLast(basePath, '!')).getPath();
            zipFile = new ZipFile(jarFilePath);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("tinymce.jar file exception", e);
            }
            throw new RuntimeException(e);
        }

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry.getName().contains(TINY_MCE)) {
                String name = zipEntry.getName();
                bindResource(application, name);
            }
        }
    }

    private void initFromClasses(Application application) {
        //todo used in development, it may not work in secured environment
        String path = scope.getProtectionDomain().getCodeSource().getLocation().getPath();
        String basePath = Strings.beforeLast(path, '/');

        initResources(application, basePath);
    }

    private void initResources(Application application, String path) {
        File tinyMceDir = new File(path + "/" + TINY_MCE);
        recursiveInitialization(application, tinyMceDir);
    }

    private void recursiveInitialization(Application application, File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                recursiveInitialization(application, file);
            } else {
                String uri = file.toURI().toString();
                bindResource(application, uri);
            }
        }
    }

    private void bindResource(Application application, String uri) {
        String resource = uri.substring(uri.indexOf(TINY_MCE));
        PackageResource.bind(application, scope, resource);
    }

    /* used for testing purpose */
    void setScope(Class scope) {
        this.scope = scope;
    }
}
