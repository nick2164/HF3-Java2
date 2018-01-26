package HF3.Java2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "XMLSave")
public class XMLSave extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String where = "";

		int count = 0;

		// Saving post parameters as string array.
		String[] outerArray = request.getParameterValues("id");

		// Looping through string array
		for( String row : outerArray) {

			// Checking if checkbox is checked for the current checkbox number
			if( request.getParameter("checkBox_" + row) != null ) {

				// Appending number for later usage in SQL Query
				where = where.concat(row + ",");

				count++;

			}

		}

		if(count != 0) {

			// Sanatizing numbers for last ','
			where = where.substring(0, where.length() - 1);

			// Defining SQLDatabaseConnection class
			SQLDatabaseConnection SQLDatabaseConnection = new SQLDatabaseConnection();

			// Defining SQL Query
			String SQLQuery = "SELECT \n" +
					"\tPerson.BusinessEntityID, FirstName, LastName, PhoneNumber, Address.City, Address.AddressLine1, Address.PostalCode\n" +
					"FROM \n" +
					"\t[AdventureWorks2014].[Person].[Person]\n" +
					"LEFT JOIN\n" +
					"\t[AdventureWorks2014].[Person].[PersonPhone] on [Person].[PersonPhone].[BusinessEntityID] = [Person].[Person].[BusinessEntityID]\n" +
					"LEFT JOIN \n" +
					"\t[AdventureWorks2014].[Person].[BusinessEntityAddress] on [Person].[BusinessEntityAddress].[BusinessEntityID] = [Person].[Person].[BusinessEntityID]\n" +
					"LEFT JOIN \n" +
					"[AdventureWorks2014].[Person].[Address] on [Person].[Address].[AddressID] = [Person].[BusinessEntityAddress].[AddressID]\n" +
					"WHERE \n" +
					"\t[Person].[BusinessEntityID] in(" + where + ");";


			// Output XML to the to a local file, that translates into a URL, from a database table that is defined in a custom SQL query
			SQLDatabaseConnection.outputXMLFromQuery(SQLQuery);

			// Setting the right content type for XML output.
			response.setHeader("Content-Disposition", "attachment; filename=persons.xml");

			// Redirect to the new XML-file that has been created.
			response.sendRedirect("persons.xml");

		} else {

			response.sendError(400, "Bad Request: Can't create XML from nothing.");

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
