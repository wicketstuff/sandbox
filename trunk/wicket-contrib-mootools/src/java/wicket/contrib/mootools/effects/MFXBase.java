package wicket.contrib.mootools.effects;

import org.apache.wicket.Component;

import wicket.contrib.mootools.AbstractRequireMooStatelessBehavior;

/**
 * This class handles base options for Mootools
 * @author victori
 *
 */
public abstract class  MFXBase extends AbstractRequireMooStatelessBehavior implements MFXBaseInterface {
	private static final long serialVersionUID = 1L;
	private int duration = 0;
	private String style;
	private String target;
	private String transition;
	private String onStart;
	private String onComplete;
	private String unit;
	private String wait;
	private String fps;
	private String startValue;
	private String endValue;
	private String method;
	private String update;
	
	
	
	/**
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#mooFunction()
	 */
	public abstract String mooFunction();
	
	
	/**
	 * @see wicket.contrib.mootools.AbstractRequireMooStatelessBehavior#onRendered(org.apache.wicket.Component)
	 */
	public void onRendered(Component arg0) {
		setTarget(arg0.getMarkupId());
		super.onRendered(arg0);
	}


	/**
	 * Write out Mootool options in Javascript hash form
	 * @return
	 */
	public String writeOptions() {
		StringBuffer buf = new StringBuffer();
		
		if(duration != 0)
			buf.append("duration: "+String.valueOf(duration)+",");
		if(transition != null)
			buf.append("transition: "+transition+",");
		if(onStart != null)
			buf.append("onStart: "+getOnStart()+",");
		if(onComplete != null)
			buf.append("onComplete: "+getOnComplete()+",");
		if(unit != null)
			buf.append("unit: "+unit+",");
		if(wait != null)
			buf.append("wait: "+wait+",");
		if(fps != null)
			buf.append("fps: "+fps+",");
		if(update != null)
			buf.append("update: '"+update+"',");
		if(method != null)
			buf.append("method: '"+method+"',");
		
		if(buf.length()>0)
			return "{"+buf.substring(0, buf.length()-1)+"}";
		else
			return "{}";
		
	}

	/**
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setDuration(int)
	 */
	public MFXBaseInterface setDuration(int duration) {
		this.duration = duration;
		return this;
	}
	
	/**
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setTarget(java.lang.String)
	 */
	public MFXBaseInterface setTarget(String target) {
		this.target = target;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getTarget()
	 */
	public String getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getDuration()
	 */
	public int getDuration() {
		return duration;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setTransition(java.lang.String)
	 */
	public MFXBaseInterface setTransition(String transition) {
		this.transition = transition;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getTransition()
	 */
	public String getTransition() {
		return transition;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setOnStart(java.lang.String)
	 */
	public MFXBaseInterface setOnStart(String onStart) {
		this.onStart = onStart;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getOnStart()
	 */
	public String getOnStart() {
		StringBuffer buf = new StringBuffer();
		buf.append("function() {");
		buf.append(onStart);
		buf.append("}");
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setOnComplete(java.lang.String)
	 */
	public MFXBaseInterface setOnComplete(String onComplete) {
		this.onComplete = onComplete;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getOnComplete()
	 */
	public String getOnComplete() {
		StringBuffer buf = new StringBuffer();
		buf.append("function() {");
		buf.append(onComplete);
		buf.append("}");
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setUnit(java.lang.String)
	 */
	public MFXBaseInterface setUnit(String unit) {
		this.unit = unit;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getUnit()
	 */
	public String getUnit() {
		return unit;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setWait(java.lang.String)
	 */
	public MFXBaseInterface setWait(String wait) {
		this.wait = wait;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getWait()
	 */
	public String getWait() {
		return wait;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#setFps(java.lang.String)
	 */
	public MFXBaseInterface setFps(String fps) {
		this.fps = fps;
		return this;
	}

	/* (non-Javadoc)
	 * @see wicket.contrib.mootools.effects.MFXBaseInterface#getFps()
	 */
	public String getFps() {
		return fps;
	}

	/**
	 * Set style
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Get style
	 * @return
	 */
	public String getStyle() {
		return style;
	}
	
	/**
	 * Set start value
	 * @param startValue
	 * @return
	 */
	public MFXBaseInterface setStartValue(String startValue) {
		this.startValue = startValue;
		return this;
	}

	/**
	 * Get start value
	 * @return
	 */
	public String getStartValue() {
		return startValue;
	}

	/**
	 * Set end value
	 * @param endValue
	 * @return
	 */
	public MFXBaseInterface setEndValue(String endValue) {
		this.endValue = endValue;
		return this;
	}

	/**
	 * Get end Value
	 * @return
	 */
	public String getEndValue() {
		return endValue;
	}

	/**
	 * Get MooTools function
	 * @return
	 */
	public String getMooFunction() {
		return mooFunction();
	}


	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}


	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}


	/**
	 * @param update the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}


	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}

}
