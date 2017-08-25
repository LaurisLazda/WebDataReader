package webScraper.stores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to collect list of SuperNetto stores from webpage.
 * 
 * @author Jānis Lazda
 */

public class SupernettoStores {

	private String homeURL = "http://supernetto.lv/actions/search";
	private List<StoreData> storeList;

	/**
	 * Constructor of SupernettoStores. Inicializes storeList and calls
	 * collectData()
	 */
	public SupernettoStores() {
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
		Pattern namePattern = Pattern
				.compile("(?<=\\s)([\\w])(.+?)(?=\\s+</div>)");
		Pattern addressPattern = Pattern
				.compile("(?<=<p class=\"address-line\">)(.+?)(?=</p>)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<divclass=\"map-search\"id=\"map-canvas\"style=\"height:350px;\"></div>")) {
				while ((inputLine = in.readLine()) != null) {
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<divclass=\"shops-search-results-keys\">")) {
						break;
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<divclass=\"title\">")) {
						inputLine = in.readLine();
						matcher = namePattern.matcher(inputLine);
						matcher.find();
						storeName = matcher.group();
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<divclass=\"address\">")) {
						inputLine = in.readLine();
						matcher = addressPattern.matcher(inputLine);
						matcher.find();
						storeAddress = matcher.group();
						storeList.add(new StoreData(storeName, storeAddress));
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
