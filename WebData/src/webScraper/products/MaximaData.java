package webScraper.products;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// !!! Not uesed (FOR NOW)!!!
/**
 * Class for collecting product info from e-maxima.lv.
 * 
 * @author Lauris Lazda
 * @author Jānis Lazda
 */
public class MaximaData {
	// (?) add boolean field to identify discounts
	String homeURL = "https://www.e-maxima.lv";
	private BufferedReader in;

	private List<String> urlCategorys;
	private List<String> urlList;
	private List<String> urlSecondaryList;
	private List<String> products;
	private List<Double> prices;

	/**
	 * Constructor of MaximaData. Inicializes Lists and HashMaps and calls
	 * collectURLs() and collectData()
	 */
	public MaximaData() {
		urlCategorys = new ArrayList<>();
		urlList = new ArrayList<>();
		urlSecondaryList = new ArrayList<>();
		products = new ArrayList<>();
		prices = new ArrayList<>();

		try {
			collectURLs();
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method which reads page count for current category, checks if category
	 * has subcategories if yes, populates urlSecondaryList with URLs. Iterates
	 * through urlList and urlSecondaryList and calls readPage() passing current
	 * url
	 */
	private void collectData() throws Exception {
		URL url;
		String inputLine;
		Matcher matcher;
		Pattern IdPattern = Pattern.compile("id=\"ctl00_MainContent_pager\"");
		Pattern countPattern = Pattern.compile("(?<=1. lapa no )(\\d+?)");

		for (String string : urlList) {
			url = new URL(homeURL + string + ".aspx");
			int pagesCount = 1;
			boolean hasCategories = true;

			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF8"));
			while ((inputLine = in.readLine()) != null) {
				matcher = IdPattern.matcher(inputLine);
				if (matcher.find()) {
					matcher = countPattern.matcher(inputLine);
					matcher.find();
					pagesCount = Integer.parseInt(matcher.group());
					// temporary output for testing
					System.out.println(string + ": " + matcher.group());
					hasCategories = false;
					break;
				}
			}
			in.close();

			if (hasCategories) {
				Pattern urlPattern = Pattern.compile("(?<=(<a href=\"))("
						+ string + "/.+?)(?=(\\.aspx\">.+?</a>))");

				in = new BufferedReader(new InputStreamReader(url.openStream(),
						"UTF8"));
				while ((inputLine = in.readLine()) != null) {
					// find a start and finish for search
					// if (finish) {
					// break;
					// }
					matcher = urlPattern.matcher(inputLine);
					if (matcher.find()) {
						// (?) add product sub-subcategory names into WebProduct
						// fields
						urlSecondaryList.add(matcher.group());
					}
				}
				in.close();
			} else {

				if (pagesCount != 1) {
					for (int i = 2; i <= pagesCount; i++) {
						// temporary output for testing
						System.out.println(string + "/" + i);
						readPage(new URL(homeURL + string + "/" + i + ".aspx"));
					}
				} else {
					// temporary output for testing
					System.out.println(string);
					readPage(url);
				}
			}
		}

		// collects data from pages found in sub-subcategories
		for (String string : urlSecondaryList) {
			url = new URL(homeURL + string + ".aspx");
			int pagesCount = 1;

			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF8"));
			while ((inputLine = in.readLine()) != null) {
				matcher = IdPattern.matcher(inputLine);
				if (matcher.find()) {
					matcher = countPattern.matcher(inputLine);
					matcher.find();
					pagesCount = Integer.parseInt(matcher.group());
					// temporary output for testing
					System.out.println(string + ": " + matcher.group());
					break;
				}
			}
			in.close();

			if (pagesCount != 1) {
				for (int i = 2; i <= pagesCount; i++) {
					// temporary output for testing
					System.out.println(string + "/" + i);
					readPage(new URL(homeURL + string + "/" + i + ".aspx"));
				}
			} else {
				// temporary output for testing
				System.out.println(string);
				readPage(url);
			}
		}
	}

	/**
	 * Method which reads passed webpages URL and collects products from each
	 * passed webpage storing them in webProduct object
	 * 
	 * @param url
	 */
	private void readPage(URL url) throws Exception {
		String eur = "\\u20ac";
		char euro = (char) Integer.parseInt(eur.substring(2), 16);
		String inputLine;
		Matcher matcher;
		Pattern namePattern = Pattern.compile("(?<=>)(.+?)(?=</a>)");
		Pattern pricePattern = Pattern
				.compile("(?<=<strong>)(\\d{1,3})(,)(\\d{2})(?= " + euro
						+ "</strong>)");

		in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			boolean isTable = false;
			boolean isContent = false;

			// TODO: content of some pages is't read correctly. Should fix it
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<divid=\"main-content\">")) {
				while ((inputLine = in.readLine()) != null) {
					if (isContent) {
						if ((inputLine.replaceAll("\\s", ""))
								.equals("</tbody></table>")) {
							break;
						}
						if ((inputLine.replaceAll("\\s", "")).equals("<h3>")) {
							inputLine = in.readLine();
							matcher = namePattern.matcher(inputLine);
							matcher.find();
							products.add(matcher.group());
							// temporary output for testing
							System.out.print(matcher.group() + " = ");
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<pclass=\"guide\">")) {
							inputLine = in.readLine();
							matcher = pricePattern.matcher(inputLine);
							matcher.find();
							prices.add(Double.parseDouble((matcher.group())
									.replaceAll(",", ".")));
							// temporary output for testing
							System.out.println(matcher.group());
						}
					}
					if (isTable) {
						if ((inputLine.replaceAll("\\s", "")).equals("<tr>")) {
							isContent = true;
						}
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<tableclass='datatype1'>")) {
						isTable = true;
					}
				}
				break;
			}
		}
		in.close();
		// temporary output for testing
		System.out.println(products.size());
	}

	/**
	 * Method which collects category names and stores them in urlCategories
	 * list then going through the list populates the urlList with category URLs
	 */
	private void collectURLs() throws Exception {
		String indexPage = homeURL + "/Produkti/partika_dzerieni.aspx";
		URL url = new URL(indexPage);
		String inputLine;
		Matcher matcher;
		Pattern urlPattern = Pattern
				.compile("(?<=(<a href=\"))(/Produkti/partika_dzerieni/[^/]+?)(?=(\\.aspx\">.+?</a>))");

		in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF8"));
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
				while ((inputLine = in.readLine()) != null) {
					// (?) add product category names into WebProduct fields
					if ((inputLine.replaceAll("\\s", ""))
							.equals("</ul></span>")) {
						break;
					}

					matcher = urlPattern.matcher(inputLine);
					if (matcher.find()) {
						urlCategorys.add(matcher.group());
						// temporary output for testing
						System.out.println(matcher.group());
					}
				}
				break;
			}
		}
		in.close();

		for (String category : urlCategorys) {
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF8"));
			String pattern = "(?<=(<a href=\"))(" + category
					+ "/.+?)(?=(\\.aspx\">.+?</a>))";
			urlPattern = Pattern.compile(pattern);
			while ((inputLine = in.readLine()) != null) {
				if ((inputLine.replaceAll("\\s", ""))
						.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
					while ((inputLine = in.readLine()) != null) {
						// (?) add product subcategory names into WebProduct
						// fields
						if ((inputLine.replaceAll("\\s", ""))
								.equals("</ul></span>")) {
							break;
						}

						matcher = urlPattern.matcher(inputLine);
						if (matcher.find()) {
							if (!(matcher.group().contains("Favorites"))) {
								urlList.add(matcher.group());
								// temporary output for testing
								System.out.println(matcher.group());
							}
						}
					}
					break;
				}
			}
			in.close();
			urlList.add("/Produkti/partika_dzerieni/sporta_uzturs");
		}
	}

	// TODO: Method that returns collected data
	// private WebProduct getData () { }
}
