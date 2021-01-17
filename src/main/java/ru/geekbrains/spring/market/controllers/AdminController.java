package ru.geekbrains.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.History;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.models.ClientRole;
import ru.geekbrains.spring.market.models.Role;
import ru.geekbrains.spring.market.services.ClientRoleService;
import ru.geekbrains.spring.market.services.ClientService;
import ru.geekbrains.spring.market.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private ClientService clientService;
    private ClientRoleService clientRoleService;
    private RoleService roleService;
    private History history;


    @GetMapping
    public String showAdminPage(Model model){
        List<ClientRole> clientRoles = clientRoleService.findAll();
        model.addAttribute("clientRoles", clientRoles);

        history.getSessionHistory().add("/admin");

        return "admin";
    }
    //изменение прав доступа клиентов (блокировка клиентов)
    @GetMapping("/change_role")
    public String blockClient(@RequestParam Long id,
                              @RequestParam String newRoleName){
        Role newRole = roleService.findByName(newRoleName);

        ClientRole cr = clientRoleService.upd(id, newRole);

        history.getSessionHistory().add("/admin/change_role for " +
                "[" + clientRoleService.findById(id).getClient().getName() + " - " + clientRoleService.findById(id).getRole().getName() + "]");

        history.getSessionHistory().add("/admin/change_role to " +
                "[" + cr.getClient().getName() + " - " + cr.getRole().getName() + "]");

        return "redirect:/admin";
    }
}
