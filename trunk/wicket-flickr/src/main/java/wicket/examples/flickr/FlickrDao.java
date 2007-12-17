package wicket.examples.flickr;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;

/**
 * Wrapper around the Flickr Api.
 */
public class FlickrDao {
	/** My API key, is set using a web.xml init-parameter. */
	public static String FLICKR_KEY;

	/**
	 * Constant URL's for working without FLICKR_KEY.
	 */
	private static String[] FLOWERS = {
			"http://static.flickr.com/14/92739343_d4dccf9f63_s.jpg",
			"http://static.flickr.com/23/92738399_5d7326174b_s.jpg",
			"http://static.flickr.com/32/92732056_2e4a2aa606_s.jpg",
			"http://static.flickr.com/39/92731528_773bf59758_s.jpg",
			"http://static.flickr.com/26/92731189_38770b3e99_s.jpg",
			"http://static.flickr.com/11/92729727_bdf2534605_s.jpg",
			"http://static.flickr.com/40/92727009_6da0730cd2_s.jpg",
			"http://static.flickr.com/23/92725895_97001944b1_s.jpg",
			"http://static.flickr.com/11/92724122_13b3309c03_s.jpg",
			"http://static.flickr.com/38/92724125_d8da613ff1_s.jpg",
			"http://static.flickr.com/19/92724124_97b0224d27_s.jpg",
			"http://static.flickr.com/22/92724119_d9b514b37d_s.jpg",
			"http://static.flickr.com/40/92723090_ccd03ab825_s.jpg",
			"http://static.flickr.com/32/92722625_62e3006f54_s.jpg",
			"http://static.flickr.com/42/92719038_e7ead83db8_s.jpg",
			"http://static.flickr.com/38/92718029_c747e0c27d_s.jpg",
			"http://static.flickr.com/27/92717023_1d067e8447_s.jpg",
			"http://static.flickr.com/33/92715696_f741fb7f81_s.jpg",
			"http://static.flickr.com/14/92715694_94268c081c_s.jpg",
			"http://static.flickr.com/34/92713820_344b8c5fce_s.jpg",
			"http://static.flickr.com/28/92706830_2c69f32291_s.jpg",
			"http://static.flickr.com/31/92706702_d108030343_s.jpg",
			"http://static.flickr.com/41/92705998_a28a549a6e_s.jpg",
			"http://static.flickr.com/16/92702814_4100c2ea83_s.jpg", };

	/**
	 * Constant URL's for working without FLICKR_KEY.
	 */
	private static String[] COLORS = {
			"http://static.flickr.com/21/92736979_cea216909c_s.jpg",
			"http://static.flickr.com/27/92736980_7bfc650a43_s.jpg",
			"http://static.flickr.com/38/92736981_343e1bb5ce_s.jpg",
			"http://static.flickr.com/39/92731528_773bf59758_s.jpg",
			"http://static.flickr.com/11/92729727_bdf2534605_s.jpg",
			"http://static.flickr.com/34/92727348_d1b9dad364_s.jpg",
			"http://static.flickr.com/31/92726872_f7fe63c0e0_s.jpg",
			"http://static.flickr.com/11/92723613_a32e6257fc_s.jpg",
			"http://static.flickr.com/30/92723617_509d11680a_s.jpg",
			"http://static.flickr.com/12/92723618_3ecd777ae6_s.jpg",
			"http://static.flickr.com/36/92722165_3e111b5a90_s.jpg",
			"http://static.flickr.com/43/92722160_65446d5c7c_s.jpg",
			"http://static.flickr.com/43/92722171_87605f6278_s.jpg",
			"http://static.flickr.com/33/92722163_6a41b16ee9_s.jpg",
			"http://static.flickr.com/37/92722169_b04de816cf_s.jpg",
			"http://static.flickr.com/22/92722167_be781ae2d4_s.jpg",
			"http://static.flickr.com/11/92722015_4a52eae106_s.jpg",
			"http://static.flickr.com/43/92722014_79e0637cb8_s.jpg",
			"http://static.flickr.com/27/92720508_892fc053a8_s.jpg",
			"http://static.flickr.com/35/92717738_d2e3efad75_s.jpg",
			"http://static.flickr.com/37/92717389_3bb8e479dc_s.jpg",
			"http://static.flickr.com/24/92717361_c29577e8fd_s.jpg",
			"http://static.flickr.com/15/92717304_b52bf7cab2_s.jpg",
			"http://static.flickr.com/43/92717275_5327959a4c_s.jpg", };

	/**
	 * Gets static url's to 75x75px thumbnails of images which are tagged with
	 * the supplied tags.
	 * 
	 * @param tags
	 *            the tags used for searching the Flickr site
	 * @return the list of static url's to the found images
	 */
	public List<String> getSmallSquareImages(String[] tags) {
		if (FLICKR_KEY == null) {
			return getImagesOffline(tags);
		}
		return getImagesOnline(tags);
	}

	/**
	 * Performs a dummy flickr lookup with stored image URL's.
	 * @param tags
	 * @return
	 */
	private List<String> getImagesOffline(String[] tags) {
		if (tags.length > 0) {
			if ("flower".equalsIgnoreCase(tags[0])) {
				return Arrays.asList(FLOWERS);
			} else if ("color".equalsIgnoreCase(tags[0])) {
				return Arrays.asList(COLORS);
			}
		}
		return Arrays.asList(new String[] {});
	}

	/**
	 * Performs a one and only flickr lookup with the provided flickr key.
	 * @param tags
	 * @return
	 */
	private List<String> getImagesOnline(String[] tags) {
		List<String> images = new Vector<String>();

		Flickr flickr = new Flickr(FLICKR_KEY);
		PhotosInterface photosInterface = flickr.getPhotosInterface();
		try {
			SearchParameters pars = new SearchParameters();
			pars.setTags(tags);
			PhotoList list = photosInterface.search(pars, 24, 1);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Photo element = (Photo) iter.next();
				String url = "http://static.flickr.com/" + element.getServer()
						+ "/" + element.getId() + "_" + element.getSecret()
						+ "_s.jpg";
				images.add(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return images;
	}
}
