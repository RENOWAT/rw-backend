package com.tfm.backend.data.daos;

import com.tfm.backend.data.entities.User;
import com.tfm.backend.data.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseStarting {

    private static final String SUPER_USER = "admin";
    private static final String MOBILE = "6";
    private static final String PASSWORD = "6";
    private static final String EMAIL = "admin@email.com";

    private final UserRepository userRepository;

    @Autowired
    public DatabaseStarting(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.initialize();
    }

    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -----------");
        if (this.userRepository.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            User user = User.builder().email(EMAIL).mobile(MOBILE).firstName(SUPER_USER)
                    .password(new BCryptPasswordEncoder().encode(PASSWORD))
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true).build();
            this.userRepository.save(user);
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -----------");
        }
    }

}
