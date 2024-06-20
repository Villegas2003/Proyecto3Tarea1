package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
        this.createDefaultUser();
    }
    private void createSuperAdministrator() {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        if (optionalRole.isEmpty()) {
            return;
        }

        String adminEmail = "super.admin@gmail.com";
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            return;
        }

        User superAdmin = new User();
        superAdmin.setName("Super");
        superAdmin.setLastname("Admin");
        superAdmin.setEmail(adminEmail);
        superAdmin.setPassword(passwordEncoder.encode("superadmin123"));
        superAdmin.setRole(optionalRole.get());

        userRepository.save(superAdmin);
    }

    private void createDefaultUser() {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            return;
        }

        String userEmail = "user@example.com";
        if (userRepository.findByEmail(userEmail).isPresent()) {
            return;
        }

        User user = new User();
        user.setName("User");
        user.setLastname("User");
        user.setEmail(userEmail);
        user.setPassword(passwordEncoder.encode("userpassword"));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}
