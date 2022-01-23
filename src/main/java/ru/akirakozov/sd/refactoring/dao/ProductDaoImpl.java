package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.connection.ConnectionProvider;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private final ConnectionProvider connectionProvider;

    public ProductDaoImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)";
                stmt.executeUpdate(sql);
            }
        }
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?);";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setInt(2, product.getPrice());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "SELECT * FROM PRODUCT";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        products.add(new Product(name, price));
                    }
                }
            }
        }
        return products;
    }

    @Override
    public Product getProductWithMinPrice() throws SQLException {
        return getProductBySQL("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    @Override
    public Product getProductWithMaxPrice() throws SQLException {
        return getProductBySQL("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    @Override
    public int getPriceSum() throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "SELECT SUM(price) FROM PRODUCT";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public long getCount() throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                String sql = "SELECT COUNT(*) FROM PRODUCT";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return 0;
    }

    private Product getProductBySQL(String sql) throws SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        return new Product(name, price);
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
