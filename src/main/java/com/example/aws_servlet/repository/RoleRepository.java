package com.example.aws_servlet.repository;

import com.example.aws_servlet.entity.Role;

public interface RoleRepository {
    public Role findRoleByRoleName(String roleName);
}
