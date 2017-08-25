package webScraper.stores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to collect list of Elvi stores from webpage.
 * 
 * @author Jānis Lazda
 */

public class ElviStores {

	private String homeURL = "http://www.elvi.lv/lv/veikali-elvi";
	private List<StoreData> storeList;

	/**
	 * Constructor of ElviStores. Inicializes storeList and calls collectData()
	 */
	public ElviStores() {
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
		Pattern namePattern = Pattern.compile("(?<=, )(.+?)(?=,)");
		Pattern addressPattern = Pattern
				.compile("(?<=\\]\\[0\\] = \")(.+?)(\\d+)(\\w?)(?=, )");
		Pattern addressPattern2 = Pattern
				.compile("(?<=\"&quot;)(.+?)(?=&quot;)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("//infoWindow=newgoogle.maps.InfoWindow();")) {
				while ((inputLine = in.readLine()) != null) {
					if ((inputLine.replaceAll("\\s", ""))
							.equals("for(i=0;i<gMarkers.length;i++){")) {
						break;
					}
					matcher = addressPattern2.matcher(inputLine);
					if (matcher.find()) {
						storeAddress = matcher.group();
						storeAddress = "\"" + fixData(storeAddress) + "\"";
						matcher = namePattern.matcher(inputLine);
						if (matcher.find()) {
							storeName = matcher.group();
							storeName = fixData(storeName);
							storeList.add(new StoreData("Elvi \"" + storeName
									+ "\"", storeAddress));
						} else {
							storeName = storeAddress;
							storeName = fixData(storeName);
							storeList.add(new StoreData("Elvi \"" + storeName
									+ "\"", storeAddress));
						}
						continue;
					}
					matcher = addressPattern.matcher(inputLine);
					if (matcher.find()) {
						storeAddress = matcher.group();
						storeAddress = fixData(storeAddress);
					} else {
						continue;
					}
					matcher = namePattern.matcher(inputLine);
					if (matcher.find()) {
						storeName = matcher.group();
						storeName = fixData(storeName);
						storeList.add(new StoreData("Elvi \"" + storeName
								+ "\"", storeAddress));
						continue;
					}
				}
				break;
			}
		}
		in.close();
	}

	/**
	 * Method for fixing some inconsistencies for store names and addresses
	 * 
	 * @param data
	 * @return fixed store name or address
	 */
	public String fixData(String data) {
		if (data.equals("Taurenes pag.")) {
			return "Taurene";
		} else if (data.equals("Ezeres pag.")) {
			return "Ezere";
		} else if (data.equals("Vilces pag.")) {
			return "Vilce";
		} else if (data.equals(" LV- 5101")) {
			return "Aizkraukle";
		} else if (data.equals("Veikals Vecsaule")) {
			return "Vecsaule";
		} else if (data.equals("Inčukalns, Laimes iela 1")) {
			return "Laimes iela 1";
		} else if (data.equals("Laimes iela 1")) {
			return "Inčukalns";
		} else if (data.equals("1.maija iela 72")) {
			return "Malta";
		} else if (data.equals("Jumpravas pagasts")) {
			return "Jumprava";
		} else if (data.equals("Malta, 1.maija iela 72")) {
			return "1.maija iela 72";
		} else if (data.equals("Ozolnieki, Rīgas iela 44")) {
			return "Rīgas iela 44";
		} else if (data.equals("Rīgas iela 44")) {
			return "Ozolnieki";
		} else if (data.equals("Ugāle  Ventspils novads")) {
			return "Ugāle";
		} else if (data.equals("Vangaži, Gaujas iela 10")) {
			return "Gaujas iela 10";
		} else if (data.equals("Gaujas iela 10")) {
			return "Vangaži";
		} else if (data.equals("Vangaži, Vidzemes iela 10")) {
			return "Vidzemes iela 10";
		} else if (data.equals("Vidzemes iela 10")) {
			return "Vangaži";
		} else if (data.equals("Bēne, Stacijas iela 9")) {
			return "Stacijas iela 9";
		} else if (data.equals("Stacijas iela 9")) {
			return "Bēne";
		} else
			return data;
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
