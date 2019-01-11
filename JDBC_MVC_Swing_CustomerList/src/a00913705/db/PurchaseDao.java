/**
 * Project: A00913705Assign2_V3
 * File: PurchaseDao.java
 * Date: Nov 26, 2017
 * Time: 5:36:51 PM
 */
package a00913705.db;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.ApplicationException;
import a00913705.data.purchase.Purchase;
import a00913705.io.PurchaseReader;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class PurchaseDao extends Dao {

	public static final String TABLE_NAME = DbConstants.TABLE_ROOT + "Purchases";

	private static final String PURCHASE_DATA_FILENAME = "purchases.csv";
	private static Logger LOG = LogManager.getLogger();
	private static PurchaseDao theInstance = new PurchaseDao();
	private static Database database;

	private PurchaseDao() {
		super(TABLE_NAME);

		database = Database.getTheInstance();
	}

	public static PurchaseDao getTheInstance() {
		return theInstance;
	}

	/**
	 * @param purchaseDataFile
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void init() throws ApplicationException {
		File purchaseDataFile = new File(PURCHASE_DATA_FILENAME);
		try {
			if (!Database.tableExists(PurchaseDao.TABLE_NAME) || Database.dbTableDropRequested()) {
				if (Database.tableExists(PurchaseDao.TABLE_NAME) && Database.dbTableDropRequested()) {
					drop();
				}

				create();

				LOG.debug("Inserting the purchases");

				if (!purchaseDataFile.exists()) {
					throw new ApplicationException(String.format("Required '%s' is missing.", PURCHASE_DATA_FILENAME));
				}

				PurchaseReader.read(purchaseDataFile, this);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}

	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		// With Derby, the JOINED_DATE was a TIMESTAMP data type. With MS SQL Server, it needs to be changed to DATETIME.
		String sqlString = String.format(
				"CREATE TABLE %s(" //
						+ "%s BIGINT, " // ID
						+ "%s VARCHAR(%d), " // CUSTOMERID
						+ "%s VARCHAR(%d), " // BOOKID
						+ "%s FLOAT(3))", // PRICE
				// + (Database.isMsSqlServer() ? "%s DATETIME, " : "%s TIMESTAMP, ") // JOINED_DATE
				// + "PRIMARY KEY (%s))", // ID
				TABLE_NAME, //
				Column.ID.name, //
				Column.CUSTOMER_ID.name, Column.CUSTOMER_ID.length, //
				Column.BOOK_ID.name, Column.BOOK_ID.length, //
				Column.PRICE.name); //
		// Column.JOINED_DATE.name, //
		// Column.ID.name);

		super.create(sqlString);
	}

	public void add(Purchase purchase) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(?, ?, ?, ?)", TABLE_NAME);
		@SuppressWarnings("unused")
		boolean result = execute(sqlString, //
				purchase.getId(), //
				purchase.getCustomerId(), //
				purchase.getBookId(), //
				purchase.getPrice()); //
		// LOG.debug(String.format("Adding %s was %s", purchase, result ? "successful" : "unsuccessful"));
		// I don't know why but it says unsuccessful, test to see if it's actually in the table or not
	}

	/**
	 * Update the book.
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void update(Purchase purchase) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, WHERE %s=?", TABLE_NAME, //
				Column.ID.name, //
				Column.CUSTOMER_ID.name, //
				Column.BOOK_ID.name, //
				Column.PRICE.name); //
		// Column.JOINED_DATE.name, //

		LOG.debug("Update statment: " + sqlString);

		boolean result = execute(sqlString, //
				purchase.getId(), //
				purchase.getCustomerId(), //
				purchase.getBookId(), //
				purchase.getPrice()); //
		// Database.isMsSqlServer() ? customer.getJoinedDate() : toTimestamp(customer.getJoinedDate()), //
		// customer.getId());
		LOG.debug(String.format("Updating %s was %s", purchase, result ? "successful" : "unsuccessful"));
	}

	/**
	 * Delete the purchase from the database.
	 * 
	 * @param purchase
	 * @throws SQLException
	 */
	public void delete(Purchase purchase) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();

			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, Column.ID.name, purchase.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	/**
	 * Retrieve all the purchase IDs from the database
	 * 
	 * @return the list of purchase IDs
	 * @throws SQLException
	 */
	public List<Long> getPurchaseIds() throws SQLException {
		List<Long> ids = new ArrayList<>();

		String selectString = String.format("SELECT %s FROM %s", Column.ID.name, TABLE_NAME);
		LOG.debug(selectString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				ids.add(resultSet.getLong(Column.ID.name));
			}

		} finally {
			close(statement);
		}

		LOG.debug(String.format("Loaded %d purchase IDs from the database", ids.size()));

		return ids;
	}

	/**
	 * @param purchaseId
	 * @return
	 * @throws Exception
	 */
	public Purchase getPurchase(Long purchaseId) throws Exception {
		String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Column.ID.name, purchaseId);
		LOG.debug(sqlString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlString);

			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					throw new ApplicationException(String.format("Expected one result, got %d", count));
				}

				// Timestamp timestamp = resultSet.getTimestamp(Column.JOINED_DATE.name);
				// LocalDate date = timestamp.toLocalDateTime().toLocalDate();

				Purchase purchase = new Purchase.Builder(resultSet.getLong(Column.ID.name), resultSet.getLong(Column.CUSTOMER_ID.name),
						resultSet.getLong(Column.BOOK_ID.name), resultSet.getFloat(Column.PRICE.name)).build();//

				// .setJoinedDate(date).build();

				return purchase;
			}
		} finally {
			close(statement);
		}

		return null;
	}

	public int countAllPurchases() throws Exception {
		Statement statement = null;
		int count = 0;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("SELECT COUNT(*) AS total FROM %s", tableName);
			ResultSet resultSet = statement.executeQuery(sqlString);
			if (resultSet.next()) {
				count = resultSet.getInt("total");
			}
		} finally {
			close(statement);
		}
		return count;
	}

	public enum Column {
		ID("id", 3), //
		CUSTOMER_ID("customer_id", 4), //
		BOOK_ID("book_id", 3), //
		PRICE("price", 5); //
		// JOINED_DATE("joinedDate", 8); //

		String name;
		int length;

		private Column(String name, int length) {
			this.name = name;
			this.length = length;
		}

	}

}
