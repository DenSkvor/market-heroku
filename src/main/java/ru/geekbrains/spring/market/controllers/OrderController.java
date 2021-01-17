package ru.geekbrains.spring.market.controllers;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.market.Cart;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.models.Order;
import ru.geekbrains.spring.market.models.OrderItem;
import ru.geekbrains.spring.market.services.ClientService;
import ru.geekbrains.spring.market.services.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private Cart cart;
    private ClientService clientService;
    private OrderService orderService;
    private History history;

    @GetMapping
    public String showOrderForm(){
        history.getSessionHistory().add("/oder");
        return "order_form";
    }

    @PostMapping
    public String showOrderForm(Model model,
                                @RequestParam(name = "phNum", required = false) String phoneNumber,
                                @RequestParam(required = false) String address){
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("address", address);

        history.getSessionHistory().add("/oder [phoneNumber: " + phoneNumber + " address: " + address + "]");

        return "order_form";
    }

    @PostMapping("/show")
    public String showOrder(Model model,
                            @RequestParam(name = "phNum") String phoneNumber,
                            @RequestParam String address){
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("address", address);

        history.getSessionHistory().add("/oder/show [phoneNumber: " + phoneNumber + " address: " + address + "]");

        return "order";
    }

    @PostMapping("/confirm")
    public String confirmOrder(Principal principal,
                               @RequestParam(name = "phNum") String phoneNumber,
                               @RequestParam String address){

        Client client = clientService.findByName(principal.getName());
        Integer totalCost = cart.getTotalCost();
        List<OrderItem> orderItems = cart.getContainer();
        Order order = new Order(client, phoneNumber, address, totalCost, orderItems);

        orderService.addOrder(order);
/*
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (OrderItem oi : orderItems) {
            sb
                    .append("(product: ").append(oi.getProduct().getTitle())
                    .append(" quantity: ").append(oi.getQuantity()).append(")");
        }
        sb.append("}");
        String products = sb.toString();
        history.getSessionHistory().add("/oder/confirm " +
                "[name: " + client.getName() + " phoneNumber: " + phoneNumber + " address: " + address + products + " totalCost: " + totalCost + "]");
*/
        cart.clear();

        return "redirect:/products";
    }
}
