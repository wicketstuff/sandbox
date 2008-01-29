/*
 * Created on Dec 11, 2007
 */
package com.codecommit.wicket;

import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IChartGrid extends Serializable {
	
	public double getXStepSize();
	
	public double getYStepSize();
	
	public int getSegmentLength();
	
	public int getBlankLength();
}
