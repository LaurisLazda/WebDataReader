package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to collect Mego stores from webpage.
 */

public class MegoStores {

	private String homeURL = "http://www.mego.lv/kontakti";
	private List<StoreData> storeList;

	public MegoStores() {
		storeList = new ArrayList<>();
		try {
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void collectData() throws Exception {
		URL url = new URL(homeURL);
		String storeName = "";
		String storeAddress = "";
		String inputLine;
		Matcher matcher;
		Pattern namePattern = Pattern.compile("(?<=<h3>)(.+?)(?=</h3>)");
		Pattern addressPattern = Pattern
				.compile("(?<=<div>)(.+?)(\\d)(\\w?)(\\s?)(?=</div>)");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			// if ((inputLine.replaceAll("\\s", ""))
			// .equals("Sv.08:00&ndash;19:00</p>")) {
			// break;
			// }
			matcher = namePattern.matcher(inputLine);
			if (matcher.find()) {
				storeName = matcher.group();
			}

			matcher = addressPattern.matcher(inputLine);
			if (matcher.find()) {
				storeAddress = matcher.group();
				storeList.add(new StoreData("Mego \"" + storeName + "\"",
						storeAddress));
			}
		}
		in.close();
	}

	public List<StoreData> getStores() {
		return storeList;
	}
}
