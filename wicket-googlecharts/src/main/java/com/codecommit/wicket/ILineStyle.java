/*
 * Created on Dec 11, 2007
 */
package com.codecommit.wicket;

import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface ILineStyle extends Serializable {
	
	public int getThickness();
	
	public int getSegmentLength();
	
	public int getBlankLength();
}
