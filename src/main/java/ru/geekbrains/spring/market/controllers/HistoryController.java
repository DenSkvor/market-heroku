package ru.geekbrains.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.market.Cart;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/history")
@AllArgsConstructor
public class HistoryController {

    private History history;

    @GetMapping
    public String showHistory(){
        history.getSessionHistory().add("/history");
        return "history_aop";
    }

}
