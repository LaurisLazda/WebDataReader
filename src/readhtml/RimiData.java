package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RimiData {

	// TODO: check if server returns answer, if not handle exception and delay
	// search for ~5 minutes.
	// TODO: read categories of groceries
	private List<String> products;
	private List<Double> prices;
	private List<String> urlList;
	private List<String> categoryIDs;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RimiData data = new RimiData();
	}

	public RimiData() {
		products = new ArrayList<>();
		prices = new ArrayList<>();
		urlList = new ArrayList<>();
		categoryIDs = new ArrayList<>();

		try {
			collectURLs();
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectData() throws Exception {
		for (String string : urlList) {
			URL url = new URL(string);
			String inputLine;
			int pagesCount = 0;
			Pattern pageCount = Pattern
					.compile("(?<=<b>1 / )(\\d{1,2})(?=</b>)");
			Matcher matcher;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			while ((inputLine = in.readLine()) != null) {
				if ((inputLine.replaceAll("\\s", ""))
						.equals("<divclass=\"category-itemsjs-cat-items\">")) {
					inputLine = in.readLine();
					matcher = pageCount.matcher(inputLine);
					matcher.find();
					pagesCount = Integer.parseInt(matcher.group());
					break;
				}
			}

			for (int i = 1; i <= pagesCount; i++) {
				if (pagesCount == 1) {
					readPage(url);
				} else {
					url = new URL(string + "/page/" + i);
					readPage(url);
				}
			}
			in.close();
		}
	}

	private void readPage(URL url) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;
		Pattern namePattern = Pattern.compile("(?<=name\">)(.+?)(?=</div>)");
		Pattern wholePricePattern = Pattern
				.compile("(?<=whole-number \">)(\\d{1,3})(?=</span>)");
		Pattern decimalPricePattern = Pattern
				.compile("(?<=decimal\">)(\\d{2})(?=</span>)");
		Matcher matcher;

		while ((inputLine = in.readLine()) != null) {
			boolean isTable = false;
			boolean isContent = false;
			boolean priceBubble = false;
			String wholePrice;
			String decimalPrice;
			String allPrice;
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<divclass=\"products-shelfjs-products-shelf\">")) {
				while ((inputLine = in.readLine()) != null) {
					if (isContent) {
						if ((inputLine.replaceAll("\\s", "")).equals("</ul>")) {
							break;
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"inforelativeclear\">")) {
							inputLine = in.readLine();
							matcher = namePattern.matcher(inputLine);
							matcher.find();
							products.add(matcher.group());
							// temporary output for testing
							// System.out.println(matcher.group());
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"price-bubble\">")) {
							priceBubble = true;
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"price\">")) {
							if (priceBubble) {
								priceBubble = false;
								continue;
							}
							inputLine = in.readLine();
							matcher = wholePricePattern.matcher(inputLine);
							matcher.find();
							wholePrice = matcher.group();
							inputLine = in.readLine();
							matcher = decimalPricePattern.matcher(inputLine);
							matcher.find();
							decimalPrice = matcher.group();
							allPrice = wholePrice + "." + decimalPrice;
							prices.add(Double.parseDouble(allPrice));
							// temporary output for testing
							// System.out.println(allPrice);
						}
					}
					if (isTable) {
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<liclass=\"relativeitemeffectfade-shadowjs-shelf-itemshelf-item\"data-ads=\"true\"")
								|| (inputLine.replaceAll("\\s", ""))
										.equals("<liclass=\"relativeitemeffectfade-shadowjs-shelf-itemshelf-item\"")) {
							isContent = true;
						}
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<ulclass=\"shelfjs-shelfclearclearfix\">")) {
						isTable = true;
					}
				}
				break;
			}
		}
		in.close();
	}

	private void collectURLs() throws Exception {
		String homeURL = "https://app.rimi.lv/products";
		URL url = new URL(homeURL);
		String inputLine;
		Pattern categoryIdPattern = Pattern
				.compile("(?<=data-category-id=\")(.+?)(?=\" class)");
		Matcher matcher;
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		while ((inputLine = in.readLine()) != null) {
			matcher = categoryIdPattern.matcher(inputLine);
			if (matcher.find()) {
				categoryIDs.add(matcher.group());
				// temporary output for testing
				// System.out.println(matcher.group());
			}
		}
		in.close();

		for (String string : categoryIDs) {
			url = new URL(homeURL + "/" + string);
			Pattern subCategoryPattern = Pattern
					.compile("(?<=data-category-id=\")(.+?)(?=\" class)");
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((inputLine = in.readLine()) != null) {
				matcher = subCategoryPattern.matcher(inputLine);
				if (matcher.find()) {
					urlList.add(homeURL + "/" + matcher.group());
					// temporary output for testing
					// System.out.println(matcher.group());
				}
			}
			in.close();
		}
	}

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }
}
