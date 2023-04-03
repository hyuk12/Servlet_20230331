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

@WebServlet(urlPatterns = {"/auth/signin"})
public class SignIn extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;
    private Gson gson;

    public SignIn () {
        userService = UserServiceImpl.getInstance();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.getUser(username);
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        resp.setContentType("application/json; charset=utf-8");
        if (user != null && user.getPassword().equals(password)) {
            ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(200, "사용자 인증 성공", true);
            resp.getWriter().write(gson.toJson(responseDto));
        } else {
            HttpSession session = req.getSession();
            assert user != null;
            session.setAttribute("AuthenticationPrincipal", user.getUserId());

            ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>(400, "사용자 인증 실패", false);
            resp.getWriter().write(gson.toJson(responseDto));
        }
    }
}
