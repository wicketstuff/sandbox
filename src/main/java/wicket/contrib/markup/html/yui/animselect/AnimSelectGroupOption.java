package wicket.contrib.markup.html.yui.animselect;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Allows the user to add AnimSelectOption
 * @author cptan
 *
 */
public class AnimSelectGroupOption implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean hideLabel= false;
	
	private ArrayList animSelectList= new ArrayList();
	
	public boolean add(AnimSelectOption animSelectOption){
		return animSelectList.add(animSelectOption);
	}
	
	public boolean remove(AnimSelectOption animSelectOption){
		return animSelectList.remove(animSelectOption);
	}
	
	public AnimSelectOption get(int index){
		return (AnimSelectOption) animSelectList.get(index);
	}
	
	public int getSize(){
		return animSelectList.size();
	}

	/**
	 * Get the hideLabel
	 * @return the hideLabel
	 */
	public boolean isHideLabel() {
		return hideLabel;
	}

	/**
	 * Set the hideLabel
	 * @param hideLabel the hideLabel to set
	 */
	public void setHideLabel(boolean hideLabel) {
		this.hideLabel = hideLabel;
	}
}
