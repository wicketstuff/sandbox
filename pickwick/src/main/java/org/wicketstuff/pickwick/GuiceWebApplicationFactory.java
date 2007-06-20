package org.wicketstuff.pickwick;

import java.io.FileFilter;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class GuiceWebApplicationFactory implements IWebApplicationFactory {
	public WebApplication createApplication(WicketFilter filter) {
		Injector inj = Guice.createInjector(Stage.DEVELOPMENT, getModule());
		WebApplication app = inj.getInstance(PickWickApplication.class);
		app.addComponentInstantiationListener(new GuiceComponentInjector(app, inj));
		return app;
	}
	protected Module getModule() {
		return new Module() {
			public void configure(Binder binder) {
				binder.bind(FileFilter.class).to(ImageFilter.class);
			}
		};
	}
}
