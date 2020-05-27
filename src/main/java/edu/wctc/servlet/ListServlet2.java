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

//this isnt being used anymore

@WebServlet(name = "ListServlet2", urlPatterns = "/List2")
public class ListServlet2 extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Declare outside the try/catch so the variables are in scope in the finally block
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            // Load the driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            String absPath = getServletContext().getRealPath("/") + "../../db";
            System.out.println(absPath);
            // Create a connection
            conn = DriverManager.getConnection(
                    "jdbc:derby:" + absPath,
                    "ITEMS",  // db username
                    "brian"); // db password

            // Create a statement to executeSQL
            stmt = conn.createStatement();

            rset = stmt.executeQuery("SELECT Item_ID, Item_Name, Category_ID FROM All_Items");

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
            request.getRequestDispatcher("view/List2.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            // If there's an exception locating the driver, send IT as the response
            response.getWriter().print(e.getMessage());
            e.printStackTrace();
        } finally {
            CloseConnection.closeAll(conn, stmt, rset);
        }
    }
}