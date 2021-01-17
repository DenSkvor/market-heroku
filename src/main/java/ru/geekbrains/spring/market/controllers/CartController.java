package ru.geekbrains.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Cart;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private ProductService productService;
    private Cart cart;
    private History history;

    @GetMapping
    public String showCart(){
        history.getSessionHistory().add("/cart");
        return "cart";
    }

    @GetMapping("/add")
    public void addProduct(@RequestParam Long id,
                           HttpServletRequest request, HttpServletResponse response) throws ProductNotFoundException, IOException {
        Product product = productService.getProductById(id);
        cart.add(product);

        history.getSessionHistory().add("/cart/add [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/del")
    public String delProduct(@RequestParam Long id) throws ProductNotFoundException {
        cart.delete(id);

        Product product = productService.getProductById(id);

        history.getSessionHistory().add("/cart/del [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "cart";
    }

    @GetMapping("/incr")
    public String incrementProduct(@RequestParam Long id) throws ProductNotFoundException {
        cart.add(id);

        Product product = productService.getProductById(id);

        history.getSessionHistory().add("/cart/add [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "cart";
    }

    @GetMapping("/decr")
    public String decrementProduct(@RequestParam Long id) throws ProductNotFoundException {
        cart.remove(id);

        Product product = productService.getProductById(id);

        history.getSessionHistory().add("/cart/decr [" + "id: " + product.getId() + " title: " + product.getTitle() + " price: " + product.getPrice() + "]");

        return "cart";
    }


}
