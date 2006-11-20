package wicket.contrib.dojo.examples;

import java.io.Serializable;

public class MyTooltipModel implements Serializable
{
	private String label1;
	
	public MyTooltipModel(String label1)
	{
		this.label1 = label1;
	}
	
	public String getLabel1()
	{
		return label1;
	}
	
	public void setLabel1(String l1)
	{
		this.label1 = l1;
	}
}
