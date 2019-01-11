/**
 * Project: A00913705Assign2_V3
 * File: BookListModel.java
 */

package a00913705.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import a00913705.data.Sorters.CompareByAuthor;
import a00913705.data.Sorters.CompareByTitle;
import a00913705.data.book.Book;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class BookListModel extends AbstractListModel<BookListItem> {

	private List<BookListItem> BookItems;

	public BookListModel() {
		BookItems = new ArrayList<>();
	}

	public void setBooks(List<Book> Books) {
		for (Book Book : Books) {
			BookItems.add(new BookListItem(Book));
		}
	}

	public void sortByAuthor() {
		CompareByAuthor bookAuthorSorter = new CompareByAuthor();
		Collections.sort(BookItems, bookAuthorSorter);
		fireContentsChanged(this, 0, BookItems.size());
	}

	public void sortDescending() {
		Collections.reverse(BookItems);
		fireContentsChanged(this, 0, BookItems.size());
	}

	public void sortByTitle() {
		CompareByTitle bookTitleSorter = new CompareByTitle();
		Collections.sort(BookItems, bookTitleSorter);
		fireContentsChanged(this, 0, BookItems.size());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return BookItems == null ? 0 : BookItems.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public BookListItem getElementAt(int index) {
		return BookItems.get(index);
	}

	/**
	 * Add an element to the list. Modify the behaviour of DefaultListModel.addElement to change the text to 'pig-latin'
	 * 
	 * @param element
	 *            element to be added
	 */
	public void add(Book Book) {
		add(-1, Book);
	}

	/**
	 * @param BookForSqlServer
	 * @param index
	 */
	public void add(int index, Book Book) {
		BookListItem item = new BookListItem(Book);
		if (index == -1) {
			BookItems.add(item);
			index = BookItems.size() - 1;
		} else {
			BookItems.add(index, item);
		}

		fireContentsChanged(this, index, index);
	}

	/**
	 * @param index
	 * @param Book
	 */
	public void update(int index, BookListItem item) {
		BookItems.set(index, item);

		fireContentsChanged(this, index, index);
	}

	/**
	 * Removes the first (lowest-indexed) occurrence of the argument from this list.
	 *
	 * @param obj
	 *            the component to be removed
	 * @return <code>true</code> if the argument was a component of this
	 *         list; <code>false</code> otherwise
	 */
	public boolean remove(BookListItem item) {
		int index = BookItems.indexOf(item);
		boolean removed = BookItems.remove(item);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return removed;
	}

}
