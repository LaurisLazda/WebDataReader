package webScraper;

import java.util.ArrayList;
import java.util.List;

import webScraper.products.RimiData;
import webScraper.products.WebProduct;
import webScraper.stores.ElviStores;
import webScraper.stores.MegoStores;
import webScraper.stores.StoreData;
import webScraper.stores.SupernettoStores;

/**
 * Class for calling MaximaData and RimiData to write WebProducts to DB. !!! Not
 * reading data from e-maxima.lv (for now)
 * Calls SupernettoStores, ElviStores, MegoStores to collect store lists from websites.
 */
public class WebData {

	public static void main(String[] args) {
		List<WebProduct> products = new ArrayList<>();
		List<StoreData> stores = new ArrayList<>();

		SupernettoStores supernettoStores = new SupernettoStores();
		stores.addAll(supernettoStores.getStores());
		ElviStores elviStores = new ElviStores();
		stores.addAll(elviStores.getStores());
		MegoStores megoStores = new MegoStores();
		stores.addAll(megoStores.getStores());
		// MaximaData maximaData = new MaximaData();
		RimiData rimiData = new RimiData();
		products = rimiData.getProducts();
	}
}
