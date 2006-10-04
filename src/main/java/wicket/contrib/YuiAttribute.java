package wicket.contrib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

public class YuiAttribute implements Serializable{
	private static final long serialVersionUID = 1L;
	Map propertyMap = new HashMap();
	
	public YuiAttribute() {
	}
  
	 public void add(String element, YuiProperty attribute) {	        
		 if (isValid(element, attribute)) {
			 propertyMap.put(element,attribute);
		 }
	 }
	 
	 public String getJsScript() {
		int count=0;
		String jsScript="";
		Set keySet= propertyMap.keySet();
		Iterator iter= keySet.iterator();
		while(iter.hasNext()){
			if(count==0){
				String aKey= (String)iter.next();
				jsScript= jsScript+ aKey+ ": {";
				YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
				jsScript = jsScript + aYuiProperty.getJsScript()+" }";
			}	
			else{
				String aKey= (String)iter.next();
				jsScript=  jsScript+ ", "+ aKey+ ": {";
				YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
				jsScript = jsScript + aYuiProperty.getJsScript()+" }";
			}
			count++;
		}
		return jsScript;
	}
	 
	 public String getReverseJsScript(){
			int count=0;
			String jsScript="";
			Set keySet= propertyMap.keySet();
			Iterator iter= keySet.iterator();
			while(iter.hasNext()){
				if(count==0){
					String aKey= (String)iter.next();
					jsScript= jsScript+ aKey+ ": {";
					YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
					jsScript = jsScript + aYuiProperty.getReverseJsScript()+" }";
				}	
				else{
					String aKey= (String)iter.next();
					jsScript=  jsScript+ ", "+ aKey+ ": {";
					YuiProperty aYuiProperty = (YuiProperty) propertyMap.get(aKey);
					jsScript = jsScript + aYuiProperty.getReverseJsScript()+" }";
				}
				count++;
			}
			return jsScript;
		}

	
	 /**
	  * validate that is valid key 
	  * e.g. : borderWidth, lineHeight ....
	  * 
	  * @param element
	  * @param value
	  * @return
	  */
	 private boolean isValid(String element, YuiProperty attribute) {
		 // TODO Auto-generated method stub
		 return true;
	 }

	public Map getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map propertyMap) {
		this.propertyMap = propertyMap;
	}
	
}
