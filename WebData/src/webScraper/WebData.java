package webScraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

	private static Connection conn;
	private static String user = "user";
	private static String pass = "parolee";
	private static String url = "jdbc:mysql://zesloka.tk/?autoReconnect=true&useSSL=false";
	
	public static void main(String[] args) throws ClassNotFoundException {
		List<WebProduct> products = new ArrayList<>();
		List<StoreData> stores = new ArrayList<>();
		
		// https://stackoverflow.com/questions/17484764/java-lang-classnotfoundexception-com-mysql-jdbc-driver-in-eclipse
		Class.forName("com.mysql.jdbc.Driver");

		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//SupernettoStores supernettoStores = new SupernettoStores();
		//stores.addAll(supernettoStores.getStores());
		//ElviStores elviStores = new ElviStores();
		//stores.addAll(elviStores.getStores());
		MegoStores megoStores = new MegoStores();
		stores.addAll(megoStores.getStores());
		// MaximaData maximaData = new MaximaData();
		//RimiData rimiData = new RimiData();
		//products = rimiData.getProducts();
		
		try {
			for (StoreData sd : stores) {
				Statement stm = conn.createStatement();
				stm.executeUpdate("UPDATE piens_un_maize.store SET s_name='" + sd.getStoreName() 
						+ "', s_location='" + sd.getStoreAddress() + "' WHERE id=14");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
