/**
 * Project: A00913705Assign2_V3
 * File: CustomerDaoTester.java
 */

package a00913705;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.data.book.Book;
import a00913705.data.customer.Customer;
import a00913705.data.purchase.Purchase;
import a00913705.db.BookDao;
import a00913705.db.CustomerDao;
import a00913705.db.PurchaseDao;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class DaoTester {

	private static Logger LOG = LogManager.getLogger();
	private CustomerDao customerDao;
	private BookDao bookDao;
	private PurchaseDao purchaseDao;

	public DaoTester(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public DaoTester(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public DaoTester(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public void testBooks() {
		try {
			LOG.info("Getting the book IDs");
			List<Long> ids = bookDao.getBookIds();
			LOG.info("BookForSqlServer IDs: " + Arrays.toString(ids.toArray()));
			for (Long id : ids) {
				LOG.info(id);
				Book book = bookDao.getBook(id);
				LOG.info(book);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public void testCustomers() {
		try {
			LOG.info("Getting the book IDs");
			List<Long> ids = customerDao.getCustomerIds();
			LOG.info("CustomerForSqlServer IDs: " + Arrays.toString(ids.toArray()));
			for (Long id : ids) {
				LOG.info(id);
				Customer customer = customerDao.getCustomer(id);
				LOG.info(customer);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public void testPurchases() {
		try {
			LOG.info("Getting the purchase IDs");
			List<Long> ids = purchaseDao.getPurchaseIds();
			LOG.info("PurchaseForSqlServer IDs: " + Arrays.toString(ids.toArray()));
			for (Long id : ids) {
				LOG.info(id);
				Purchase purchase = purchaseDao.getPurchase(id);
				LOG.info(purchase);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

}
