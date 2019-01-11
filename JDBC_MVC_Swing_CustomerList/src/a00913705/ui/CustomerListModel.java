/**
 * Project: A00913705Assign2_V3
 * File: CustomerListModel.java
 */

package a00913705.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

import a00913705.data.Sorters.CompareByJoinedDate;
import a00913705.data.Sorters.CompareByLastName;
import a00913705.data.customer.Customer;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class CustomerListModel extends AbstractListModel<CustomerListItem> {

	public static final DateFormat birthDateFormat = new SimpleDateFormat("EEE yyyy MMM dd");

	private List<CustomerListItem> customerItems;

	public CustomerListModel() {
		customerItems = new ArrayList<>();
	}

	public void setCustomers(List<Customer> customers) {
		for (Customer customer : customers) {
			customerItems.add(new CustomerListItem(customer));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return customerItems == null ? 0 : customerItems.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public CustomerListItem getElementAt(int index) {
		return customerItems.get(index);
	}

	/**
	 * Add an element to the list. Modify the behaviour of DefaultListModel.addElement to change the text to 'pig-latin'
	 * 
	 * @param element
	 *            element to be added
	 */
	public void add(Customer customer) {
		add(-1, customer);
	}

	/**
	 * @param customerForSqlServer
	 * @param index
	 */
	public void add(int index, Customer customer) {
		CustomerListItem item = new CustomerListItem(customer);
		if (index == -1) {
			customerItems.add(item);
			index = customerItems.size() - 1;
		} else {
			customerItems.add(index, item);
		}

		fireContentsChanged(this, index, index);
	}

	/**
	 * @param index
	 * @param customer
	 */
	public void update(int index, CustomerListItem item) {
		customerItems.set(index, item);

		fireContentsChanged(this, index, index);
	}

	public void sortByJoinedDate() {
		CompareByJoinedDate dateSorter = new CompareByJoinedDate();
		Collections.sort(customerItems, dateSorter);
		fireContentsChanged(this, 0, customerItems.size());
	}

	public void sortByLastName() {
		CompareByLastName nameSorter = new CompareByLastName();
		Collections.sort(customerItems, nameSorter);
		fireContentsChanged(this, 0, customerItems.size());
	}

	public void sortDescending() {

		Collections.reverse(customerItems);
		fireContentsChanged(this, 0, customerItems.size());
	}

	/**
	 * Removes the first (lowest-indexed) occurrence of the argument from this list.
	 *
	 * @param obj
	 *            the component to be removed
	 * @return <code>true</code> if the argument was a component of this
	 *         list; <code>false</code> otherwise
	 */
	public boolean remove(CustomerListItem item) {
		int index = customerItems.indexOf(item);
		boolean removed = customerItems.remove(item);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return removed;
	}

}
