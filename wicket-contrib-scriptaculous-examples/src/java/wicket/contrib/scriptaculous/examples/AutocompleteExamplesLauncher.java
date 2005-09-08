package wicket.contrib.scriptaculous.examples;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.servlet.ServletHttpContext;

import wicket.protocol.http.WicketServlet;

public class AutocompleteExamplesLauncher {

	public static void main(String[] args) throws Exception {
        SocketListener listener = new SocketListener();
        listener.setPort(8080);


        Server jettyServer = new Server();
        jettyServer.addListener(listener);
        jettyServer.addContext(createContext("/autocompleteExamples", AutocompleteExamplesApplication.class, "Autocomplete Examples"));
        jettyServer.start();
	}

    private static ServletHttpContext createContext(String path, Class applicationClass, String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ServletHttpContext httpContext = new ServletHttpContext();
        httpContext.setContextPath(path);

        //lklevan: this is important, otherwise form posts get redirected and don't work
        httpContext.setRedirectNullPath(false);

        ServletHolder holder = httpContext.addServlet("Wicket", "/*", WicketServlet.class.getName());

        holder.setInitParameter("applicationClassName", applicationClass.getName());
        holder.setDisplayName(name);
        return httpContext;
    }
}
