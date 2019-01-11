/**
 * Project: A00913705Assign2_V3
 * File: BookDao.java
 * Date: Nov 25, 2017
 * Time: 6:17:15 PM
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
import a00913705.data.book.Book;
import a00913705.io.BookReader;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class BookDao extends Dao {

	public static final String TABLE_NAME = DbConstants.TABLE_ROOT + "Books";

	private static final String BOOKS_DATA_FILENAME = "books500.csv";
	private static Logger LOG = LogManager.getLogger();
	private static BookDao theInstance = new BookDao();
	private static Database database;

	private BookDao() {
		super(TABLE_NAME);

		database = Database.getTheInstance();
	}

	public static BookDao getTheInstance() {
		return theInstance;
	}

	/**
	 * @param bookDataFile
	 * @throws ApplicationException
	 * @throws SQLException
	 */
	public void init() throws ApplicationException {
		File bookDataFile = new File(BOOKS_DATA_FILENAME);
		try {
			if (!Database.tableExists(BookDao.TABLE_NAME) || Database.dbTableDropRequested()) {
				if (Database.tableExists(BookDao.TABLE_NAME) && Database.dbTableDropRequested()) {
					drop();
				}

				create();

				LOG.debug("Inserting the books");

				if (!bookDataFile.exists()) {
					throw new ApplicationException(String.format("Required '%s' is missing.", BOOKS_DATA_FILENAME));
				}

				BookReader.read(bookDataFile, this);
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
						+ "%s VARCHAR(%d), " // ISBN
						+ "%s VARCHAR(%d), " // AUTHORS
						+ "%s VARCHAR(%d), " // YEAR
						+ "%s VARCHAR(%d), " // TITLE
						+ "%s VARCHAR(%d), " // RATING
						+ "%s VARCHAR(%d), " // RATINGS_COUNT
						+ "%s VARCHAR(%d))", // IMAGE_URL
				// + (Database.isMsSqlServer() ? "%s DATETIME, " : "%s TIMESTAMP, ") // JOINED_DATE
				// + "PRIMARY KEY (%s))", // ID
				TABLE_NAME, //
				Column.ID.name, //
				Column.ISBN.name, Column.ISBN.length, //
				Column.AUTHOR.name, Column.AUTHOR.length, //
				Column.YEAR.name, Column.YEAR.length, //
				Column.TITLE.name, Column.TITLE.length, //
				Column.RATING.name, Column.RATING.length, //
				Column.RATINGS_COUNT.name, Column.RATINGS_COUNT.length, //
				Column.IMAGE_URL.name, Column.IMAGE_URL.length); //
		// Column.JOINED_DATE.name, //
		// Column.ID.name);

		super.create(sqlString);
	}

	public void add(Book book) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME);
		@SuppressWarnings("unused")
		boolean result = execute(sqlString, //
				book.getId(), //
				book.getIsbn(), //
				book.getAuthors(), //
				book.getYear(), //
				book.getTitle(), //
				book.getRating(), //
				book.getRatingsCount(), //
				book.getImageUrl()); //
		// LOG.debug(String.format("Adding %s was %s", book, result ? "successful" : "unsuccessful"));
		// I don't know why but it says unsuccessful, test to see if it's actually in the table or not
	}

	/**
	 * Update the book.
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void update(Book book) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?", TABLE_NAME, //
				Column.ID.name, //
				Column.ISBN.name, //
				Column.AUTHOR.name, //
				Column.YEAR.name, //
				Column.TITLE.name, //
				Column.RATING.name, //
				Column.RATINGS_COUNT.name, //
				Column.IMAGE_URL.name); //
		// Column.JOINED_DATE.name, //

		LOG.debug("Update statment: " + sqlString);

		boolean result = execute(sqlString, //
				book.getId(), //
				book.getIsbn(), //
				book.getAuthors(), //
				book.getYear(), //
				book.getTitle(), //
				book.getRating(), //
				book.getRatingsCount(), //
				book.getImageUrl()); //
		// Database.isMsSqlServer() ? customer.getJoinedDate() : toTimestamp(customer.getJoinedDate()), //
		// customer.getId());
		LOG.debug(String.format("Updating %s was %s", book, result ? "successful" : "unsuccessful"));
	}

	/**
	 * Delete the book from the database.
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void delete(Book book) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = database.getConnection();
			statement = connection.createStatement();

			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, Column.ID.name, book.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	/**
	 * Retrieve all the book IDs from the database
	 * 
	 * @return the list of book IDs
	 * @throws SQLException
	 */
	public List<Long> getBookIds() throws SQLException {
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

		LOG.debug(String.format("Loaded %d book IDs from the database", ids.size()));

		return ids;
	}

	/**
	 * @param bookId
	 * @return
	 * @throws Exception
	 */
	public Book getBook(Long bookId) throws Exception {
		String sqlString = String.format("SELECT * FROM %s WHERE %s = %d", TABLE_NAME, Column.ID.name, bookId);
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

				Book book = new Book.Builder(resultSet.getInt(Column.ID.name)) //
						.setIsbn(resultSet.getString(Column.ISBN.name)) //
						.setAuthors(resultSet.getString(Column.AUTHOR.name)) //
						.setYear(resultSet.getInt(Column.YEAR.name)) //
						.setTitle(resultSet.getString(Column.TITLE.name)) //
						.setRating(resultSet.getFloat(Column.RATING.name)) //
						.setRatingsCount(resultSet.getInt(Column.RATINGS_COUNT.name)) //
						.setImageUrl(resultSet.getString(Column.IMAGE_URL.name)).build(); //
				// .setJoinedDate(date).build();

				return book;
			}
		} finally {
			close(statement);
		}

		return null;
	}

	public int countAllBooks() throws Exception {
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
		ID("id", 16), //
		ISBN("isbn", 20), //
		AUTHOR("author", 100), //
		YEAR("year", 10), //
		TITLE("title", 100), //
		RATING("rating", 10), //
		RATINGS_COUNT("ratingscount", 12), //
		IMAGE_URL("imageurl", 100); //
		// JOINED_DATE("joinedDate", 8); //

		String name;
		int length;

		private Column(String name, int length) {
			this.name = name;
			this.length = length;
		}

	}

}
