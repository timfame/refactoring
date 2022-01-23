package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ServiceImpl implements Service {
    private final ProductDao productDao;

    public ServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> GetProducts() {
        try {
            return productDao.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void AddProduct(String name, int price) {
        try {
            productDao.insertProduct(new Product(name, price));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product QueryProduct(Command command) {
        try {
            switch (command) {
                case MIN:
                    return productDao.getProductWithMinPrice();
                case MAX:
                    return productDao.getProductWithMaxPrice();
                default:
                    throw new RuntimeException("Wrong command for QueryProduct");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long QueryLong(Command command) {
        try {
            switch (command) {
                case SUM:
                    return productDao.getPriceSum();
                case COUNT:
                    return productDao.getCount();
                default:
                    throw new RuntimeException("Wrong command for QueryInt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
