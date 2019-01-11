/**
 * Project: A00913705Assign2_V3
 * File: PurchaseListModel.java
 */

package a00913705.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import a00913705.data.purchase.Purchase;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class PurchaseListModel extends AbstractListModel<PurchaseListItem> {

	private List<PurchaseListItem> PurchaseItems;
	// private String customerLastName;

	public PurchaseListModel() {
		PurchaseItems = new ArrayList<>();
	}

	public void setPurchases(List<Purchase> Purchases) {
		for (Purchase Purchase : Purchases) {
			PurchaseItems.add(new PurchaseListItem(Purchase));
		}
	}

	public void sortDescending() {

		Collections.reverse(PurchaseItems);
		fireContentsChanged(this, 0, PurchaseItems.size());
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return PurchaseItems == null ? 0 : PurchaseItems.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public PurchaseListItem getElementAt(int index) {
		return PurchaseItems.get(index);
	}

	/**
	 * Add an element to the list. Modify the behaviour of DefaultListModel.addElement to change the text to 'pig-latin'
	 * 
	 * @param element
	 *            element to be added
	 */
	public void add(Purchase Purchase) {
		add(-1, Purchase);
	}

	/**
	 * @param BookForSqlServer
	 * @param index
	 */
	public void add(int index, Purchase Purchase) {
		PurchaseListItem item = new PurchaseListItem(Purchase);
		if (index == -1) {
			PurchaseItems.add(item);
			index = PurchaseItems.size() - 1;
		} else {
			PurchaseItems.add(index, item);
		}

		fireContentsChanged(this, index, index);
	}

	/**
	 * @param index
	 * @param Book
	 */
	public void update(int index, PurchaseListItem item) {
		PurchaseItems.set(index, item);

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
	public boolean remove(PurchaseListItem item) {
		int index = PurchaseItems.indexOf(item);
		boolean removed = PurchaseItems.remove(item);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return removed;
	}

}
