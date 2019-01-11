/**
 * Project: A00913705Assign2_V3
 * File: BookReader.java
 * Date: Nov 25, 2017
 * Time: 6:05:49 PM
 */
package a00913705.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00913705.ApplicationException;
import a00913705.data.book.Book;
import a00913705.db.BookDao;

/**
 * @author Bobby Gill, A00913705
 *
 */
public class BookReader {

	public static final String FILENAME = "books500.csv";
	// public static int bookCount;

	private static final Logger LOG = LogManager.getLogger();

	/**
	 * private constructor to prevent instantiation
	 */
	private BookReader() {
	}

	/**
	 * Read the book input data.
	 * 
	 * @return A collection of books.
	 * @throws ApplicationException
	 * @throws IOException
	 */
	public static void read(File bookDataFile, BookDao dao) throws ApplicationException {
		File file = new File(FILENAME);
		// bookCount = 0;
		FileReader in;
		Iterable<CSVRecord> records;
		try {
			in = new FileReader(file);
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}

		// Map<Long, Book> books = new HashMap<>();

		LOG.debug("Reading" + file.getAbsolutePath());
		for (CSVRecord record : records) {
			String id = record.get("book_id");
			String isbn = record.get("isbn");
			String authors = record.get("authors");
			String original_publication_year = record.get("original_publication_year");
			String original_title = record.get("original_title");
			String average_rating = record.get("average_rating");
			String ratings_count = record.get("ratings_count");
			String image_url = record.get("image_url");

			Book book = new Book.Builder(Long.parseLong(id)). //
					setIsbn(isbn). //
					setAuthors(authors). //
					setYear(Integer.parseInt(original_publication_year)). //
					setTitle(original_title). //
					setRating(Float.parseFloat(average_rating)). // //
					setRatingsCount(Integer.parseInt(ratings_count)). //
					setImageUrl(image_url).//
					build();

			try {
				// books.put(book.getId(), book);
				dao.add(book);
				// bookCount++;
			} catch (SQLException e) {
				throw new ApplicationException(e);
			}

			LOG.debug("Added " + book.toString() + " as " + id);
		}

		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				throw new ApplicationException(e);
			}
		}

		// return bookCount;
	}

}
