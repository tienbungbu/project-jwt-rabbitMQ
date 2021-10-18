package com.sapo.ex7.service.imp;

import com.sapo.ex7.entity.Role;
import com.sapo.ex7.entity.UserEntity;
import com.sapo.ex7.repository.RoleRepository;
import com.sapo.ex7.repository.UserRepository;
import com.sapo.ex7.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            log.error("User not found in the database");
            throw  new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(),userEntity.getPassword(),authorities);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        log.info("Saving new user {} to the database",entity.getName());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to username {} ",roleName,username);
        UserEntity entity = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        entity.getRoles().add(role);

    }

    @Override
    public UserEntity getUser(String username) {
        log.info("Fetching user {}",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
