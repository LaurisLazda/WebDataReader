package webScraper.products;

/**
 * Class to organize data collected from e-maxima.lv and app.rimi.lv. !!! NOT
 * collecting data from e-maxima.lv (for now)
 * 
 * @author Lauris Lazda
 */
public class WebProduct {
	private String category;
	private String product;
	private double price;
	private long barcode;

	/**
	 * Constructor for WebProduct
	 * 
	 * @param category
	 * @param product
	 * @param price
	 * @param barcode
	 */
	public WebProduct(String category, String product, double price,
			long barcode) {
		setCategory(category);
		setProduct(product);
		setPrice(price);
		setBarcode(barcode);
	}

	/**
	 * Category getter
	 * 
	 * @return category name
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Category setter
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Product name getter
	 * 
	 * @return product name
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * Product name setter
	 * 
	 * @param name
	 */
	public void setProduct(String name) {
		this.product = name;
	}

	/**
	 * Price getter
	 * 
	 * @return prduct price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Price setter
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Product barcode getter
	 * 
	 * @return product barcode
	 */
	public long getBarcode() {
		return barcode;
	}

	/**
	 * Product barcode setter
	 * 
	 * @param barcode
	 */
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	@Override
	public String toString() {
		return "[category=" + category + ", product=" + product + ", price="
				+ price + ", barcode=" + barcode + "]";
	}
}
