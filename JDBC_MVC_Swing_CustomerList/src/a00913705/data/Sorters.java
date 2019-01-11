/**
 * Project: A00913705Assign2_V3
 * File: CompareByJoinedDate.java
 */

package a00913705.data;

import java.util.Comparator;

import a00913705.ui.BookListItem;
import a00913705.ui.CustomerListItem;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class Sorters {

	public static class CompareByJoinedDate implements Comparator<CustomerListItem> {
		@Override
		public int compare(CustomerListItem customer1, CustomerListItem customer2) {
			return customer1.getCustomer().getJoinedDate().compareTo(customer2.getCustomer().getJoinedDate());
		}
	}

	public static class CompareByAuthor implements Comparator<BookListItem> {
		@Override
		public int compare(BookListItem book1, BookListItem book2) {
			return book1.getBook().getAuthors().compareTo(book2.getBook().getAuthors());
		}
	}

	public static class CompareByTitle implements Comparator<BookListItem> {
		@Override
		public int compare(BookListItem book1, BookListItem book2) {
			return book1.getBook().getTitle().compareTo(book2.getBook().getTitle());
		}
	}

	public static class CompareByLastName implements Comparator<CustomerListItem> {
		@Override
		public int compare(CustomerListItem customer1, CustomerListItem customer2) {
			return customer1.getCustomer().getLastName().compareTo(customer2.getCustomer().getLastName());
		}
	}

}
