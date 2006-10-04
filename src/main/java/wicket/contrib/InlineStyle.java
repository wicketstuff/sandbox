package wicket.contrib;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InlineStyle implements Serializable {

    // valid Styles....
    
    Map styleMap = new HashMap();
    
    /**
     * 
     *
     */
    public InlineStyle() {
    }

    /**
     * 
     * @param map
     */
    public InlineStyle(Map map) {
        this.styleMap.putAll(map);
    }

    /**
     * @returns a ":" separated String to be used a an Inline Style
     * 
     */
    
    public String getStyle() {
        
        final StringBuffer buffer = new StringBuffer();
        for (final Iterator iterator = styleMap.entrySet().iterator(); iterator.hasNext();)
        {
            final Map.Entry entry = (Map.Entry)iterator.next();
            final Object value = entry.getValue();
            if (value != null)
            {
                buffer.append(entry.getKey());
                buffer.append(":");
                buffer.append(value);
                buffer.append(";");
            }
        }
        return buffer.toString();
    }

    /**
     * adds a style element with teh value...
     * TODO : check for valid element string and value ? override? 
     * 
     * @param element
     * @param value
     */
    
    public void add(String element, String value) {
        
        if (isValid(element, value)) {
//            if (styleMap.get(element) != null) {
//            }
            styleMap.put(element,value);
        }
    }

    /**
     * validate is this style is valid for this element value...
     * now it just returns true all the time...
     *  
     * @param element
     * @param value
     * @return
     */
    private boolean isValid(String element, String value) {
        // TODO Auto-generated method stub
        return true;
    }
    
    
    
    
}
