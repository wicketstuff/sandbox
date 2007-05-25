package wicket.contrib.mootools.effects;

public interface MFXBaseInterface {

	public abstract MFXBaseInterface setDuration(int duration);

	public abstract MFXBaseInterface setTarget(String target);

	public abstract String getTarget();

	public abstract int getDuration();

	public abstract MFXBaseInterface setTransition(String transition);

	public abstract String getTransition();

	public abstract MFXBaseInterface setOnStart(String onStart);

	public abstract String getOnStart();

	public abstract MFXBaseInterface setOnComplete(String onComplete);

	public abstract String getOnComplete();

	public abstract MFXBaseInterface setUnit(String unit);

	public abstract String getUnit();

	public abstract MFXBaseInterface setWait(String wait);

	public abstract String getWait();

	public abstract MFXBaseInterface setFps(String fps);

	public abstract String getFps();
}