package readhtml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for calling MaximaData and RimiData to write WebProducts to DB. !!! Not
 * reading data from e-maxima.lv (for now)
 */
public class WebData {

	public static void main(String[] args) {
		List<WebProduct> products = new ArrayList<>();
		List<StoreData> stores = new ArrayList<>();

		// MaximaData maximaData = new MaximaData();
		RimiData rimiData = new RimiData();
		products = rimiData.getProducts();
		SupernettoStores supernettoStores = new SupernettoStores();
		stores = supernettoStores.getStores();
	}
}
