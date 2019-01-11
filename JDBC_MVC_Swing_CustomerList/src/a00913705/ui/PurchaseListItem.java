/**
 * Project: A00913705Assign2_V3
 * File: PurchaseListItem.java
 */

package a00913705.ui;

import a00913705.data.purchase.Purchase;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class PurchaseListItem {

	private Purchase Purchase;

	/**
	 * @param PurchaseForSqlServer
	 */
	public PurchaseListItem(Purchase Purchase) {
		this.Purchase = Purchase;
	}

	/**
	 * @return the PurchaseForSqlServer
	 */
	public Purchase getPurchase() {
		return Purchase;
	}

	/**
	 * @param PurchaseForSqlServer
	 *            the PurchaseForSqlServer to set
	 */
	public void setPurchase(Purchase PurchaseForSqlServer) {
		this.Purchase = PurchaseForSqlServer;
	}

	@Override
	public String toString() {
		if (Purchase == null) {
			return null;
		}

		// pull from a hashmap instead of Dao so there isn't lag while scrolling

		return String.format("%s %s %s %s %s %s %s %s $%s", "Purchase ID", Purchase.getId(), "Customer ID", Purchase.getCustomerId(), "Name",
				MainFrame.customers.get(Purchase.getCustomerId()).getFirstName(), MainFrame.customers.get(Purchase.getCustomerId()).getLastName(),
				"Price:", Purchase.getPrice());

		/*
		 * return String.format("Purchase ID %s Customer ID %s Last Name %s Price %s", Purchase.getId(), Purchase.getCustomerId(),
		 * Purchase.getCustomerLastName(Purchase.getCustomerId()), Purchase.getPrice());
		 */
	}

}
