package org.example.springapp.errors;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Категория: " + name + " не найдена.");
    }
}
