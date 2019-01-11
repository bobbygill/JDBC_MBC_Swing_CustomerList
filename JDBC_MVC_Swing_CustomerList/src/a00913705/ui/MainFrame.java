/**
 * Project: A00913705Assign2_V3
 * File: MainFrame.java
 */

package a00913705.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.data.book.Book;
import a00913705.data.customer.Customer;
import a00913705.data.purchase.Purchase;
import a00913705.db.BookDao;
import a00913705.db.CustomerDao;
import a00913705.db.PurchaseDao;

/**
 * @author Bobby Gill, A00913705
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static final Logger LOG = LogManager.getLogger();

	// private List<Customer> customerList = new ArrayList<>();
	// private List<Book> bookList = new ArrayList<>();

	public static BookListModel bookModel;
	public static CustomerListModel customerModel;
	public static PurchaseListModel purchaseModel;
	// private JPanel contentPane;
	// private JList<CustomerListItem> customersList;
	// private CustomerListModel customerModel;
	private JMenuItem mntmCustomer;
	private JMenuItem mntmQuit;
	private JMenuItem mntmAbout;
	private JMenuItem mntmDrop;
	private JMenu mnBooks;
	private JMenuItem mntmBookCount;
	public static JCheckBoxMenuItem chckbxbyAuthor;
	public static JCheckBoxMenuItem chckbxBookDescending;
	private JMenuItem mntmBookList;
	private int bookCount;
	private int customerCount;
	private int purchaseCount;
	private JMenu mnCustomers;
	private JMenuItem mntmCustCount;
	public static JCheckBoxMenuItem chckbxByJoinDate;
	private JMenuItem mntmCustList;
	private JMenu mnPurchases;
	private JMenuItem mntmTotal;
	public static JCheckBoxMenuItem chckbxmntmByLastName;
	private JMenuItem mntmList;
	public static JCheckBoxMenuItem chckbxmntmByTitle;
	public static JCheckBoxMenuItem chckbxmntmPurchaseDescending;
	public static JCheckBoxMenuItem chckbxmntmCustomerDescending;
	public static Map<Long, Customer> customers;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		LOG.debug("Creating the MainFrame");

		setCustomerData();

		setBookData();

		setPurchaseData();

		createUi();

		addEventHandlers();

	}

	/**
	 * 
	 */
	private void createUi() {
		LOG.debug("createUi");
		setTitle("Books2");

		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmDrop = new JMenuItem("Drop");
		mntmDrop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DropDialog dropDialog = new DropDialog();
				dropDialog.setVisible(true);
			}
		});
		mnFile.add(mntmDrop);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mnFile.add(mntmQuit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmAbout = new JMenuItem("About");
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnHelp.add(mntmAbout);

		mnBooks = new JMenu("Books");
		menuBar.add(mnBooks);

		mntmBookCount = new JMenuItem("Count");
		mntmBookCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "The book count is " + bookCount, "Book Count", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnBooks.add(mntmBookCount);

		chckbxbyAuthor = new JCheckBoxMenuItem("By Author");

		mnBooks.add(chckbxbyAuthor);

		chckbxBookDescending = new JCheckBoxMenuItem("Descending");
		if (chckbxBookDescending.isSelected()) {
			bookModel.sortDescending();
		}

		chckbxmntmByTitle = new JCheckBoxMenuItem("By Title");
		mnBooks.add(chckbxmntmByTitle);
		mnBooks.add(chckbxBookDescending);

		mntmBookList = new JMenuItem("List");
		mntmBookList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookDialog bookDialog = new BookDialog();
				bookDialog.setVisible(true);

			}
		});
		mnBooks.add(mntmBookList);

		mnCustomers = new JMenu("Customers");
		menuBar.add(mnCustomers);

		mntmCustomer = new JMenuItem("Random Customer");
		mntmCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnCustomers.add(mntmCustomer);

		mntmCustCount = new JMenuItem("Count");
		mntmCustCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "The customer count is " + customerCount, "Customer Count",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnCustomers.add(mntmCustCount);

		chckbxByJoinDate = new JCheckBoxMenuItem("By Join Date");
		mnCustomers.add(chckbxByJoinDate);

		chckbxmntmByLastName = new JCheckBoxMenuItem("By Last Name");
		mnCustomers.add(chckbxmntmByLastName);

		mntmCustList = new JMenuItem("List");
		mntmCustList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				CustomerListDialog customerDialog = new CustomerListDialog();

			}
		});

		chckbxmntmCustomerDescending = new JCheckBoxMenuItem("Descending");
		mnCustomers.add(chckbxmntmCustomerDescending);
		mnCustomers.add(mntmCustList);

		mnPurchases = new JMenu("Purchases");
		menuBar.add(mnPurchases);

		mntmTotal = new JMenuItem("Total");
		mntmTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "The purchase count is " + purchaseCount, "Purchase Count",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnPurchases.add(mntmTotal);

		mntmList = new JMenuItem("List");
		mntmList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				PurchaseDialog purchaseDialog = new PurchaseDialog();

			}
		});

		chckbxmntmPurchaseDescending = new JCheckBoxMenuItem("Descending");
		mnPurchases.add(chckbxmntmPurchaseDescending);
		mnPurchases.add(mntmList);

	}

	/**
	 * 
	 */
	private void addEventHandlers() {
		LOG.debug("addEventHandlers");

		new UiController(this);

		addWindowListener(new UiController.MainFrameWindowEevntHandler());

		mntmCustomer.addActionListener(new UiController.CustomerMenuItemHandler());
		mntmQuit.addActionListener(new UiController.ExitMenuItemHandler());
		mntmAbout.addActionListener(new UiController.AboutMenuItemHandler());
		// customersList.getSelectionModel().addListSelectionListener(new UiController.CustomerListSelectionHandler(customersList));

	}

	/**
	 * set the data
	 */
	private void setCustomerData() {

		LOG.debug("setCustomerData");
		customerModel = new CustomerListModel();

		try {
			customers = new HashMap<Long, Customer>();
			CustomerDao customerDao = CustomerDao.getTheInstance();
			List<Long> ids = customerDao.getCustomerIds();
			for (long id : ids) {
				Customer customer = customerDao.getCustomer(id);

				customerModel.add(customer);
				customers.put(customer.getId(), customer);
				customerCount++;

			}
		} catch (Exception e) {
			UiController.handle(e);
		}
	}

	public void setBookData() {
		LOG.debug("setBookData");
		bookModel = new BookListModel();
		// bookCount = 0;

		try {
			BookDao bookDao = BookDao.getTheInstance();
			List<Long> ids = bookDao.getBookIds();
			// bookList = new ArrayList<Book>();

			for (long id : ids) {
				Book book = bookDao.getBook(id);
				// bookList.add(book);
				bookModel.add(book);
				bookCount++;
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
	}

	public void setPurchaseData() {
		LOG.debug("setPurchaseData");
		purchaseModel = new PurchaseListModel();

		try {
			PurchaseDao purchaseDao = PurchaseDao.getTheInstance();
			List<Long> ids = purchaseDao.getPurchaseIds();

			for (long id : ids) {
				Purchase purchase = purchaseDao.getPurchase(id);

				purchaseModel.add(purchase);
				purchaseCount++;
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
	}

}

@SuppressWarnings("serial")
class EvenOddCellRenderer extends JLabel implements ListCellRenderer<Object> {

	private Color evenColor;
	private Color oddColor;

	EvenOddCellRenderer() {
		setOpaque(true);

		evenColor = new Color(255, 255, 255);
		oddColor = new Color(220, 220, 250);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// Assumes the stuff in the list has a pretty toString
		setText(value.toString());

		// based on the index you set the color. This produces the every other effect.
		if (index % 2 == 0) {
			setBackground(evenColor);
		} else {
			setBackground(oddColor);
		}

		return this;
	}

}
