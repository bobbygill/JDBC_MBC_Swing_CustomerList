/**
 * Project: A00913705Assign2_V3
 * File: BookListItem.java
 */

package a00913705.ui;

import a00913705.data.book.Book;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class BookListItem {

	private Book Book;

	/**
	 * @param BookForSqlServer
	 */
	public BookListItem(Book Book) {
		this.Book = Book;
	}

	/**
	 * @return the BookForSqlServer
	 */
	public Book getBook() {
		return Book;
	}

	/**
	 * @param BookForSqlServer
	 *            the BookForSqlServer to set
	 */
	public void setBook(Book BookForSqlServer) {
		this.Book = BookForSqlServer;
	}

	@Override
	public String toString() {
		if (Book == null) {
			return null;
		}

		return String.format("%d %s %s %s", Book.getId(), Book.getAuthors(), Book.getTitle(), Book.getYear());
	}

}
