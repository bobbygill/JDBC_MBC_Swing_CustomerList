/**
 * Project: A00913705Assign2_V3
 * File: Purchase.java
 * Date: Nov 26, 2017
 * Time: 4:16:59 PM
 */
package a00913705.data.purchase;

import a00913705.db.BookDao;
import a00913705.db.CustomerDao;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class Purchase {
	public static final int ATTRIBUTE_COUNT = 4;

	private long id;
	private long customerId;
	private long bookId;
	private float price;
	private String lastName;
	private String bookTitle;

	public static class Builder {
		// Required parameters
		private long id;
		private long customerId;
		private long bookId;
		private float price;

		public Builder(long id, long customerId, long bookId, float price) {
			this.id = id;
			this.customerId = customerId;
			this.bookId = bookId;
			this.price = price;
		}

		public Purchase build() {
			return new Purchase(this);
		}

		public Builder setId(Long id) {
			this.id = id;

			return this;
		}

		public Builder setCustomerId(Long customerId) {
			this.customerId = customerId;

			return this;
		}

		public Builder setbookId(Long bookId) {
			this.bookId = bookId;

			return this;
		}

		public Builder setPrice(float price) {
			this.price = price;

			return this;
		}
	}

	/**
	 * @param builder
	 */
	public Purchase(Builder builder) {
		this.id = builder.id;
		this.customerId = builder.customerId;
		this.bookId = builder.bookId;
		this.price = builder.price;
	}

	/**
	 * @return the attributeCount
	 */
	public static int getAttributeCount() {
		return ATTRIBUTE_COUNT;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}

	public String getCustomerLastName(long customerId) {
		CustomerDao customerDao = CustomerDao.getTheInstance();
		try {
			lastName = customerDao.getCustomer(customerId).getLastName();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return lastName;
	}

	public String getBookTitle(long bookId) {
		BookDao bookDao = BookDao.getTheInstance();
		try {
			bookTitle = bookDao.getBook(bookId).getTitle();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return bookTitle;
	}

	/**
	 * @return the bookid
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Purchase [customerId=" + customerId + ", bookId=" + bookId + ", price=" + price + "]";
	}

}
