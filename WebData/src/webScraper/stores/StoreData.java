package webScraper.stores;

/**
 * Class organizes data about stores.
 * 
 * @author Jānis Lazda
 */

public class StoreData {
	private String storeName;
	private String storeAddress;

	/**
	 * Constructor of StoreData
	 * 
	 * @param storeName
	 * @param storeAddress
	 */
	public StoreData(String storeName, String storeAddress) {
		setStoreName(storeName);
		setStoreAddress(storeAddress);
	}

	/**
	 * Store name getter
	 * 
	 * @return the name of store
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * Store name setter
	 * 
	 * @param storeName
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * Store address getter
	 * 
	 * @return the address of store
	 */
	public String getStoreAddress() {
		return storeAddress;
	}

	/**
	 * Store address setter
	 * 
	 * @param storeAddress
	 */
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	@Override
	public String toString() {
		return "[storeName=" + storeName + ", storeAddress=" + storeAddress
				+ "]";
	}
}
