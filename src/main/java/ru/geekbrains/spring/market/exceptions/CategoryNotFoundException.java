package ru.geekbrains.spring.market.exceptions;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CategoryNotFoundException. Не удалось найти категорию в базе. Проверьте ID.";
    }
}
