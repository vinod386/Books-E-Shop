package com.vinod.eshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Order
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SCOTT","TIGER");
			Statement stmt = conn.createStatement();
			String authors[] = request.getParameterValues("author"); // getting all the selected author names
			if(authors == null){
				pw.println("You didn't select any author");
				return;
			}
			
				
			String query = "select * from books where author in ('"+authors[0]+"'";
			for(int i=1;i<authors.length;i++)
				query = query +",'"+authors[i] + "'";
			query = query + ")";
			
			ResultSet res = stmt.executeQuery(query);
			
			pw.println("<html><head><title>Query Results</title></head>");
			pw.println("<body><h2>Thank you for your query.</h2>");
			pw.println("<form method='post' action='OrderServlet'>");
			pw.println("<table border=1 style='width:100%'><tr><th> </th><th>Author</th><th>Title</th><th>Price</th></tr>");
			while(res.next()){
				pw.println("<tr><td><input type='checkbox' name='id' value='"
						   +res.getString("id")+"' /></td><td>"
						   +res.getString("author")+"</td><td>"
						   +res.getString("title")+"</td><td>$"
						   +res.getFloat("price")+"</td></tr>");
			}
			pw.println("</table><br>");
			pw.println("<p>Enter your Name: <input type='text' name='name'/></p>");
			pw.println("<p>Enter your Email: <input type='text' name='email'/></p>");
			pw.println("<p>Enter your Phone number: <input type='text' name='phone'/></p>");
			pw.println("<input type='Submit' value='Order'></p>");
			pw.println("</form></body></html>");
			
			stmt.close();
			conn.close();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
			
	}
}


