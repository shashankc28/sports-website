

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class ProfileServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);
		
		HttpSession session=request.getSession(false);
		if(session == null)
                {
                    out.print("Please login first");
                    request.getRequestDispatcher("login.html").include(request, response);
                }
                else{

					out.print("Hello, user with id= "+session.getAttribute("id")+" ,Welcome to Profile<br><br>");

                	try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        
         Statement stmt = conn.createStatement();
         String sql;
         String id1=(String)session.getAttribute("id");
         sql = "SELECT * from user where id="+id1;
         ResultSet rs = stmt.executeQuery(sql);
         rs.next();
         int score=rs.getInt("score");
         int attempt=rs.getInt("attempt");
            
 			 out.print("Your score is ="+score);


         if(attempt==0)
         { 
           out.print("<br><br>You have not given the test . To start the test click <a href='Quiz'>here ");
         }





		
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }


		
		}
		out.close();
	}
}
