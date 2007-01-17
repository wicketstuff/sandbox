package wicket.extensions.markup.html.beanedit;

import java.lang.annotation.*;

/**
 * 
 * @author Paolo Di Tommaso
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Label {
	String value();
}
