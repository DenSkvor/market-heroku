package ru.geekbrains.spring.market.controllers;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.exceptions.CategoryNotFoundException;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.services.CategoryService;
import ru.geekbrains.spring.market.services.ProductService;
import ru.geekbrains.spring.market.utils.ProductFilter;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private History history;

    public static String responseUrl;


    @GetMapping
    public String showProducts(Principal principal,
                               Model model,
                               HttpServletRequest request,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                               @RequestParam(required = false) Map<String, String> params
                               ){
        if(pageNum < 0) pageNum = 1;
        if(pageSize < 1) pageSize = 5;

        ProductFilter pf = new ProductFilter(params);

        String clientName = null;
        if(principal != null) clientName = principal.getName();

        responseUrl = "http://localhost:" + request.getHeader("fromPort");

        model.addAttribute("clientName", clientName);
        model.addAttribute("products", productService.getProducts(pf.getSpec(), pageNum - 1, pageSize));
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("productFilter", pf.getFilter());
        model.addAttribute("productCategoryFilter", pf.getCategoryFilter());
        model.addAttribute("responseUrl", responseUrl);

        history.getSessionHistory().add("/products");

        return "products";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model){
        model.addAttribute("categories", categoryService.getCategories());

        history.getSessionHistory().add("/products/add");

        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam() String title,
                             @RequestParam() Integer price,
                             @RequestParam() Integer categoryID) throws CategoryNotFoundException {

        Product product = new Product(title, price, categoryService.getCategorydById(categoryID));
        productService.addProduct(product);

        history.getSessionHistory().add("/products/add [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "redirect:/products";
    }

    @GetMapping("/upd")
    public String showUpdProductForm(Model model,
                                     @RequestParam(required = false) Long id) throws ProductNotFoundException {
        if(id == null) return "redirect:/products";
        Product product = productService.getProductById(id);
        model.addAttribute("updProduct", product);
        model.addAttribute("categories", categoryService.getCategories());

        history.getSessionHistory().add("/products/upd for [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "upd_product";
    }

    @PostMapping("/upd/{id}")
    public String updProduct(@PathVariable() Long id,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) Integer price,
                             @RequestParam(required = false) Integer categoryID) throws ProductNotFoundException, CategoryNotFoundException {
        Product product = productService.updProduct(id, title, price, categoryService.getCategorydById(categoryID));

        history.getSessionHistory().add("/products/upd to [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "redirect:/products";
    }


}
