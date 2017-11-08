// Loading required libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Register extends HttpServlet{
    
  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
  

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

    request.getRequestDispatcher("header.html").include(request, response);
int flag=1;
      try{
         // Register JDBC driver
         Class.forName("com.mysql.jdbc.Driver");

         // Open a connection
         //get values of all the variables 
         String name=request.getParameter("name");
         String email=request.getParameter("email");
         String phone =request.getParameter("phone");
         String address =request.getParameter("address");
         int yy=0;
         yy =Integer.parseInt(request.getParameter("yy"));
         String game =request.getParameter("game");
         String play ;
         if(request.getParameter("play")==null)
          play="off";
        else
          play="on";
         String m1,m2;


         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/user","root","root");
String sql;
         // Execute SQL query
         Statement stmt = conn.createStatement();
        sql = "SELECT * from user where email='"+email+"'";
         ResultSet rs = stmt.executeQuery(sql);

         // Extract data from result set
         if(rs.next())
         {
          m1="User already registered!!";
          m2="Try again!";
          flag=0;
         }

         
 else
 {        
         

if(yy==0 || game.equals("none") || name.equals("Name") || email.equals("Email") || phone.equals("Phone") || address.equals("Address"))
{
  m1="Please fill in all the fields !!";
  m2="Try again !!";
  flag=0;
}
else
{
  if(yy<15)
    {
      m1="You dont have enough age to learn right now , we cannot register you at this moment!!";
      m2="Come back after "+(15-yy)+" years!!";
      flag=0;
    }
    else
    {
      sql = "insert into user (name,email,phone,address,yob,sport)values('"+name+"','"+email+"','"+phone+"','"+address+"','"+yy+"','"+game+"')";
      stmt.executeUpdate(sql);
       HttpSession session=request.getSession();
        session.setAttribute("email",email);
        session.setAttribute("sport",game);
      m1="Thank you for your time";
      m2="You are successfully registered.";
      flag=1;
    }
}

}
if(play.equals("on")&& flag==1)
{
 out.println("<div class='four'><div class='container'><p>"+m1+"</p>  <h2>"+m2+"</h2><br><br><br><br><br><br><a href='register.html' class='more'>Go Back </a><br><p>To avail your discount click on the button to earn it</p><a href='Quiz' class='more'>Take the Quiz </a></div></div>");
}
else{

          out.println("<div class='four'><div class='container'><p>"+m1+"</p>  <h2>"+m2+"</h2><br><br><br><br><br><br><a href='register.html' class='more'>Go Back </a></div></div>");
     } 
         stmt.close();
         conn.close();

          request.getRequestDispatcher("footer.html").include(request, response);
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println("An error occoured with the sql query please go back and try again or check your database connection..");;
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println("An unnexpected error occoured please try again after some time....");
      } //end try
   }
}
