package edu.wctc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseConnection {

    // Connection constants for use by all servlets
    public static final String DRIVER_NAME = "jdbc:derby:";
    public static final String DATABASE_PATH = "db";
    public static final String USERNAME = "ALL_ITEMS";
    public static final String PASSWORD = "brian";

    public static void closeAll(Connection conn, Statement stmt, ResultSet rset) {
        if (rset != null) {
            try {
                rset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
