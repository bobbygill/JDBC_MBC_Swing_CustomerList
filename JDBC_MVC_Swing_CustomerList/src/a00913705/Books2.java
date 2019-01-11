/**
 * Project: A00913705Assign2_V3
 * File: BookStore.java
 */

package a00913705;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import a00913705.db.BookDao;
import a00913705.db.CustomerDao;
import a00913705.db.Database;
import a00913705.db.PurchaseDao;
import a00913705.ui.MainFrame;

/**
 * This program connects to a database via JDBC (DAO), reads all the data (Books, Customers, Purchases)
 * and allows you to sort and edit entries by using the GUI (swing). Log4j2, Comparator, and dialogues are used.
 * 
 * @author Bobby Gill, A00913705
 *
 */
public class Books2 {

	private static final String DROP_OPTION = "-drop";
	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static Instant startTime;

	static {
		configureLogging();
	}

	private static final Logger LOG = LogManager.getLogger();

	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);

		} catch (IOException e) {
			System.out.println(String.format("Can't find the log4j logging configuration file %s.", LOG4J_CONFIG_FILENAME));
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		startTime = Instant.now();
		LOG.info("Start: " + startTime);

		if (args.length == 1 && args[0].equalsIgnoreCase(DROP_OPTION)) {
			Database.requestDbTableDrop();
		}

		Books2 bookstore = null;
		try {
			bookstore = new Books2();
			bookstore.run();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}

		LOG.debug("main thread exiting");
	}

	/**
	 * Books2 constructor. Initializes the collections
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws
	 */
	public Books2() throws FileNotFoundException, IOException, ApplicationException {

		Database.getTheInstance().init();
		CustomerDao.getTheInstance().init();
		PurchaseDao.getTheInstance().init();
		BookDao.getTheInstance().init();

	}

	private void run() {
		try {
			createUI();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}

	}

	public static void createUI() {
		LOG.debug("Creating the UI");
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LOG.debug("Setting the Look & Feel");
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error(e.getMessage());
				}
			}
		});
	}

	/**
	 * @return the startTime
	 */
	public static Instant getStartTime() {
		return startTime;
	}

}
