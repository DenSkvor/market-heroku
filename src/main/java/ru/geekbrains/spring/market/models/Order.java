package ru.geekbrains.spring.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "address")
    String address;

    @Column(name = "price")
    Integer price;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems;

    public Order(Client client, String phoneNumber, String address, Integer price, List<OrderItem> orderItems){
        this.client = client;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.price = price;
        this.orderItems = orderItems;

        for (OrderItem oi:orderItems) {
            oi.setOrder(this);
        }
    }

}
