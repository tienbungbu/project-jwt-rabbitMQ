package com.sapo.ex7;

import com.sapo.ex7.entity.UserEntity;
import com.sapo.ex7.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Ex7Application {

    public static void main(String[] args) {
        SpringApplication.run(Ex7Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null,"ROLE_USER"));
//            userService.saveRole(new Role(null,"ROLE_ADMIN"));
//            userService.saveRole(new Role(null,"ROLE_MANAGER"));
//            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//
//            userService.save(new UserEntity(null,"Trịnh Văn Tiến","tienbungbu","1234",new ArrayList<>()));
//            userService.save(new UserEntity(null,"Đặng Thanh Tâm","thanhtam","123",new ArrayList<>()));
//            userService.save(new UserEntity(null,"Trịnh Gia Quyến","quyen19569","123",new ArrayList<>()));
//            userService.save(new UserEntity(null,"Hoàng Thị Dựng","dunghoang10573","123",new ArrayList<>()));
//
//            userService.addRoleToUser("tienbungbu", "ROLE_ADMIN");
//            userService.addRoleToUser("thanhtam", "ROLE_USER");
//            userService.addRoleToUser("quyen19569", "ROLE_SUPER_ADMIN");
//            userService.addRoleToUser("dunghoang10573", "ROLE_MANAGER");
//        };
//    }

}
