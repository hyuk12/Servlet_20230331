package com.example.aws_servlet.controller;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet(name = "userInfo", urlPatterns = {"/user"})
public class UserInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.addHeader("test", "test data");
        Gson gson = new Gson();
        resp.setContentType("application/json;charset=UTF-8");
//        resp.setContentType("text/html;charset=UTF-8");
//        resp.setCharacterEncoding("UTF-8");

        System.out.println("GET요청");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("password", phone);

        String userJson = gson.toJson(userMap);
        System.out.println(userJson);
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println("<h1>User Info</h1>");
//        out.println("<p>name: " + name + "</p>");
//        out.println("<p>password: " + phone + "</p>");
        out.println(userJson);
        out.println("</body>");
        out.println("</html>");
        /**
         *  1.  주소창,  src, href, replace 등으로 요청가능하다.
         *  2. 데이터를 전달하는 방법 (Query String)  http(s)://서버주소:포트/요청메세지?key=value&key=value
         */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        System.out.println("POST요청");

        Gson gson = new Gson();
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        String s = json.replaceAll(" ", "");
//        String json = reader.lines().collect(Collectors.joining());
        System.out.println(s);

        Map jsonMap = gson.fromJson(s, Map.class);
        System.out.println(jsonMap);
//        System.out.println(req.getParameter("name"));
//        System.out.println(req.getParameter("phone"));
        /**
         *  <form action="/user" method="post" >
         *      <input type="text" name="name">
         *      <input type="text" name="phone">
         *      <button type="submit">Submit</button>
         *  </form>
         */



    }
}
