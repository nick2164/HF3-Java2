package HF3.Java2;

import com.microsoft.sqlserver.jdbc.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SQLDatabaseConnection {

	private Connection connection = null;

	/**
	 * Opening connection to local MSSQL server
	 */
	private void openConnection() {

		String connectionString =
				"jdbc:sqlserver://DESKTOP-01M7Q0D\\SQLEXPRESS;"
						+ "database=AdventureWorks2014;"
						+ "user=Opgave1User;"
						+ "password=abc112;"
						+ "loginTimeout=4;";
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection( connectionString );

		} catch ( Exception e ) {

			e.printStackTrace();

		}

	}

	/**
	 * Closing connection to local MSSQL server
	 */
	public void closeConnection() {

		if (connection != null)

			try {

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

			openConnection();

			closeConnection();

			return true;

	}

	/**
	 * Get data from the database with a custom SQL Query
	 *
	 * @param SQLQuery String
	 */
	public ResultSet getFromQuery( String SQLQuery ) {
		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			// using Database variables to execute SQL query
			statement = connection.createStatement();
			resultSet = statement.executeQuery( SQLQuery );

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {

			return resultSet;

		}

	}

	/**
	 * Validation for login.
	 *
	 * @param firstName String
	 * @param phoneNumber String
	 * @return Boolean
	 */
	public boolean validateCredentials( String firstName, String phoneNumber ) {

		// Open connection to MSSQL Database
		openConnection();

		// Setting database variables
		Statement statement = null;
		ResultSet resultSet = null;

		String SQLQuery = "SELECT COUNT(*) AS COUNT FROM [AdventureWorks2014].[Person].[Person] LEFT JOIN [AdventureWorks2014].[Person].[PersonPhone] ON [Person].[PersonPhone].BusinessEntityID = [Person].[Person].BusinessEntityID WHERE FirstName = '" + firstName + "' AND PhoneNumber = '" + phoneNumber + "';";

		try {

			// using Database variables to execute SQL query
			statement = connection.createStatement();
			resultSet = statement.executeQuery( SQLQuery );

			// Picking the first row
			resultSet.next();

			// Checking if count is equal to 0
			if ( resultSet.getInt("COUNT") == 0 ) {
				return false;
			} else {
				 return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeConnection();
		}

	}

	/**
	 * Output XML to the to a local file, that
	 * translates into a URL, from a database table
	 * that is defined in a custom SQL query
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
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQLQuery);

			// Setting XML output location
			String fileLocation = "persons.xml";

			// Initializing Persons class
			Persons persons = new Persons();
			persons.setPersons(new ArrayList<Person>());

			// Loop through resultSet
			while (resultSet.next()) {

				// Initializing Person class
				Person person = new Person();

				// Setting Person classes variables
				person.setFirstName(resultSet.getString("FirstName"));
				person.setLastName(resultSet.getString("LastName"));
				person.setPhoneNumber(resultSet.getString("PhoneNumber"));
				person.setCity(resultSet.getString("City"));
				person.setAddressLine1(resultSet.getString("AddressLine1"));
				person.setPostalCode(resultSet.getString("PostalCode"));

				// Adding Person class to Persons class
				persons.getPersons().add(person);

			}

			// Initializing JAXBContext and Marshaller for XML handling
			JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Setting Marshaller settings
			jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

			// Outputting an XML-file to the location of the Tomcat server's web folder.
			jaxbMarshaller.marshal( persons, new File( "C:\\Users\\HF\\IdeaProjects\\opgave2\\web" , fileLocation ) );

			// Return success
			return true;

		} catch (Exception e) {

			// Print stacktrace
			e.printStackTrace();

			// Return false
			return false;

		}

	}
}
