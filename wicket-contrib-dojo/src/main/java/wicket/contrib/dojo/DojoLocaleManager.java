package wicket.contrib.dojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import wicket.markup.html.IHeaderResponse;

public class DojoLocaleManager {
	private static DojoLocaleManager manager;
	private Set locales;
	
	private DojoLocaleManager(){
		locales = new HashSet();
	}
	
	public static DojoLocaleManager getInstance(){
		if(manager == null){
			manager = new DojoLocaleManager();
		}
		return manager;
	}
	
	public void addLocale(Locale locale){
		locales.add(locale.toString().replace("_", "-").toLowerCase());
	}
	
	private String generateLocaleJs(){
		if(locales.size() != 0){
			String js = "" +
				"if(djConfig == null){\n" +
				"	var djConfig = {};\n" +
				"}" +
				"if(djConfig.extraLocale == null){\n" +
				"	djConfig.extraLocale = new Array();\n" +
					"}\n";
			Iterator ite = locales.iterator();
			while(ite.hasNext()){
				js += "djConfig.extraLocale.push('" + ite.next() + "')\n";
			}
			
			return js;
		}
		return null;
	}
	
	public void renderLocale(IHeaderResponse response){
		String js = generateLocaleJs();
		if (js != null){
			response.renderJavascript(js , "localeManagerJs");
		}
	}
}
