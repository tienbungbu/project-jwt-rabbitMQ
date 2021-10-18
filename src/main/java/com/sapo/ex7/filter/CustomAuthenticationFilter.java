package com.sapo.ex7.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager; // gọi trình quản lí xác thực
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}",username);
        log.info("Password is: {}",password);
        // tạo 1 obj token xác thực password and username
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        // trả về trình quan lý xác thực và sau đó chuyển nó vào token xác thực
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //userditails
        User user = (User) authResult.getPrincipal(); // trả về 1 obj là ng dùng đã đc xác thực thành công
        // lấy thông tin đăng nhập đó để tạo mã JWT
        // vì vậy hãy xác định thuật toán
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // HMAC256 chọn mảng byte
        String access_token = JWT.create()
                .withSubject(user.getUsername()) // có thể là id,username,...
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // đặt 10 phút cho ng dùng nhập token
                .withIssuer(request.getRequestURL().toString()) //tên cty hoặc tác giả của mã token này
                .withClaim("roles",user.getAuthorities() //withClaim == với yêu cầu + key = roles == danh sách các role
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername()) // có thể là id,username,...
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // đặt 10 phút cho ng dùng nhập token
                .withIssuer(request.getRequestURL().toString()) //tên cty hoặc tác giả của mã token này
                .sign(algorithm);

//        response.setHeader("access_token",access_token);
//        response.setHeader("refresh_token",refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);


    }
}
