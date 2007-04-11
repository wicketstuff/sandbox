package wicket.extensions.markup.html.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;

/**
 * AJAX based {@link ISelectCallback}, which executes an AJAX event when the
 * user selects something in the date picker.
 * 
 * @author Frank Bille Jensen
 */
public abstract class AjaxSelectCallback implements ISelectCallback
{
	private static final Pattern ajaxScriptPattern = Pattern
			.compile("^var wcall=wicketAjaxGet\\('([^']+)'.*$");
	private static final SimpleDateFormat dateParamFormat = new SimpleDateFormat("yyyy-M-d-H-m");

	private abstract class SelectCallbackBehavior extends AbstractDefaultAjaxBehavior
	{
		public CharSequence getCallbackScript()
		{
			return super.getCallbackScript();
		}
	}

	private SelectCallbackBehavior eventBehavior;

	public final void bind(Component component)
	{
		eventBehavior = new SelectCallbackBehavior()
		{
			private static final long serialVersionUID = 1L;

			protected void respond(AjaxRequestTarget target)
			{
				Request request = RequestCycle.get().getRequest();

				String dateParam = request.getParameter("dateParam");
				Date date = null;

				try
				{
					date = dateParamFormat.parse(dateParam);
				}
				catch (ParseException e)
				{
					throw new WicketRuntimeException(e);
				}

				onEvent(target, date);
			}

			protected IAjaxCallDecorator getAjaxCallDecorator()
			{
				return new AjaxCallDecorator()
				{
					private static final long serialVersionUID = 1L;

					public CharSequence decorateScript(CharSequence script)
					{
						AppendingStringBuffer b = new AppendingStringBuffer();

						Matcher mat = ajaxScriptPattern.matcher(script);
						if (mat.matches())
						{
							String url = mat.group(1);
							String newUrl = url + "&dateParam='+dateParam+'";
							b.append(Strings.replaceAll(script, url, newUrl));
						}
						else
						{
							throw new WicketRuntimeException("Internal error in Wicket");
						}

						return b;
					}

				};
			}
		};

		component.add(eventBehavior);
	}

	public final CharSequence handleCallback()
	{
		return eventBehavior.getCallbackScript();
	}

	protected abstract void onEvent(AjaxRequestTarget target, Date date);
}