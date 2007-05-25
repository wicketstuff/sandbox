package wicket.contrib.mootools.events;

import java.util.List;

import org.apache.wicket.behavior.IBehavior;

import wicket.contrib.mootools.effects.MFXBaseInterface;

public interface MFXEventInterface extends IBehavior {
	public MFXEventInterface addStyle(MFXBaseInterface style);
	public MFXEventInterface addAction(String action) ;
	public MFXEventInterface setMfxStyles(List mfxStyles);
	public List getMfxStyles();
	public void setActions(List actions);
	public List getActions();
	public String mooFunction();
}
