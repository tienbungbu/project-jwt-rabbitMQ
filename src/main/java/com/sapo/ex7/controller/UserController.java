package com.sapo.ex7.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapo.ex7.entity.Role;
import com.sapo.ex7.entity.UserEntity;
import com.sapo.ex7.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user){
        return ResponseEntity.ok().body(userService.save(user));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(),form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length()); // chỉ cần mã thông báo
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // HMAC256 chọn mảng byte
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserEntity user =  userService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername()) // có thể là id,username,...
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // đặt 10 phút cho ng dùng nhập token
                        .withIssuer(request.getRequestURL().toString()) //tên cty hoặc tác giả của mã token này
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception e){

                response.setHeader("error",e.getMessage());
//                    response.sendError(FORBIDDEN.value());//403 = không có quyền truy cập
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}
