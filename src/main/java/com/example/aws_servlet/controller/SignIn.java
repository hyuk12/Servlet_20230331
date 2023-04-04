package com.example.aws_servlet.controller;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/signin")
public class SignIn extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;
    private Gson gson;

    public SignIn() {
        userService = UserServiceImpl.getInstance();
        gson = new Gson();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.getUser(username);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        if(user == null) {
            // 로그인 실패 1(아이디 찾을 수 없음)
            ResponseDto<Boolean> responseDto =
                    new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
            out.println(gson.toJson(responseDto));
            return;
        }

        if(!user.getPassword().equals(password)) {
            // 로그인 실패 2(비밀번호 틀림)
            ResponseDto<Boolean> responseDto =
                    new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
            out.println(gson.toJson(responseDto));
            return;
        }

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute("AuthenticationPrincipal", user.getUserId());
        System.out.println("실행?");
        ResponseDto<Boolean> responseDto =
                new ResponseDto<Boolean>(200, "사용자 인증 성공", true);
        out.println(gson.toJson(responseDto));
    }

}

