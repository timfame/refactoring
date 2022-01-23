package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface Service {
    List<Product> GetProducts();
    void AddProduct(String name, int price);
    Product QueryProduct(Command command);
    long QueryLong(Command command);
}
