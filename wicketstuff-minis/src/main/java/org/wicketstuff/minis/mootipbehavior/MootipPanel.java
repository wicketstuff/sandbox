package org.wicketstuff.minis.mootipbehavior;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * Place one instance of this on your page to allow for ajax interaction
 * @author nino.martinez @ jayway.dk
 *
 */
public class MootipPanel extends Panel
{

	public static String mooTipPanelId="mooTipAjaxPanel";
	
	public static String mooTipContentId="mooTipContent";
	
	public static String getMooTipContentId()
	{
		return mooTipContentId;
	}

	public static String getMooTipPanelId()
	{
		return mooTipPanelId;
	}

	public MootipPanel()
	{
		super(mooTipPanelId);
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}
}
