package ru.geekbrains.spring.market.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.spring.market.models.Category;
import ru.geekbrains.spring.market.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTest {


    private CategoryRepository categoryRepository;


    public void setUp(){
        categoryRepository = Mockito.mock(CategoryRepository.class);
    }

    @Test
    public void checkGetCategories(){

        Category categoryOne = new Category();
        categoryOne.setTitle("Категория_1");

        Category categoryTwo = new Category();
        categoryTwo.setTitle("Категория_2");

        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(categoryOne);
        expectedCategories.add(categoryTwo);

        Mockito.when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryRepository.findAll();

        Assertions.assertNotNull(actualCategories);
        Assertions.assertEquals(expectedCategories, actualCategories);

    }

}