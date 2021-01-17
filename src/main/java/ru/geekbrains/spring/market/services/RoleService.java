package ru.geekbrains.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.models.Role;
import ru.geekbrains.spring.market.repositories.RoleRepository;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name).get(); //todo optional
    }
}
