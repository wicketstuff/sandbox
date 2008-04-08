/**
 * 
 */
package org.wicketstuff.picnik.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>
 * Created 30.03.2008 01:25:53
 * </p>
 * @author Rüdiger Schulz <rueschu@gmail.com>
 * @version $Revision$
 */
public class PicnikCall implements Serializable {

	public final static String PICNIK_API_URL = "http://www.picnik.com/service/";

	private GeneralSettings generalSettings = new GeneralSettings();

	private AppearanceSettings appearanceSettings = new AppearanceSettings();

	private BoxSettings boxSettings = new BoxSettings();

	private ExportSettings exportSettings = new ExportSettings();

	private ImportSettings importSettings = new ImportSettings();

	private NavigationSettings navigationSettings = new NavigationSettings();

	public Map<String, String> getParams() {
		Map<String, String> ret = new HashMap<String, String>();
		ret.putAll(getGeneralSettings().fillParameters().getParams());
		ret.putAll(getAppearanceSettings().fillParameters().getParams());
		ret.putAll(getBoxSettings().fillParameters().getParams());
		ret.putAll(getExportSettings().fillParameters().getParams());
		ret.putAll(getImportSettings().fillParameters().getParams());
		ret.putAll(getNavigationSettings().fillParameters().getParams());
		return ret;
	}

	/**
	 * Get the generalSettings.
	 * @return Returns the generalSettings.
	 */
	public GeneralSettings getGeneralSettings() {
		return generalSettings;
	}

	/**
	 * Set the generalSettings.
	 * @param generalSettings The generalSettings to set.
	 */
	public void setGeneralSettings(GeneralSettings generalSettings) {
		this.generalSettings = generalSettings;
	}

	/**
	 * Get the appearanceSettings.
	 * @return Returns the appearanceSettings.
	 */
	public AppearanceSettings getAppearanceSettings() {
		return appearanceSettings;
	}

	/**
	 * Set the appearanceSettings.
	 * @param appearanceSettings The appearanceSettings to set.
	 */
	public void setAppearanceSettings(AppearanceSettings appearanceSettings) {
		this.appearanceSettings = appearanceSettings;
	}

	/**
	 * Get the boxSettings.
	 * @return Returns the boxSettings.
	 */
	public BoxSettings getBoxSettings() {
		return boxSettings;
	}

	/**
	 * Set the boxSettings.
	 * @param boxSettings The boxSettings to set.
	 */
	public void setBoxSettings(BoxSettings boxSettings) {
		this.boxSettings = boxSettings;
	}

	/**
	 * Get the exportSettings.
	 * @return Returns the exportSettings.
	 */
	public ExportSettings getExportSettings() {
		return exportSettings;
	}

	/**
	 * Set the exportSettings.
	 * @param exportSettings The exportSettings to set.
	 */
	public void setExportSettings(ExportSettings exportSettings) {
		this.exportSettings = exportSettings;
	}

	/**
	 * Get the importSettings.
	 * @return Returns the importSettings.
	 */
	public ImportSettings getImportSettings() {
		return importSettings;
	}

	/**
	 * Set the importSettings.
	 * @param importSettings The importSettings to set.
	 */
	public void setImportSettings(ImportSettings importSettings) {
		this.importSettings = importSettings;
	}

	/**
	 * Get the navigationSettings.
	 * @return Returns the navigationSettings.
	 */
	public NavigationSettings getNavigationSettings() {
		return navigationSettings;
	}

	/**
	 * Set the navigationSettings.
	 * @param navigationSettings The navigationSettings to set.
	 */
	public void setNavigationSettings(NavigationSettings navigationSettings) {
		this.navigationSettings = navigationSettings;
	}

}
