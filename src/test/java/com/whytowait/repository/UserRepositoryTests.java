package com.whytowait.repository;

import com.whytowait.domain.models.User;
import com.whytowait.domain.models.enums.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    public UserRepository userRepository;

    @Test
    public void testUserCreation(){
        User testUser = User.builder().firstName("Test").lastName("User").email("testuser@test.com").mobile("+919822451252").role(UserRole.CUSTOMER).build();
        System.out.println(testUser);
        User testUserFromRepository = userRepository.save(testUser);
        Assertions.assertNotNull(testUserFromRepository);
    }
}
