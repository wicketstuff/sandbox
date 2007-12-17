package wicket.contrib.mootools.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;


import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;
import wicket.contrib.mootools.MFXMooBindable;
import wicket.contrib.mootools.effects.MFXBaseInterface;
import wicket.contrib.mootools.effects.MFXStyle;


/**
 * Class for adding Window Load actions
 * @author victori
 *
 */
public class MFXWindowLoad extends AbstractRequireMooStatelessBehavior implements MFXEventInterface  {
	private static final long serialVersionUID = 1L;
	private List mfxStyles = new ArrayList();	
	private List actions = new ArrayList();
	private String target;
	
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#onRendered(org.apache.wicket.Component)
	 */
	public void onRendered(Component arg0) {
		
		if(isMoobindable(arg0))
			actions.add(((MFXMooBindable)arg0).mooFunction());
		
		setTarget(arg0.getMarkupId());
		addMooDomFunction(mooFunction());
		super.onRendered(arg0);
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#mooFunction()
	 */
	public String mooFunction() {
		return writeActions()+writeStyles();
	}
	
	/**
	 * Write pending actions
	 * @return
	 */
	public String writeActions() {
		String str="";
		Iterator i = actions.iterator();
		while(i.hasNext())
			str+=String.valueOf(i.next());
		return str;
	}
	
	/**
	 * Write out MFXStyles {@link MFXStyle}
	 * @return
	 */
	public String writeStyles() {
		StringBuffer buf = new StringBuffer();
		for(int j = 0 ; j < mfxStyles.size(); j++ ) {
			final String effectId = getTarget()+String.valueOf(j);
			final MFXStyle style = (MFXStyle)mfxStyles.get(j);
			style.setTarget(getTarget());
			buf.append(effectId);
			buf.append(" = "+style.getMooFunction());
			
			String delimeter = "";
			if(style.getStartValue().substring(0, 1).equals("#"))
				delimeter = "'";
			
			buf.append(effectId+".start("+delimeter+style.getStartValue()+delimeter+","
						+delimeter+style.getEndValue()+delimeter+");");	
		}
		return buf.toString();
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#addStyle(wicket.contrib.mootools.effects.MFXBaseInterface)
	 */
	public MFXEventInterface addStyle(MFXBaseInterface style) {
		mfxStyles.add(style);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#addAction(java.lang.String)
	 */
	public MFXEventInterface addAction(String action) {
		actions.add(action);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#setMfxStyles(java.util.List)
	 */
	public MFXEventInterface setMfxStyles(List mfxStyles) {
		this.mfxStyles = mfxStyles;
		return this;
	}


	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#getMfxStyles()
	 */
	public List getMfxStyles() {
		return mfxStyles;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#setActions(java.util.List)
	 */
	public void setActions(List actions) {
		this.actions = actions;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.events.MFXEventInterface#getActions()
	 */
	public List getActions() {
		return actions;
	}
	
	/**
	 * Set target DOM Element
	 * @param target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Get target DOM Element
	 * @return
	 */
	public String getTarget() {
		return target;
	}


}
