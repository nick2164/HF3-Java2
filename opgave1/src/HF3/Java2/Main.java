package HF3.Java2;

public class Main {

	public static void main(String[] args) {

		opgave2();

		System.out.println();

		opgave3_1();

		System.out.println();

		opgave3_2();

		System.out.println();

		opgave3_2_1();

		System.out.println();

		opgave4();

	}

	private static void opgave2() {

		// Initialize SQLDatabaseConnection Class
		SQLDatabaseConnection Database = new SQLDatabaseConnection();

		System.out.println("Opgave 2");

		if ( Database.testConnection() ) {

			System.out.println("Connected to database!");

		} else {

			System.out.println("Something went wrong!");

		}

	}

	private static void opgave3_1() {

		// Initialize SQLDatabaseConnection Class
		SQLDatabaseConnection Database = new SQLDatabaseConnection();

		System.out.println("Opgave 3.1");

		Database.getFirst10Persons();

	}

	private static void opgave3_2() {

		// Initialize SQLDatabaseConnection Class
		SQLDatabaseConnection Database = new SQLDatabaseConnection();

		System.out.println("Opgave 3.2 Forsøg 1");

		// Setting Columns to be to be used for SQL Query in a String Array
		String[] Columns = new String[2];

		Columns[0] = "EmailAddress";
		Columns[1] = "ModifiedDate";

		Database.getFirst10FromChosenTable("EmailAddress", Columns);

	}

	private static void opgave3_2_1() {

		// Initialize SQLDatabaseConnection Class
		SQLDatabaseConnection Database = new SQLDatabaseConnection();

		System.out.println("Opgave 3.2 Forsøg 2");

		Database.getFromQuery("SELECT TOP 10 [FirstName], [MiddleName], [LastName] from [AdventureWorks2014].[Person].[Person] ORDER BY [FirstName] DESC");

	}

	private static void opgave4() {

		// Initialize SQLDatabaseConnection Class
		SQLDatabaseConnection Database = new SQLDatabaseConnection();

		System.out.println("Opgave 4");

		if( Database.outputXMLFromQuery("SELECT TOP 10 [FirstName], [MiddleName], [LastName] from [AdventureWorks2014].[Person].[Person] ORDER BY [FirstName] DESC") ) {
			System.out.println("Everything went OK!");
		} else {
			System.out.println("Something went bad!");
		}

	}

}