package com.example.aws_servlet.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "hello", urlPatterns = {"/hello"})
public class Hello extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String message;
    public Hello() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("최초 1회만 실행합니다.");
        message = "Hello";
    }

    @Override
    public void destroy() {
        System.out.println("마지막으로 한번만 실행됩니다.");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("요청이 들어올 때 마다 호출된다.");
        System.out.println(req.getMethod());
        System.out.println(req.getRequestURL());
        // Hello
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
}
