//package org.example.dormtaskmanagerapi.config;
//
//import org.example.dormtaskmanagerapi.entity.AuthUser;
//import org.example.dormtaskmanagerapi.entity.User;
//import org.example.dormtaskmanagerapi.entity.repository.AuthUserRepository;
//import org.example.dormtaskmanagerapi.security.Role;
//import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class DataInitializer {
//    @Bean
//    CommandLineRunner initUsers(UserRepository userRepository,
//                                PasswordEncoder passwordEncoder,
//                                AuthUserRepository authUserRepository) {
//        return args -> {
//
//            AuthUser adminAuth = new AuthUser();
//            adminAuth.setUsername("admin");
//            adminAuth.setPassword(passwordEncoder.encode("admin123"));
//            adminAuth.setRole(Role.ADMIN);
//            authUserRepository.save(adminAuth);
//
//            User admin = new User();
//            admin.setName("Admin User");
//            admin.setAuthUser(adminAuth);
//            userRepository.save(admin);
//
//        };
//    }
//}
