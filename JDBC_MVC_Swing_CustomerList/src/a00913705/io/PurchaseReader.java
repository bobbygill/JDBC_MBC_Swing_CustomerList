/**
 * Project: A00913705Assign2_V3
 * File: PurchaseReader.java
 * Date: Nov 26, 2017
 * Time: 4:19:04 PM
 */
package a00913705.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.ApplicationException;
import a00913705.data.purchase.Purchase;
import a00913705.db.PurchaseDao;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class PurchaseReader {

	public static final String FILENAME = "purchases.csv";

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * private constructor to prevent instantiation
	 */
	private PurchaseReader() {
	}

	/**
	 * Read the inventory input data.
	 * 
	 * @return the inventory.
	 * @throws ApplicationException
	 */

	public static int read(File purchaseDataFile, PurchaseDao dao) throws ApplicationException {
		File file = new File(FILENAME);
		int purchaseCount = 0;
		FileReader in;
		Iterable<CSVRecord> records;
		try {
			in = new FileReader(file);
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}

		// Map<Long, Book> books = new HashMap<>();

		LOG.debug("Reading" + file.getAbsolutePath());
		for (CSVRecord record : records) {
			String Id = record.get("id");
			String customerId = record.get("customer_id");
			String bookId = record.get("book_id");
			String price = record.get("price");

			Purchase purchase = new Purchase.Builder(Long.parseLong(Id), Long.parseLong(customerId), Long.parseLong(bookId), Float.parseFloat(price))
					.build();

			try {
				// books.put(book.getId(), book);
				dao.add(purchase);
				purchaseCount++;
			} catch (SQLException e) {
				throw new ApplicationException(e);
			}

			LOG.debug("Added " + purchase.toString() + " as " + Id);
		}

		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new ApplicationException(e);
			}
		}

		return purchaseCount;
	}

}
