

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class Calculate extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		HttpSession session=request.getSession(false);
		String m1,m2;
		try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        out.println("<div class='four'><div class='container'>"); 
         Statement stmt = conn.createStatement();
         String sql;
         sql = "SELECT * from questions where cat='"+session.getAttribute("sport")+"' limit 10";
         ResultSet rs = stmt.executeQuery(sql);
         int cc=1;
         int score=0;
       while(rs.next())
       {
         int ans=rs.getInt("answer");
         String a=ans+"";
         String pp="Q"+cc;
         String ans1=request.getParameter(pp);
         if(ans1.equals(a))
         {
            score++;
         }
         cc++;
       }
       m1="Thank you for your time!! Your score is "+score+"/10";
                     m2="You have earned a discount of "+score+"0%";
                    out.println("<p>"+m1+"</p>  <h2>"+m2+"</h2><br><br><br><br><br><br><a href='index.html' class='more'>Home </a>");
		sql= "update user set score="+score+" where email='"+session.getAttribute("email")+"'";
      stmt.executeUpdate(sql);
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      } //end try
      out.println("</div></div>");
      request.getRequestDispatcher("footer.html").include(request, response);  
      out.close();
	}

}
