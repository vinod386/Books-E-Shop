package com.vinod.eshop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
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
			pw.println("<html><head><title>Order Results</title></head><body>");

			String[] ids = request.getParameterValues("id");
			if(ids == null)
			{
				pw.println("<h1>0 books selected. Please select atleast one book<h1>");
				return;
			}
            pw.println("<p>"+ids.length+" book(s) ordered");

			for(int i=0;i<ids.length;i++){
				String query = "update books set quantity = quantity -1 where id="+ids[i];
				stmt.executeUpdate(query);
				query = "insert into orders values("+ids[i]+",'"+request.getParameter("name")
				         +"','"+request.getParameter("email")+"',"+request.getParameter("phone")+",1)";
                stmt.executeUpdate(query);
                pw.println("<p>You have ordered book id="+ids[i]+"</p>");
			}
			pw.println("<p>Thanks for shopping</p>");
			pw.println("</body></html>");
	
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
