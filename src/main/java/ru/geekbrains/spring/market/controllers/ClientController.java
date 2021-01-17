package ru.geekbrains.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.services.ClientService;

import java.security.Principal;

@Controller
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    History history;

    @GetMapping
    public String showClientInfo(Principal principal,
                                   Model model){
        Client client = clientService.findByName(principal.getName());
        model.addAttribute("name", client.getName());
        model.addAttribute("phoneNumber", client.getPhoneNumber());
        model.addAttribute("email", client.getEmail());

        history.getSessionHistory().add("/client");

        return "client";
    }

    @GetMapping("/upd")
    public String showUpdClientInfo(Principal principal,
                                 Model model){
        Client client = clientService.findByName(principal.getName());
        model.addAttribute("name", client.getName());
        model.addAttribute("phoneNumber", client.getPhoneNumber());
        model.addAttribute("email", client.getEmail());

        history.getSessionHistory().add("/client/upd for [" + "name: " + client.getName() + " phoneNumber: " + client.getPhoneNumber() + " email: " + client.getEmail() + "]");

        return "upd_client";
    }

    @PostMapping("/upd")
    public String updClientInfo(Principal principal,
                                @RequestParam(required = false) String newName,
                                @RequestParam(name = "newPhNum", required = false) String newPhoneNumber,
                                @RequestParam(required = false) String newEmail){
        Client client = clientService.updClient(principal.getName(), newName, newPhoneNumber, newEmail);

        history.getSessionHistory().add("/client/to [" + "name: " + client.getName() + " phoneNumber: " + client.getPhoneNumber() + " email: " + client.getEmail() + "]");

        return "redirect:/client";
    }
}
