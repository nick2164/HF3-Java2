package HF3.Java2;

// Use the JDBC driver
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class SQLDatabaseConnection {

	private Connection connection = null;

	/**
	 * Opening connection to local MSSQL server
	 */
	private void openConnection() {

		// Defining the database connection string
		String connectionString =
				"jdbc:sqlserver://DESKTOP-01M7Q0D\\SQLEXPRESS;"
						+ "database=AdventureWorks2014;"
						+ "user=Opgave1User;"
						+ "password=abc112;"
						+ "loginTimeout=4;";
		try {

			// Creating database connection
			connection = DriverManager.getConnection( connectionString );

		} catch ( Exception e ) {

			e.printStackTrace();

		}

	}

	/**
	 * Closing connection to local MSSQL server
	 */
	private void closeConnection() {

		if (connection != null)

			try {

				// Closing earlier established database connection.
				connection.close();

			}  catch(Exception e) {

			}

	}

	/**
	 * This method is for testing the database connection
	 * and catching any errors along the way
	 *
	 * @return boolean
	 */
	public Boolean testConnection() {

		try{

			// Opening connection to local MSSQL server
			openConnection();

			// Closing connection to local MSSQL server
			closeConnection();

			return true;

		} catch (Exception e) {

			return false;

		}

	}

	/**
	 * Get the first 10 rows from a given table,
	 * with given columns.
	 *
	 * @param table String
	 * @param Columns String[]
	 */
	public void getFirst10FromChosenTable( String table, String[] Columns ) {
		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// Parsing StringArray with columns to a usable String
			String usableColumns = "";

			for( String Column : Columns ) {

				usableColumns += "[" +  Column + "],";

			}

			usableColumns = usableColumns.substring(0, usableColumns.length() - 1);

			// Setting SQL Query
			String selectSql = "SELECT TOP 10 " + usableColumns + " from [AdventureWorks2014].[Person].[" + table + "]";

			// Setting database variables
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);

			// Print results from select statement
			while (resultSet.next())
			{
				System.out.println( "EmailAddress = " + resultSet.getString(1) );
				System.out.println( "Modified Date = " + resultSet.getString(2) );
			}
		}

		catch (Exception e) {

			// Print stacktrace
			e.printStackTrace();

		} finally {

			// Close connection
			closeConnection();

		}

	}

	/**
	 * Get the first 10 rows from Persons table.
	 */
	public void getFirst10Persons() {
		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// Setting SQL Query
			String selectSql = "SELECT TOP 10 [FirstName], [MiddleName], [LastName] from [AdventureWorks2014].[Person].[Person]";

			// Setting database variables
			statement = connection.createStatement();
			resultSet = statement.executeQuery( selectSql );

			// Parse SQL Query to a ArrayList with table names
			ArrayList tables = Tools.extractTablesFromSQLQueryToArrayList( selectSql );

			// Loop through resultSet ant print results to console
			Tools.printResultSetFromSQLQuery(resultSet,tables);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			// Close connection to MSSQL Database
			closeConnection();

		}
	}

	/**
	 * Get data from the database with a custom SQL Query
	 *
	 * @param SQLQuery String
	 */
	public void getFromQuery( String SQLQuery ) {
		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// using Database variables to execute SQL query
			statement = connection.createStatement();
			resultSet = statement.executeQuery( SQLQuery );

			// Parse SQL Query to a ArrayList with table names
			ArrayList tables = Tools.extractTablesFromSQLQueryToArrayList( SQLQuery );

			// Loop through resultSet ant print results to console
			Tools.printResultSetFromSQLQuery(resultSet,tables);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// Close connection to MSSQL Database
			closeConnection();
		}

	}

	/**
	 * Output XML to the console and to a local file
	 * from a database table that is defined in a
	 * custum SQL query
	 *
	 * @param SQLQuery String
	 * @return Boolean
	 */
	public boolean outputXMLFromQuery( String SQLQuery ) {
		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// Using Database variables to execute SQL query
			statement			= connection.createStatement();
			resultSet			= statement.executeQuery( SQLQuery );

			// Setting XML output location
			String fileLocation = "./out/persons.XML";

			// Initializing Persons class
			Persons persons = new Persons();
			persons.setPersons( new ArrayList<Person>() );

			// Loop through resultSet
			while ( resultSet.next() ){

				// Initializing Person class
				Person person = new Person();

				// Setting Person classes variables
				person.setFirstName( resultSet.getString(1) );
				person.setMiddleName( resultSet.getString(2) );
				person.setLastName( resultSet.getString(3) );

				// Adding Person class to Persons class
				persons.getPersons().add( person );

			}

			// Initializing JAXBContext and Marshaller for XML handling
			JAXBContext jaxbContext		= JAXBContext.newInstance( Persons.class );
			Marshaller jaxbMarshaller	= jaxbContext.createMarshaller();

			// Setting Marshaller settings
			jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

			// Marshalling Persons in console
			jaxbMarshaller.marshal( persons, System.out );

			// Marshalling Persons in a XML file
			jaxbMarshaller.marshal( persons, new File( fileLocation ) );
			System.out.println("XML was writen to " + fileLocation);

			// Return success
			return true;

		} catch ( Exception e ) {

			// Print stacktrace
			e.printStackTrace();

			// Return false
			return false;

		}

	}

}