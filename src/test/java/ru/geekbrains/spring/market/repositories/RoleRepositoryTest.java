package ru.geekbrains.spring.market.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.geekbrains.spring.market.models.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void checkFindByName() {

        Role role = new Role();
        role.setName("TestRole");

        entityManager.persist(role);

        Optional<Role> actualRoleOpt = roleRepository.findByName("TestRole");

        Assertions.assertNotNull(actualRoleOpt.get());
        Assertions.assertEquals(role.getName(), actualRoleOpt.get().getName());

    }
}