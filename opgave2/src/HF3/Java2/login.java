package HF3.Java2;

import com.sun.xml.internal.bind.v2.TODO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@WebServlet(name = "login")
public class login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SQLDatabaseConnection SQLDatabaseConnection = new SQLDatabaseConnection();
		String firstName = request.getParameter("FirstName");
		String phoneNumber = request.getParameter("PhoneNumber");

		if ( SQLDatabaseConnection.validateCredentials(firstName,phoneNumber) ) {

			response.sendRedirect("/success.jsp");

		} else {

			response.sendRedirect("/invalidCredentials.jsp");

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		SQLDatabaseConnection SQLDatabaseConnection = new SQLDatabaseConnection();

		String SQLQuery = "SELECT TOP 10 Person.BusinessEntityID, FirstName, LastName, PhoneNumber, Address.City, Address.AddressLine1, Address.PostalCode FROM [AdventureWorks2014].[Person].[Person] LEFT JOIN [AdventureWorks2014].[Person].[PersonPhone] on [Person].[PersonPhone].[BusinessEntityID] = [Person].[Person].[BusinessEntityID] LEFT JOIN [AdventureWorks2014].[Person].[BusinessEntityAddress] on [Person].[BusinessEntityAddress].[BusinessEntityID] = [Person].[Person].[BusinessEntityID] LEFT JOIN [AdventureWorks2014].[Person].[Address] on [Person].[Address].[AddressID] = [Person].[BusinessEntityAddress].[AddressID]";

		ResultSet resultSet = SQLDatabaseConnection.getFromQuery(SQLQuery);

		try {
			Tools.dumpData( resultSet, out, SQLQuery );
		} catch (Exception e) {
			e.printStackTrace();
		}

		SQLDatabaseConnection.closeConnection();

	}

}
