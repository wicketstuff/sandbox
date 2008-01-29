/*
 * Created on Dec 11, 2007
 */
package com.codecommit.wicket;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IChartAxis extends Serializable {
	
	public ChartAxisType getType();
	
	public String[] getLabels();
	
	public double[] getPositions();
	
	public Range getRange();
	
	public Color getColor();
	
	public int getFontSize();
	
	public AxisAlignment getAlignment();
}
