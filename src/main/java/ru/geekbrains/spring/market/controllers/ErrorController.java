package ru.geekbrains.spring.market.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ProductNotFoundException.class)
    public String productNotFoundException(Exception exp, Model model){
        model.addAttribute("exp", exp != null ? exp.getMessage() : "Что-то пошло не так.");
        return "error";
    }
}
