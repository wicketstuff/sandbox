package org.wicketstuff.pickwick;

import java.io.FileFilter;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.ContextParamWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class GuiceWebApplicationFactory extends ContextParamWebApplicationFactory {
	@Override
	protected WebApplication createApplication(String applicationClassName) {
		Injector inj = Guice.createInjector(Stage.DEVELOPMENT, getModule());
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = getClass().getClassLoader();
		}
		final Class applicationClass;
		try {
			applicationClass = loader.loadClass(applicationClassName);
		} catch (ClassNotFoundException e) {
			throw new WicketRuntimeException("Unable to create application of class " + applicationClassName, e);
		}

		if (!WebApplication.class.isAssignableFrom(applicationClass)) {
			throw new WicketRuntimeException("Application class " + applicationClassName
					+ " must be a subclass of WebApplication");
		}

		WebApplication app = inj.getInstance((Class<WebApplication>) applicationClass);
		app.addComponentInstantiationListener(new GuiceComponentInjector(app, inj));
		return app;
	}

	public static Module getModule() {
		return new Module() {
			public void configure(Binder binder) {
				binder.bind(FileFilter.class).to(ImageFilter.class);
			}
		};
	}
}
