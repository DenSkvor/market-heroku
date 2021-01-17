package ru.geekbrains.spring.market.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.market.Cart;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.models.Order;
import ru.geekbrains.spring.market.models.OrderItem;
import ru.geekbrains.spring.market.models.Product;
import ru.geekbrains.spring.market.services.ClientService;
import ru.geekbrains.spring.market.services.OrderService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private Cart cart;
    @MockBean
    private ClientService clientService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private History history;

    private String phoneNumber = "1234567";
    private String address = "в деревню к дедушке";

    private static final String USER_NAME = "user1";
    private static final String PASSWORD = "123";
    private Order order;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setName(USER_NAME);
        Mockito.when(clientService.findByName(USER_NAME)).thenReturn(client);

        Integer totalCost = 999;
        Mockito.when(cart.getTotalCost()).thenReturn(totalCost);

        ArrayList<OrderItem> container = new ArrayList<>();
        Product product = new Product();
        product.setTitle("Гарнитура");
        product.setPrice(3000);
        container.add(new OrderItem(product));
        Mockito.when(cart.getContainer()).thenReturn(container);

        order = new Order();
        order.setClient(client);
        order.setPhoneNumber(phoneNumber);
        order.setAddress(address);
        order.setPrice(totalCost);
        order.setOrderItems(container);

        //todo добавить в сущности билдеры

    }

    @WithMockUser(username = USER_NAME, password = PASSWORD)
    @Test
    public void checkConfirmOrder() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/order/confirm")
                        .param("phNum", phoneNumber)
                        .param("address", address)
                        .accept(MediaType.TEXT_HTML))
                ;
        verify(orderService).addOrder(order);
        verify(cart).clear();
    }

    //код тестируемого метода

    /*
    @Controller
    @RequestMapping("/order")


    @PostMapping("/confirm")
    public String confirmOrder(Principal principal,
                               @RequestParam(name = "phNum") String phoneNumber,
                               @RequestParam String address){

        Client client = clientService.findByName(principal.getName());
        Integer totalCost = cart.getTotalCost();
        List<OrderItem> orderItems = cart.getContainer();
        Order order = new Order(client, phoneNumber, address, totalCost, orderItems);

        orderService.addOrder(order);
        orderService.saveInFile(order);

        cart.clear();

        return "redirect:/products";
}
     */

}