/**
 * Project: A00913705Assign2_V3
 * File: CustomerReport.java
 */

package a00913705.io;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import a00913705.data.customer.Customer;
import a00913705.data.util.Common;

/**
 * Prints a formated Customers report.
 * 
 * @author Bobby Gill, A00913705
 *
 */
public class CustomerReport {

	public static final String HORIZONTAL_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------";
	public static final String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-25s %-12s %-12s %-15s %-25s%s%n";
	public static final String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-25s %-12s %-12s %-15s %-25s%s%n";

	/**
	 * private constructor to prevent instantiation
	 */
	private CustomerReport() {
	}

	/**
	 * Print the report.
	 * 
	 * @param customerForSqlServers
	 */
	public static void write(List<Customer> customers, PrintStream out) {
		out.println("Customers Report");
		out.println(HORIZONTAL_LINE);
		out.format(HEADER_FORMAT, "#", "ID", "First name", "Last name", "Street", "City", "Postal Code", "Phone", "Email", "Join Date");
		out.println(HORIZONTAL_LINE);

		int i = 0;
		for (Customer customer : customers) {
			LocalDate date = customer.getJoinedDate();
			out.format(CUSTOMER_FORMAT, ++i, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreet(),
					customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(), Common.DATE_FORMAT.format(date));
		}
	}
}
