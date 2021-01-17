package ru.geekbrains.spring.market.dto;

import lombok.Data;
import ru.geekbrains.spring.market.models.Order;
import ru.geekbrains.spring.market.models.OrderItem;
import ru.geekbrains.spring.market.models.Product;

import javax.persistence.*;

@Data
public class OrderItemDto {

    private Long id;

    private String product;

    private Integer quantity;

    private Integer productPrice;

    private Integer totalCost;

    public OrderItemDto(OrderItem orderItem){
        this.id = orderItem.getId();
        this.product = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.productPrice = orderItem.getProductPrice();
        this.totalCost = orderItem.getTotalCost();
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", productPrice=" + productPrice +
                ", totalCost=" + totalCost +
                '}';
    }
}
