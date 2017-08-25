package webScraper.stores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to collect list of Mego stores from webpage.
 * 
 * @author Jānis Lazda
 */

public class MegoStores {

	private String homeURL = "http://www.mego.lv/kontakti";
	private List<StoreData> storeList;

	/**
	 * Constructor of MegoStores. Inicializes storeList and calls collectData()
	 */
	public MegoStores() {
		storeList = new ArrayList<>();
		try {
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method which reads webpage source and populates storeList with store
	 * names and addresses
	 */
	public void collectData() throws Exception {
		URL url = new URL(homeURL);
		String storeName = "";
		String storeAddress = "";
		String inputLine;
		Matcher matcher;
		Pattern namePattern = Pattern.compile("(?<=<h3>)(.+?)(?=</h3>)");
		Pattern addressPattern = Pattern.compile("(?<=<div>)(.+?)(?=</div>)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<pstyle=\"margin:0cm;margin-bottom:.0001pt\">&nbsp;</p>")) {
				while ((inputLine = in.readLine()) != null) {
					matcher = namePattern.matcher(inputLine);
					if (matcher.find()) {
						storeName = matcher.group();
					}
					matcher = addressPattern.matcher(inputLine);
					if (matcher.find()) {
						storeAddress = matcher.group();
						storeList.add(new StoreData("Mego \"" + storeName
								+ "\"", storeAddress));
					}
				}
				break;
			}
		}
		in.close();
	}

	/**
	 * Method to pass collected data
	 * 
	 * @return list of StoreData
	 */
	public List<StoreData> getStores() {
		return storeList;
	}
}
