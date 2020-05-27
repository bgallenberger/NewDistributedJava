package edu.wctc.servlet;

import edu.wctc.CloseConnection;
import edu.wctc.entity.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet2", urlPatterns = "/Search2")
public class SearchServlet2 extends HttpServlet {
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
            StringBuilder sql = new StringBuilder("select Item_ID, Item_Name, Category_ID from All_Items Where Item_Name like ? order by Item_Id");
            // Create a statement to executeSQL
            pstmt = conn.prepareStatement(sql.toString());

            pstmt.setString(1, "%" + searchName + "%");

            rset = pstmt.executeQuery();

            //make html

            List<Item> itemList = new ArrayList<Item>();

            // Loop while the result set has more rows
            while (rset.next()) {
                Item item = new Item();
                item.setItemId(rset.getInt(1));
                item.setName(rset.getString(2));
                item.setCategoryId(rset.getInt(3));
                itemList.add(item);
            }

            request.setAttribute("items", itemList);
            request.getRequestDispatcher("view/search2.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            // If there's an exception locating the driver, send IT as the response
            response.getWriter().print(e.getMessage());
            e.printStackTrace();
        } finally {
            CloseConnection.closeAll(conn, pstmt, rset);
        }
    }
}