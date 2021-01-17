package ru.geekbrains.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.models.ClientRole;
import ru.geekbrains.spring.market.models.Role;
import ru.geekbrains.spring.market.repositories.ClientRoleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientRoleService {

    private ClientRoleRepository clientRoleRepository;

    public List<ClientRole> findAll(){
        return clientRoleRepository.findAll();
    }

    public ClientRole findById(Long id){
        return clientRoleRepository.findById(id).get();//todo переделать на optional
    }

    public void add(Client client, Role role){
        clientRoleRepository.save(new ClientRole(client, role));
    }

    public ClientRole upd(Long id, Role newRole){
        ClientRole cr = new ClientRole(id, findById(id).getClient(), newRole);
        clientRoleRepository.save(cr);
        return cr;
    }
}
