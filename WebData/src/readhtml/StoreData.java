package readhtml;

/**
 * Class organizes data about Stores.
 */

public class StoreData {
	private String storeName;
	private String storeAddress;

	public StoreData(String storeName, String storeAddress) {
		setStoreName(storeName);
		setStoreAddress(storeAddress);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	@Override
	public String toString() {
		return "[storeName=" + storeName + ", storeAddress=" + storeAddress
				+ "]";
	}
}
