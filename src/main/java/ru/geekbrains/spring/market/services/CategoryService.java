package ru.geekbrains.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.exceptions.CategoryNotFoundException;
import ru.geekbrains.spring.market.models.Category;
import ru.geekbrains.spring.market.repositories.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategorydById(Integer id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Попытка обратиться к категории с ID - " + id));
    }
}
