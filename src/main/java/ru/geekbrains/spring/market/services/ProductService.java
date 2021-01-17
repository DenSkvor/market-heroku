package ru.geekbrains.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.models.Category;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.repositories.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public Page<Product> getProducts(Specification<Product> spec, Integer pageNum, Integer pageSize){
        return productRepository.findAll(spec, PageRequest.of(pageNum, pageSize));
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Попытка обратиться к товару с ID - " + id));
    }

    @Transactional
    public Product updProduct(Long id, String title, Integer price, Category category) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Попытка обратиться к товару с ID - " + id));
        if (title != null && !title.isEmpty()) product.setTitle(title);
        if (price != null) product.setPrice(price);
        if (category != null) product.setCategory(category);
        return product;
    }

}
