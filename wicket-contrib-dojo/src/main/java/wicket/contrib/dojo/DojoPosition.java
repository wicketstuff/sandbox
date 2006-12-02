package wicket.contrib.dojo;


public enum DojoPosition
{
	//"br-up", "br-left", "bl-up", "bl-right", "tr-down", "tr-left", "tl-down", "tl-right"
	BOTTOM_RIGHT_UP("br-up"),
	BOTTOM_RIGHT_LEFT("br-left"),
	BOTTOM_LEFT_UP("bl-up"),
	BOTTOM_LEFT_RIGHT("bl-right"),
	TOP_RIGHT_DOWM("tr-down"),
	TOP_RIGHT_LEFT("tr-left"),
	TOP_LEFT_DOWN("tl-down"),
	TOP_LEFT_RIGHT("tl-right");

	
	private final String position;

	private DojoPosition(String position)
	{
		this.position = position;
	}

	/**
	 * Actual Position
	 * 
	 * @return the position
	 */
	public String getPosition()
	{
		return position;
	}
}
