package ru.geekbrains.spring.market;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {

    private int totalCost;
    private int totalQuantity;

    public CartDto(int totalCost, int totalQuantity){
        this.totalCost = totalCost;
        this.totalQuantity = totalQuantity;
    }

    public CartDto(Cart cart){
        this.totalCost = cart.getTotalCost();
        this.totalQuantity = cart.getTotalQuantity();
    }
}
