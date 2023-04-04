package com.example.aws_servlet.controller;

import com.example.aws_servlet.dto.ResponseDto;
import com.example.aws_servlet.entity.Role;
import com.example.aws_servlet.service.RoleService;
import com.example.aws_servlet.service.RoleServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * 데이터베이스에서 파라미터로 넘어온 RoleName이 존재하는지 여부 확인
 *	존재한다면 		ResponseDto Json(200, success, true)
 *	존재하지 않으면	ResponseDto Json(400, error, false)
 *
 *	RoleService
 *	RoleRepository
 *
 */

@WebServlet(urlPatterns = "/role")
public class RoleInfo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RoleService roleService;
    private Gson gson;

    public RoleInfo() {
        roleService = RoleServiceImpl.getInstance();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        System.out.println("roleName: " + roleName);

        Role role = roleService.getRole(roleName);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        if(role == null) {
            out.println(gson.toJson(new ResponseDto<>(400, "error", false)));
            return;
        }

        out.println(gson.toJson(new ResponseDto<>(200, "success", true)));

    }

}

