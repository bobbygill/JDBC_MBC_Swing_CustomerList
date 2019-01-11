/**
 * Project: A00913705Assign2_V3
 * File: UiController.java
 */

package a00913705.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.Books2;
import a00913705.data.customer.Customer;
import a00913705.db.CustomerDao;
import a00913705.db.Database;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class UiController {

	private static final Logger LOG = LogManager.getLogger();
	private static MainFrame mainFrame;
	private static CustomerDao customerDao;

	protected UiController(MainFrame mainFrame) {
		UiController.mainFrame = mainFrame;
		customerDao = CustomerDao.getTheInstance();
	}

	public static void handle(Exception e) {
		LOG.error(e.getMessage());
		e.printStackTrace();
	}

	protected static class CustomerMenuItemHandler implements ActionListener {

		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				LOG.debug("CustomerForSqlServer menu item pressed.");
				// get a random customer
				List<Long> ids = customerDao.getCustomerIds();
				Random random = new Random();
				int index = random.nextInt(ids.size());
				LOG.debug("CustomerForSqlServer id index selected: " + index);
				Customer customer = customerDao.getCustomer(ids.get(index));
				LOG.debug("CustomerForSqlServer retreived: " + customer.getId());
				// show the dialog
				CustomerDialog dialog = new CustomerDialog(mainFrame, customer);
				dialog.setVisible(true);
			} catch (Exception e) {
				UiController.handle(e);
			}
		}
	}

	protected static class ExitMenuItemHandler implements ActionListener {

		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.debug("Exit menu item pressed.");
			exit(0);
		}
	}

	protected static class AboutMenuItemHandler implements ActionListener {

		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			LOG.debug("About menu item pressed.");
			JOptionPane.showMessageDialog(UiController.mainFrame, "Assignment 2\nBy Bobby Gill A00913705", "About Assignment 2",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	protected static class CustomerListSelectionHandler implements ListSelectionListener {
		private JList<CustomerListItem> customersList;
		private CustomerListModel customerModel;

		/**
		 * @param customersList
		 */
		public CustomerListSelectionHandler(JList<CustomerListItem> customersList) {
			this.customersList = customersList;
			// sorry about the cast!
			customerModel = (CustomerListModel) customersList.getModel();
		}

		/*
		 * (non-Javadoc)
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}

			CustomerListItem customer = customersList.getSelectedValue();
			if (customer != null) {
				LOG.debug("CustomerForSqlServer selected: " + customersList.getSelectedIndex());
				updateCustomer(customer, customersList.getSelectedIndex());
			}
		}

		protected void updateCustomer(CustomerListItem item, int index) {
			try {
				CustomerDialog dialog = new CustomerDialog(mainFrame, item.getCustomer());
				dialog.setVisible(true);
				Customer customer = dialog.getCustomer();
				if (customer != null) {
					LOG.debug("Updating CustomerForSqlServer: " + customer.getId());
					customerDao.update(customer);
					item.setCustomer(customer);
					customerModel.update(index, item);
				}

				customersList.clearSelection();
			} catch (Exception e) {
				UiController.handle(e);
			}
		}
	}

	protected static class MainFrameWindowEevntHandler extends WindowAdapter {

		/**
		 * Invoked when a window is in the process of being closed.
		 * The close operation can be overridden at this point.
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			Database.getTheInstance().shutdown();
			exit(0);
		}
	}

	public static void exit(int exitCode) {
		Instant endTime = Instant.now();
		LOG.info("End: " + endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(Books2.getStartTime(), endTime).toMillis()));
		System.exit(exitCode);
	}

}
