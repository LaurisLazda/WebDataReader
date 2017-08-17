package readhtml;

/**
 * Class to organize data collected from e-maxima.lv and app.rimi.lv. !!! Not
 * collecting data from e-maxima.lv (for now)
 */
public class WebProduct {
	private String category;
	private String product;
	private double price;
	private long barcode;

	public WebProduct(String category, String product, double price,
			long barcode) {
		setCategory(category);
		setProduct(product);
		setPrice(price);
		setBarcode(barcode);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String name) {
		this.product = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	@Override
	public String toString() {
		return "[category=" + category + ", product=" + product
				+ ", price=" + price + ", barcode=" + barcode + "]";
	}
}
