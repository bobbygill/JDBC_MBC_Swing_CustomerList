/**
 * Project: A00913705Assign2_V3
 * File: CustomerDialog.java
 */

package a00913705.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import a00913705.data.customer.Customer;
import net.miginfocom.swing.MigLayout;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class CustomerDialog extends JDialog {

	public static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final JPanel contentPanel = new JPanel();
	private JTextField idField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField streetField;
	private JTextField cityField;
	private JTextField postalCodeField;
	private JTextField phoneField;
	private JTextField emailField;
	private JTextField joinedDateField;

	private Customer customer;

	/**
	 * Create the dialog.
	 */
	public CustomerDialog(JFrame frame, Customer customer) {
		super(frame, true);

		this.customer = customer;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(450, 360);
		setLocationRelativeTo(frame);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));

		JLabel lblId = new JLabel("ID");
		contentPanel.add(lblId, "cell 0 0,alignx trailing");

		idField = new JTextField();
		idField.setEnabled(false);
		idField.setEditable(false);
		contentPanel.add(idField, "cell 1 0,growx");
		idField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name");
		contentPanel.add(lblFirstName, "cell 0 1,alignx trailing");

		firstNameField = new JTextField();
		contentPanel.add(firstNameField, "cell 1 1,growx");
		firstNameField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		contentPanel.add(lblLastName, "cell 0 2,alignx trailing");

		lastNameField = new JTextField();
		contentPanel.add(lastNameField, "cell 1 2,growx");
		lastNameField.setColumns(10);

		JLabel lblStreet = new JLabel("Street");
		contentPanel.add(lblStreet, "cell 0 3,alignx trailing");

		streetField = new JTextField();
		contentPanel.add(streetField, "cell 1 3,growx");
		streetField.setColumns(10);

		JLabel lblCity = new JLabel("City");
		contentPanel.add(lblCity, "cell 0 4,alignx trailing");

		cityField = new JTextField();
		contentPanel.add(cityField, "cell 1 4,growx");
		cityField.setColumns(10);

		JLabel lblPostalCode = new JLabel("Postal Code");
		contentPanel.add(lblPostalCode, "cell 0 5,alignx trailing");

		postalCodeField = new JTextField();
		contentPanel.add(postalCodeField, "cell 1 5,growx");
		postalCodeField.setColumns(10);

		JLabel lblPhone = new JLabel("Phone");
		contentPanel.add(lblPhone, "cell 0 6,alignx trailing");

		phoneField = new JTextField();
		contentPanel.add(phoneField, "cell 1 6,growx");
		phoneField.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		contentPanel.add(lblEmail, "cell 0 7,alignx trailing");

		emailField = new JTextField();
		contentPanel.add(emailField, "cell 1 7,growx");
		emailField.setColumns(10);

		JLabel lblJoinedDate = new JLabel("Joined Date");
		contentPanel.add(lblJoinedDate, "cell 0 8,alignx trailing");

		joinedDateField = new JTextField();
		contentPanel.add(joinedDateField, "cell 1 8,growx");
		joinedDateField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateCustomer();
					CustomerDialog.this.dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerDialog.this.dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}

		setCustomer(customer);
	}

	/**
	 * @param customerForSqlServer
	 */
	public void setCustomer(Customer customer) {
		idField.setText(Long.toString(customer.getId()));
		firstNameField.setText(customer.getFirstName());
		lastNameField.setText(customer.getLastName());
		streetField.setText(customer.getStreet());
		cityField.setText(customer.getCity());
		postalCodeField.setText(customer.getPostalCode());
		phoneField.setText(customer.getPhone());
		emailField.setText(customer.getEmailAddress());
		joinedDateField.setText(customer.getJoinedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}

	protected void updateCustomer() {
		// the ID can't change
		customer.setFirstName(firstNameField.getText());
		customer.setLastName(lastNameField.getText());
		customer.setStreet(streetField.getText());
		customer.setCity(cityField.getText());
		customer.setPostalCode(postalCodeField.getText());
		customer.setPhone(phoneField.getText());
		customer.setEmailAddress(emailField.getText());
		String dateString = joinedDateField.getText(); // Tue Feb 22 1977
		LocalDate date = LocalDate.parse(dateString, SHORT_DATE_FORMAT);
		customer.setJoinedDate(date);
	}

	public Customer getCustomer() {
		return customer;
	}
}
