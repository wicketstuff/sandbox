/*
 * Created on Dec 11, 2007
 */
package com.codecommit.wicket;

import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IChartData extends Serializable {
	
	public ChartDataEncoding getEncoding();
	
	public double[][] getData();
	
	public double getMax();
}
