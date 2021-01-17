package ru.geekbrains.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.geekbrains.spring.market.Cart;
import ru.geekbrains.spring.market.ProductDto;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.services.ProductService;


@Controller
@AllArgsConstructor
public class WebSocketController {

    private ProductService productService;
    private Cart cart;
    private SimpMessagingTemplate template;

    @MessageMapping("/add")
    public void addProductToCart(ProductDto pd) throws ProductNotFoundException {
        Product product = productService.getProductById(pd.getId());
        cart.add(product);
    }
}

