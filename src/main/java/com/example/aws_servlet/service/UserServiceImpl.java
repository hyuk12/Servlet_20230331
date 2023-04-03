package com.example.aws_servlet.service;

import com.example.aws_servlet.entity.User;
import com.example.aws_servlet.repository.UserRepository;
import com.example.aws_servlet.repository.UserRepositoryImpl;

public class UserServiceImpl implements UserService{
    // Service 객체 싱글톤 정의
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserRepository userRepository;

    private UserServiceImpl() {
        // Repository 객체 DI(Dependency Injection): 의존관계 주입
        userRepository = UserRepositoryImpl.getInstance();
    }

    @Override
    public int addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean duplicatedUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }
}
