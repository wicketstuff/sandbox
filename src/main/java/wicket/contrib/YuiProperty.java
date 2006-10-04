package wicket.contrib;

import java.io.Serializable;

public class YuiProperty implements Serializable{
	private static final long serialVersionUID = 1L;

	private String by="";
	private String to="";
	private String from="";
	private String unit="";
	
	private String fontFamily;
	private int fontSize;
	private String fontColor;
	private String textAlign;
	
	/**
	 * Constructor where unit is required.
	 * @param toBy
	 * @param from
	 * @param unit
	 * @param isTo
	 */
	public YuiProperty(String toBy, String from, String unit, boolean isTo){
		if(isTo == true){
			this.to=toBy;
			this.from=from;
			this.unit=unit;
		}
		else{
			this.by= toBy;
			this.from= from;
			this.unit=unit;
		}
	}
	
	/**
	 * Constructor where unit is not required.
	 * @param toBy
	 * @param from
	 * @param isTo
	 */
	public YuiProperty(String toBy, String from, boolean isTo){
		if(isTo == true){
			this.to=toBy;
			this.from=from;
		}
		else{
			this.by= toBy;
			this.from= from;
		}
	}
	
	public String getCssScript(){
		return "";
	}
	
	public String getJsScript() {
		String jsScript="";
		
		if(getBy()!=""){
			if(jsScript==""){jsScript= " by:" + getBy();}
			else {jsScript= jsScript + ", by:" + getBy();}
			
			if(jsScript==""){jsScript= " from:" + getFrom();}
			else {jsScript= jsScript + ", from:" + getFrom();}
			
			if(getUnit()!=""){
				if(jsScript==""){jsScript= " unit:" + getUnit();}
				else {jsScript= jsScript + ", unit:" + getUnit();}
			}	
		}
		if(getTo()!=""){
			if(jsScript=="") {jsScript= " to:" + getTo();}
			else {jsScript= jsScript + ", to:" + getTo();}
			
			if(jsScript==""){jsScript= " from:" + getFrom();}
			else {jsScript= jsScript + ", from:" + getFrom();}
			
			if(getUnit()!=""){
				if(jsScript==""){jsScript= " unit:" + getUnit();}
				else {jsScript= jsScript + ", unit:" + getUnit();}
			}	
		}
		
		return jsScript;
	}
	
	public String getReverseJsScript() {
		String jsScript="";
		
		if(getBy()!=""){
			if(jsScript==""){jsScript= " by:" + getFrom();}
			else {jsScript= jsScript + ", by:" + getFrom();}
			
			if(jsScript=="") {jsScript= " from:" + getBy();}
			else {jsScript= jsScript + ", from:" + getBy();}
			
			if(getUnit()!=""){
				if(jsScript==""){jsScript= " unit:" + getUnit();}
				else {jsScript= jsScript + ", unit:" + getUnit();}
			}	
		}
		if(getTo()!=""){
			if(jsScript==""){jsScript= " to:" + getFrom();}
			else {jsScript= jsScript + ", to:" + getFrom();}
			
			if(jsScript=="") {jsScript= " from:" + getTo();}
			else {jsScript= jsScript + ", from:" + getTo();}
			
			if(getUnit()!=""){
				if(jsScript==""){jsScript= " unit:" + getUnit();}
				else {jsScript= jsScript + ", unit:" + getUnit();}
			}	
		}
		return jsScript;
	}

	public String getBy() {
		return by;
	}
	
	public void setBy(String by) {
		this.by = by;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
