

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

public class Quiz extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("header.html").include(request, response);
		String m1,m2;
		HttpSession session=request.getSession(false);
		if(session == null)
                {
                	 m2="Gottcha!!";
                     m1="You need to register before u can get a quiz!!";
                    out.println("<div class='four'><div class='container'><p>"+m1+"</p>  <h2>"+m2+"</h2><br><br><br><br><br><br><a href='register.html' class='more'>Register </a></div></div>");
     
                }
                else{

					

                	try{

         Class.forName("com.mysql.jdbc.Driver");

      
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");

        Statement ck = conn.createStatement();
         String sql;
         String email=(String)session.getAttribute("email");
         sql = "SELECT * from user where email='"+email+"'";
         ResultSet ck1 = ck.executeQuery(sql);
         ck1.next();
         int attempt=ck1.getInt("attempt");
           out.println("<div class='four'><div class='container'>"); 
            


         if(attempt==0)
         { 
         	
            sql="update user set attempt =1 where email='"+email+"'";
            ck.executeUpdate(sql);
            ck.close();
            ck1.close();
         out.print("<h4>*All the questions are compulsary || there is no negative marking || Please do not refresh or leave the page otherwise u will b awarded a zero! * .</h4><br><br><br><br><br>");
      
             sql = "SELECT * FROM questions  where cat='"+session.getAttribute("sport")+"' limit 10";

                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql);
                int cc=1;
                out.print("<form action='Calculate' method='post'>");
                while(rs2.next())
             {
                String question=rs2.getString("question");
                String op1=rs2.getString("op1");
                String op2=rs2.getString("op2");
                String op3=rs2.getString("op3");
                String qn="Q"+cc;

                out.println("<p><b> "+qn+": "+question+" </b></p>");
                out.println("<p><ul>");
                out.println("<input type='radio' name='"+qn+"' value='1' id='"+qn+"'><label for='"+qn+"'>"+op1+"</label><br/>");
                out.println("<input type='radio' name='"+qn+"' value='2' id='"+qn+"'><label for='"+qn+"'>"+op2+"</label><br/>");
                out.println("<input type='radio' name='"+qn+"' value='3' id='"+qn+"'><label for='"+qn+"'>"+op3+"</label><br/>");
                out.println("</ul></p><br><br><br>");
                cc++;
            }
         
			out.println("<br><br><br><br><input class='more' type='submit' value='Finish'>");
          out.println("</form>");
          out.println("</div></div>");
		}
        else
        {
            out.println("<h2>You have already given the quiz.</h2>");
            out.println("</div></div>");
        }
         
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }


		
		}
		 
          request.getRequestDispatcher("footer.html").include(request, response);   
		out.close();
	}
}
