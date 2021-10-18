package com.sapo.ex7.service;

import com.sapo.ex7.entity.Role;
import com.sapo.ex7.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity save(UserEntity entity);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserEntity getUser(String username);
    List<UserEntity> getUsers();
}
