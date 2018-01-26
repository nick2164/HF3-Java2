package HF3.Java2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "databaseConnection")
public class databaseConnection extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		SQLDatabaseConnection SQLDatabaseConnection = new SQLDatabaseConnection();

		if( SQLDatabaseConnection.testConnection() ) {
			out.print("It's connected!");
		} else {
			out.print("Connection is not established.");
		}

		SQLDatabaseConnection.closeConnection();

	}



}
