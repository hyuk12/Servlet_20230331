package com.example.aws_servlet.controller;

import com.example.aws_servlet.dto.RequestDto;
import com.example.aws_servlet.dto.ResponseDto;
import com.example.aws_servlet.entity.User;
import com.example.aws_servlet.service.UserService;
import com.example.aws_servlet.service.UserServiceImpl;
import com.google.gson.Gson;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/auth"})
public class Authentication extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService ;
    private Gson gson;
    public Authentication() {
        userService = UserServiceImpl.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user  = RequestDto.<User>convertRequestBody(req, User.class);
        boolean duplicatedFlag = userService.duplicatedUsername(user.getUsername());

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if(duplicatedFlag){
            // true == 중복, false == 가입가능
            ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400, "duplicated username", duplicatedFlag);
            out.println(gson.toJson(responseDto));
            return;
        }

        ResponseDto<Integer> responseDto = new ResponseDto<Integer>(201, "signup", userService.addUser(user));
        out.println(gson.toJson(responseDto));


//        Map<String, String> map = RequestDto.<Map<String, String>>convertRequestBody(req, Map.class);
//        System.out.println(user);
//        System.out.println(map);
    }
}
