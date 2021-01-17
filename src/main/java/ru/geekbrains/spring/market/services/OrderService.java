package ru.geekbrains.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.dto.OrderDto;
import ru.geekbrains.spring.market.models.Order;
import ru.geekbrains.spring.market.repositories.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;


    public void addOrder(Order order){
        orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

}
