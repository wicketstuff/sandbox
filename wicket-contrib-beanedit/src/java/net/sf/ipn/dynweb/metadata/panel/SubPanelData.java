/*
 * Created on Feb 1, 2005
 */
package net.sf.ipn.dynweb.metadata.panel;

import java.io.Serializable;

/**
 * @author Jonathan Carlson TODO Provides...
 */
public class SubPanelData implements Serializable
{

	private String expression;

	private PanelData panelData;

	public SubPanelData(String expression, PanelData panelData)
	{
		super();
		if (expression == null)
			throw new IllegalArgumentException(
					"SubPanelData(expr,panelData): expr may not be null.");
		this.expression = expression;
		this.panelData = panelData;
	}

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String name)
	{
		this.expression = name;
	}

	public PanelData getPanelData()
	{
		return panelData;
	}

	public void setPanelData(PanelData panelData)
	{
		this.panelData = panelData;
	}
}
