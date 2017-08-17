package readhtml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for calling MaximaData and RimiData to write WebProducts to DB. !!! Not
 * reading data from e-maxima.lv (for now)
 */
public class WebData {
	private List<WebProduct> products;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		WebData data = new WebData(); 
	}
	
	public WebData(){
		products = new ArrayList<>();
		
		// MaximaData maximaData = new MaximaData();
		RimiData rimiData = new RimiData();
		// TODO: call getData of RimiData
		// TODO: write data to DB
	}

}
