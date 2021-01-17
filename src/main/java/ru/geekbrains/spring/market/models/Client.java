package ru.geekbrains.spring.market.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "client_tbl")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;
/*
    @ManyToMany
    @JoinTable(name = "client_role_tbl",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;
*/

    @OneToMany(mappedBy = "client")
    private List<ClientRole> clientRoles;


    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
