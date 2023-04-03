package com.example.aws_servlet.service;

import com.example.aws_servlet.entity.User;

public interface UserService {
    public int addUser(User user);
    public User getUser(String username);
    public boolean duplicatedUsername(String username);
}
