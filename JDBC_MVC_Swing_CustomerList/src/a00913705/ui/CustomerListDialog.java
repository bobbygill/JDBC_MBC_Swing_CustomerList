/**
 * Project: A00913705Assign2_V3
 * File: CustomerListDialog.java
 * Date: Nov 29, 2017
 * Time: 10:34:49 AM
 */
package a00913705.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class CustomerListDialog extends JDialog {

	private JPanel contentPane;
	private JList<CustomerListItem> customersList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public CustomerListDialog() {

		// createcustomerList();
		setTitle("List of Customers");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

		contentPane = new JPanel();
		setBounds(100, 100, 700, 500);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		if (MainFrame.chckbxByJoinDate.isSelected()) {
			MainFrame.customerModel.sortByJoinedDate();
			customersList = new JList<>(MainFrame.customerModel);
		}

		if (MainFrame.chckbxmntmByLastName.isSelected()) {
			MainFrame.customerModel.sortByLastName();
			customersList = new JList<>(MainFrame.customerModel);
		}

		if (MainFrame.chckbxmntmCustomerDescending.isSelected()) {
			MainFrame.customerModel.sortDescending();
			customersList = new JList<>(MainFrame.customerModel);
		}

		customersList = new JList<>(MainFrame.customerModel);
		customersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customersList.setCellRenderer(new EvenOddCellRenderer());
		customersList.getSelectionModel().addListSelectionListener(new UiController.CustomerListSelectionHandler(customersList));
		getContentPane().add(new JScrollPane(customersList));

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
