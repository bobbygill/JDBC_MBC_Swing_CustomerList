/**
 * Project: A00913705Assign2_V3
 * File: BookDialog.java
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
public class BookDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	// private static final Logger LOG = LogManager.getLogger();
	// private List<Book> bookList;
	private JList<BookListItem> displayBookList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public BookDialog() {

		// createBookList();
		setTitle("List of Books");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 700, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// bookModel.sortByAuthor();
		// bookModel.sortDescending();

		if (MainFrame.chckbxbyAuthor.isSelected()) {
			MainFrame.bookModel.sortByAuthor();
			displayBookList = new JList<>(MainFrame.bookModel);
		}
		if (MainFrame.chckbxmntmByTitle.isSelected()) {
			MainFrame.bookModel.sortByTitle();
			displayBookList = new JList<>(MainFrame.bookModel);
		}

		if (MainFrame.chckbxBookDescending.isSelected()) {
			MainFrame.bookModel.sortDescending();
			displayBookList = new JList<>(MainFrame.bookModel);
		}
		displayBookList = new JList<>(MainFrame.bookModel);
		displayBookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayBookList.setCellRenderer(new EvenOddCellRenderer());
		getContentPane().add(new JScrollPane(displayBookList));

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
