package ru.geekbrains.spring.market.exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "ProductNotFoundException. Не удалось найти продукт в базе. Проверьте ID.";
    }
}
