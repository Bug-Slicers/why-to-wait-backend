package com.whytowait.api.v1.services;

import com.whytowait.domain.models.HashedPassword;
import com.whytowait.domain.models.User;
import com.whytowait.repository.HashedPasswordRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HashedPasswordService {

    @Autowired
    HashedPasswordRepository hashedPasswordRepository;

    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Transactional(propagation = Propagation.REQUIRED)
    void createHashedPassword(User user, String password) {
        UUID userId = user.getId();
        password = hashPassword(password);
        HashedPassword hashedPassword = HashedPassword.builder().userId(userId).hashedPassword(password).build();
        hashedPasswordRepository.save(hashedPassword);
    }

    boolean checkPassword(UUID userId, String password) {
        HashedPassword hashedPassword = hashedPasswordRepository.findByUserId(userId);
        return compareHashedPassword(password, hashedPassword.getHashedPassword());
    }

    private boolean compareHashedPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
