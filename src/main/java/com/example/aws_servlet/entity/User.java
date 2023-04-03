package com.example.aws_servlet.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private int userId;
    private String username;
    private String password;
    private String name;
    private String email;
}
