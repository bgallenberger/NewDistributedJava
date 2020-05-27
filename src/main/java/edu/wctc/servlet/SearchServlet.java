package edu.wctc.servlet;

import edu.wctc.CloseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "SearchList", urlPatterns = "/Search")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Declare outside the try/catch so the variables are in scope in the finally block
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String searchName = "";

        try {
            searchName = request.getParameter("Item_Name");
            if(searchName == null){
                searchName = "";
            }
            // Load the driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            String absPath = getServletContext().getRealPath("/") + "../../db";

            // Create a connection
            conn = DriverManager.getConnection(
                    "jdbc:derby:" + absPath,
                    "ITEMS",  // db username
                    "brian"); // db password

            // Build the query as a String
            StringBuilder sql = new StringBuilder("select Item_ID, Item_Name from All_Items Where Item_Name like ? order by Item_Id");
            // Create a statement to executeSQL
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, "%" + searchName + "%");

            rset = pstmt.executeQuery();

            //make html
            StringBuilder output = new StringBuilder("<html>" +
                    "<head>\n" +
                    "    <title>List</title>\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"resources/style.css\">\n" +
                    "</head><body>" +
                    "   <div id=\"loginLink\"><a href=\"/DistributedJavaWebSite/view/List.jsp\">Login</a></div>\n" +
                    "   <h1>My Site</h1>" +
                    "<form method=\"get\" action=\"/DistributedJavaWebSite/Search\">" +
                    "<input type=\"text\" class=\"searchItem\" value=\"search\" name=\"Item_Name\">" +
                    "</form>" +
                    "<form><table>");

            if(searchName != null) {
                output.append("<p> Searching for: ");
                output.append(searchName);
                output.append("</p>");
            }

            output.append("<table>");
            output.append("<th>ID</th>");
            output.append("<th>Name</th>");
            output.append("<th>Edit</th>");
            output.append("<th>Delete</th>");

            while (rset.next()) {
                int id = rset.getInt("Item_Id");
                String name = rset.getString(2);
                output.append("<tr><td>").append(id);
                output.append("</td><td>").append(name);
                output.append("</td><td><input type=\"button\" class=\"editItem\" value=\"edit\"></td><td><input type=\"button\" class=\"deleteItem\" value=\"delete\"></td>");
                output.append("</tr>");
            }

            output.append("</table><p><input type=\"button\" class=\"addItem\" value=\"add\"></p></form></body></html>");

            // Send the HTML as the response
            response.setContentType("text/html");
            response.getWriter().print(output.toString());

        } catch (SQLException | ClassNotFoundException e) {
            // If there's an exception locating the driver, send IT as the response
            response.getWriter().print(e.getMessage());
            e.printStackTrace();
        } finally {
            CloseConnection.closeAll(conn, pstmt, rset);
        }
    }
}