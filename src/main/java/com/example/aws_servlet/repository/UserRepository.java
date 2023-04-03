package com.example.aws_servlet.repository;

import com.example.aws_servlet.entity.User;

public interface UserRepository {
    public int save(User user);
    public User findUserByUsername(String username);

}
