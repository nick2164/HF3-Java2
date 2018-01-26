package HF3.Java2;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

	/**
	 * Parse SQL Query to a ArrayList with table names
	 *
	 * @param SQLQuery String
	 * @return ArrayList
	 */
	public static ArrayList extractTablesFromSQLQueryToArrayList( String SQLQuery ) {

		// Takes tables from SQL string and places it into another String, with regular expression
		Matcher matcher = Pattern.compile("^SELECT [A-Z]?[A-Z]?[A-Z]? ?[0-9]?[0-9]? (.*) from.*$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES ).matcher( SQLQuery );
		matcher.matches();

		// Sanitizing tables to a usable String format
		SQLQuery = matcher.group(1);
		SQLQuery = SQLQuery.replace(" ","");

		// Puts tables into an ArrayList
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(SQLQuery.split(",")));

		int count = 0;

		for( String row : list ) {

			if(row.contains(".")) {

				String[] bits = row.split("\\.");

				list.set(count,bits[1]);

			}

			count++;

		}

		return list;

	}

	/**
	 * This method takes a resultSet and turns into a
	 * HTML table.
	 *
	 * @param rs ResultSet
	 * @param out PrintWriter
	 * @param SQLQuery String
	 * @throws Exception
	 */
	public static void dumpData(ResultSet rs, PrintWriter out, String SQLQuery) throws Exception {

		// Parse SQL Query to a ArrayList with table names
		ArrayList tables = extractTablesFromSQLQueryToArrayList( SQLQuery );


		out.println("<table>");
		out.println("<form method='post' action='XMLSave'>");
		out.println("<tr>");

		for ( Object table : tables ) {
			out.println("<th>");
			out.println(table.toString());
			out.println("</th>");
		}

		out.println("</tr>");

		// Go through results
		while ( rs.next() ) {

			out.println("<tr>");


			// loop through each table name from the search
			for ( Object table : tables ) {

				// Checking if table name is empty and not equal to BusinessEntityID
				if ( rs.getString( table.toString() ) != null && !table.toString().equals("BusinessEntityID")) {

					out.println("<td>");

					// Print result based on table name
					out.println(rs.getString(table.toString()));

					out.println("</td>");

				// Checking if table name is equal to BusinessEntityID
				} else if( table.toString().equals("BusinessEntityID") ) {

					out.println("<td>");
					out.println("<input name='checkBox_" + rs.getString( "BusinessEntityID" ) + "' type='checkbox'>");
					out.println("<input name='id' type='hidden' value='" + rs.getString( "BusinessEntityID" ) + "'>");
					out.println("</td>");

				}

			}

			out.println("</tr>");

		}

		out.println("</table>");

		out.println("<p>Do you wanna save the chosen person(s) to a XML-file file?</p>");

		out.println("<p class='submit'><input type='submit' name='commit' value='Save file'></p>");

		out.println("</form>");

	}

}
