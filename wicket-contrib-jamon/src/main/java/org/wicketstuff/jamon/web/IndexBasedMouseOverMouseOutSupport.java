package org.wicketstuff.jamon.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
/**
 * Util class to add mouseover, mouseout behavior. Typically this is used
 * in listviews and tables. The css classes "even" and "odd" should be present.
 * @author lars
 *
 */
class IndexBasedMouseOverMouseOutSupport {
    
    private IndexBasedMouseOverMouseOutSupport() {}
    /**
     * Add mouseover and mouseout behavior.
     * @param <T> The type of Component.
     * @param component The component to add the behavior to.
     * @param index The index of the Component in a repeatable container (such as table, listview). The index
     *  is used to determine the original className for the mouseout event. If index is an odd number 
     *  then "odd" is used as css class, otherwise "even".
     * @return The component, handy for chaining method calls.
     */
    public static <T extends Component> T add(T component, int index) {
        String originalClassName = (index % 2 == 0) ? "even" : "odd";
        component.add(new AttributeModifier("onmouseover", true, new Model("this.className='selected'")));
        component.add(new AttributeModifier("onmouseout", true, new Model(String.format("this.className='%s'", originalClassName))));
        return component;
    }
}
