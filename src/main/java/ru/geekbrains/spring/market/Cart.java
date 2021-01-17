package ru.geekbrains.spring.market;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.spring.market.models.OrderItem;
import ru.geekbrains.spring.market.models.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class Cart {

    private List<OrderItem> container;
    @Value("0")
    private Integer totalCost;
    @Value("0")
    private Integer totalQuantity;

    private SimpMessagingTemplate template;
    @Autowired
    public void setTemplate(SimpMessagingTemplate template){
        this.template = template;
    }

    @PostConstruct
    private void init(){
        container = new ArrayList<>();
    }

    public void add(Product p){
        for (OrderItem oi : container) {
            if(oi.getProduct().getId().equals(p.getId())){
                oi.incrementQuantity();
                //calcTotalCost();
                totalCost += oi.getProductPrice();
                totalQuantity ++;

                template.convertAndSend("/topic/products", new CartDto(this));

                return;
            }
        }
        container.add(new OrderItem(p));
        //calcTotalCost();
        totalCost += p.getPrice();
        totalQuantity ++;

        template.convertAndSend("/topic/products", new CartDto(this));
    }

    public void add(Long id){
        for (OrderItem oi : container) {
            if(oi.getProduct().getId().equals(id)){
                oi.incrementQuantity();
                //calcTotalCost();
                totalCost += oi.getProductPrice();
                totalQuantity ++;
                return;
            }
        }
    }

    public void remove(Long id){
        Iterator<OrderItem> iter = container.iterator();
        while (iter.hasNext()){
            OrderItem item = iter.next();
            if(item.getProduct().getId().equals(id)) {
                item.decrementQuantity();
                totalCost -= item.getProductPrice();
                totalQuantity --;
                if(item.getQuantity() == 0) iter.remove();
                //calcTotalCost();
                return;
            }
        }
    }

    public void delete(Long id){
        Iterator<OrderItem> iter = container.iterator();
        while (iter.hasNext()){
            OrderItem item = iter.next();
            if(item.getProduct().getId().equals(id)) {
                iter.remove();
                //calcTotalCost();
                totalCost -= item.getTotalCost();
                totalQuantity -= item.getQuantity();
                return;
            }
        }
    }

    public void calcTotalCost(){
        totalCost = 0;
        for (OrderItem oi : container) {
            totalCost += oi.getTotalCost();
        }
    }

    public void calcTotalQuantity(){
        totalQuantity = 0;
        for (OrderItem oi : container) {
            totalQuantity += oi.getQuantity();
        }
    }

    public void clear(){
        totalCost = 0;
        totalQuantity = 0;
        container.clear();
    }

}
