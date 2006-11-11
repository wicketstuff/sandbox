package wicket.contrib.dojo.examples;

import wicket.Component;
import wicket.ajax.AjaxRequestTarget;
import wicket.contrib.dojo.AbstractDojoTimerBehavior;
import wicket.contrib.dojo.DojoSelfUpdatingTimerBehavior;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.model.Model;
import wicket.util.time.Duration;

public class SimpleAutoRefreshExample extends WebPage {

	Label label;
	String display;
	int timer;
	
	public SimpleAutoRefreshExample() {
		timer = 0;
		updateTimer();
		label  = new Label(this, "label");
		label.add(new DojoSelfUpdatingTimerBehavior(Duration.milliseconds(1000)){

			@Override
			protected void onPostProcessTarget(final AjaxRequestTarget target){
				updateTimer();
				getComponent().setModel(new Model<String>(display));
				
			}
			
		});
	}
	
	public void updateTimer(){
		display = "resfreshed " + timer + " time(s)";
		timer++;
	}
	
	

}
