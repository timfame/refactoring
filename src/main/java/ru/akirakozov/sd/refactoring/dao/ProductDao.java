package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    void createTable() throws SQLException;
    void insertProduct(Product product) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    Product getProductWithMinPrice() throws SQLException;
    Product getProductWithMaxPrice() throws SQLException;
    int getPriceSum() throws SQLException;
    long getCount() throws SQLException;
}
