package wicket.contrib.activewidgets;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractHeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWHeaderContributor extends AbstractHeaderContributor {


    
	@Override
	public IHeaderContributor[] getHeaderContributors() {
		List<IHeaderContributor> contributors = new ArrayList<IHeaderContributor>();
		IHeaderContributor mainJs = new IHeaderContributor () {

			ResourceReference mainJs = new ResourceReference(AWHeaderContributor.class
					, ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/lib/aw.js");
			public void renderHead(IHeaderResponse response) {
				response.renderJavascriptReference(mainJs);
			}
			
		};
		
		contributors.add(mainJs);

		IHeaderContributor mainCss = new IHeaderContributor () {
			ResourceReference mainCss = new ResourceReference(AWHeaderContributor.class, 
					ActiveWidgetsConfiguration.AW_LIB_HOME_PATH + "/styles/xp/aw.css");
			public void renderHead(IHeaderResponse response) {
				response.renderCSSReference(mainCss);
			}
			
		};
		contributors.add(mainCss);

        return contributors.toArray(new IHeaderContributor[]{});
	}
	
}
