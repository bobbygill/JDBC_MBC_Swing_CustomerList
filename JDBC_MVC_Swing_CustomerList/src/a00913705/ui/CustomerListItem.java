/**
 * Project: A00913705Assign2_V3
 * File: CustomerListItem.java
 */

package a00913705.ui;

import a00913705.data.customer.Customer;
import a00913705.data.util.Common;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class CustomerListItem {

	private Customer customer;

	/**
	 * @param customerForSqlServer
	 */
	public CustomerListItem(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return the customerForSqlServer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customerForSqlServer
	 *            the customerForSqlServer to set
	 */
	public void setCustomer(Customer customerForSqlServer) {
		this.customer = customerForSqlServer;
	}

	@Override
	public String toString() {
		if (customer == null) {
			return null;
		}

		String joinedDate = "";
		if (customer.getJoinedDate() != null) {
			joinedDate = customer.getJoinedDate().format(Common.DATE_FORMAT);
		}

		return String.format("%d %s %s %s %s", customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmailAddress(),
				joinedDate);
	}
}
