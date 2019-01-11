/**
 * Project: A00913705Assign2_V3
 * File: DropDialog.java
 * Date: Nov 28, 2017
 * Time: 3:10:07 PM
 */
package a00913705.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.db.BookDao;
import a00913705.db.CustomerDao;
import a00913705.db.Database;
import a00913705.db.PurchaseDao;
import net.miginfocom.swing.MigLayout;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class DropDialog extends JDialog {

	private static Logger LOG = LogManager.getLogger();
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DropDialog dialog = new DropDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DropDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Confirm Drop Tables");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setToolTipText("");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		{
			JTextPane dropConfirm = new JTextPane();
			dropConfirm.setFont(new Font("Tahoma", Font.PLAIN, 14));
			dropConfirm.setEditable(false);
			dropConfirm.setText("Would you like to drop tables and exit the program?");
			contentPanel.add(dropConfirm, "cell 1 1,alignx center,aligny center");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {

						try {
							BookDao.getTheInstance().drop();
							CustomerDao.getTheInstance().drop();
							PurchaseDao.getTheInstance().drop();
						} catch (SQLException e) {
							LOG.error(e.getMessage());
						}

						Database.getTheInstance().shutdown();
						System.exit(0);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						DropDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
