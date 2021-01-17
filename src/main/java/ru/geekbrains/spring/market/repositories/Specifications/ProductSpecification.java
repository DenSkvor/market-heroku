package ru.geekbrains.spring.market.repositories.Specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.market.models.Product;

public class ProductSpecification {

    public static Specification<Product> findByPriceGTE(Integer minPrice){
        return (Specification<Product>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> findByPriceLTE(Integer maxPrice){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> findByPriceBetween(Integer minPrice, Integer maxPrice){
        return (Specification<Product>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Product> findByTitleLike(String title){
        return (Specification<Product>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Product> findByCategory(Integer id){
        return (Specification<Product>)(root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), id);
    }
}
