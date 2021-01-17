package ru.geekbrains.spring.market.dto;

import lombok.Data;
import ru.geekbrains.spring.market.models.Order;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {


    private Long id;

    private String client;

    private String phoneNumber;

    private String address;

    private Integer price;

    private List<OrderItemDto> orderItems;

    public OrderDto(Order order){
        this.id = order.getId();
        this.client = order.getClient().getName();
        this.phoneNumber = order.getPhoneNumber();
        this.address = order.getAddress();
        this.price = order.getPrice();
        this.orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", orderItems=" + orderItems +
                '}';
    }
}
