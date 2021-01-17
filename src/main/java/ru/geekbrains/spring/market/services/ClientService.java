package ru.geekbrains.spring.market.services;


import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.models.Client;
import ru.geekbrains.spring.market.models.Role;
import ru.geekbrains.spring.market.repositories.ClientRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService implements UserDetailsService {

    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findByName(String name){
        return clientRepository.findByName(name).get(); //todo переделать на optional or else throw
    }

    public Client findById(Long id){
        return clientRepository.findById(id).get(); //todo переделать на optional or else throw
    }

    @Transactional
    public Client updClient(String name, String newName, String newPhoneNumber, String newEmail){
        Client client = findByName(name);

        if(newName != null && !Objects.equals(newName, client.getName())) client.setName(newName);
        if(newPhoneNumber != null && !Objects.equals(newPhoneNumber, client.getPhoneNumber())) client.setPhoneNumber(newPhoneNumber);
        if(newEmail != null && !Objects.equals(newEmail, client.getEmail())) client.setEmail(newEmail);

        return client;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = findByName(username);
        Collection<Role> roles = client.getClientRoles().stream().map(clientRole -> clientRole.getRole()).collect(Collectors.toList());
        if (client == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(client.getName(), client.getPassword(), mapRolesToAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
