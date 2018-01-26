package HF3.Java2;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class Tools {

	/**
	 * Parse SQL Query to a ArrayList with table names
	 *
	 * @param SQLQuery String
	 * @return ArrayList
	 */
	public static ArrayList extractTablesFromSQLQueryToArrayList( String SQLQuery ) {

		// Takes tables from SQL string and places it into another String, with regular expression
		Matcher matcher = Pattern.compile("^SELECT [A-Z]?[A-Z]?[A-Z]? ?[0-9]?[0-9]? (\\[.*\\]) from.*$", Pattern.CASE_INSENSITIVE ).matcher( SQLQuery );
		matcher.matches();

		// Sanitizing tables to a usable String format
		SQLQuery = matcher.group(1);
		SQLQuery = SQLQuery.replace("[","");
		SQLQuery = SQLQuery.replace("]","");
		SQLQuery = SQLQuery.replace(" ","");

		// Puts tables into an ArrayList
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(SQLQuery.split(",")));

		return list;

	}

	/**
	 * Loop through resultSet ant print results to console
	 *
	 * @param resultSet ResultSet
	 * @param tables ArrayList
	 */
	public static void printResultSetFromSQLQuery(ResultSet resultSet, ArrayList tables) throws SQLException {

		// Go through results
		while ( resultSet.next() ) {

			// loop through each table name from the search
			for ( Object table : tables ) {

				// Check if result is null
				if ( resultSet.getString( table.toString() ) != null )
					// Print result based on table name
					System.out.print(resultSet.getString( table.toString() ) + " ");

			}
			// Print a new line
			System.out.print("\n");

		}

	}

}
