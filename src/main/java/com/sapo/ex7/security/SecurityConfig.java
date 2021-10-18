package com.sapo.ex7.security;

import com.sapo.ex7.filter.CustomAuthenticationFilter;
import com.sapo.ex7.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable(); // làm vô hiệu hóa giả mạo yêu cầu trên nh trang web
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // không trạng thái
        http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**").permitAll(); // URL ko cần phải xác thực
        http.authorizeRequests().antMatchers(GET,"/api/users").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/admin/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/admin/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/admin/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated(); //bt khi gõ thằng này thì mọi request đều phải bắt xác thực.. mà nãy mk vào nó vào luôn m
//        http.formLogin().loginPage("/login").loginProcessingUrl("/login")
//                .failureForwardUrl("/login")
//                .defaultSuccessUrl("/home",true)
//                .and().logout().logoutSuccessUrl("/login");

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_SUPER_ADMIN").anyRequest().authenticated() // phải xác thực
//                .antMatchers("/users/**").hasAnyRole("ROLE_USER").anyRequest().permitAll() // không cần phải xác thực
//                // cấu hình giao diện
//                .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
//                .failureForwardUrl("/login") // đăng nhập sai về lại url login
//                .defaultSuccessUrl("/users",true) // đăng nhập đúng
//                .and().logout().logoutSuccessUrl("/login")
//
//                // khi user truy cap trai phep
//                .and().exceptionHandling().accessDeniedPage("/access-deny");
//
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
