package ru.akirakozov.sd.refactoring.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public static final String DATABASE_URL = "jdbc:sqlite:test.db";

    static void setupForTest() throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATABASE_URL).createStatement()) {
            String query = "DROP TABLE IF EXISTS PRODUCT;\n" +
                    "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL);";
            statement.executeUpdate(query);
        }
    }

    static void deleteAllProducts() throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATABASE_URL).createStatement()) {
            String query = "DELETE FROM PRODUCT;";
            statement.executeUpdate(query);
        }
    }

    static void insert(String name, int price) throws SQLException {
        try (Statement statement = DriverManager.getConnection(DATABASE_URL).createStatement()) {
            String query = "INSERT INTO PRODUCT (NAME, PRICE) VALUES \n" +
                    "(\"" + name + "\"," + price + ");";
            statement.executeUpdate(query);
        }
    }

    static String wrapResult(String res) {
        return "<html><body>\n" + res + "</body></html>\n";
    }
}
