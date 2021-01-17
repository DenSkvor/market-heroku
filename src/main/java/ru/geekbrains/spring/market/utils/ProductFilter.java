package ru.geekbrains.spring.market.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.repositories.Specifications.ProductSpecification;

import java.util.Map;

@Getter
public class ProductFilter {

    private Specification<Product> spec;

    private String filter;

    private String categoryFilter;

    public ProductFilter(Map<String, String> params){
        spec = Specification.where(null);
        StringBuilder tempFilter = new StringBuilder();
        StringBuilder tempCatFilter = new StringBuilder();
        //по-хорошему вместо проверки !isEmpty нужна проверка !isBlank, но 8 версия джавы, поэтому увы.
        if(params.containsKey("minPrice") && !params.get("minPrice").isEmpty()) {
            spec = spec.and(ProductSpecification.findByPriceGTE(Integer.parseInt(params.get("minPrice"))));
            tempFilter.append("&minPrice=" + Integer.parseInt(params.get("minPrice")));

        }
        if(params.containsKey("maxPrice") && !params.get("maxPrice").isEmpty()) {
            spec = spec.and(ProductSpecification.findByPriceLTE(Integer.parseInt(params.get("maxPrice"))));
            tempFilter.append("&maxPrice=" + Integer.parseInt(params.get("maxPrice")));
        }
        if(params.containsKey("title") && !params.get("title").isEmpty()) {
            spec = spec.and(ProductSpecification.findByTitleLike(params.get("title")));
            tempFilter.append("&title=" + params.get("title"));
        }
        if(params.containsKey("category") && !params.get("category").isEmpty()) {
            spec = spec.and(ProductSpecification.findByCategory(Integer.parseInt(params.get("category"))));
            tempCatFilter.append("&category=" + params.get("category"));
        }
        filter = tempFilter.toString();
        categoryFilter = tempCatFilter.toString();
    }
}
