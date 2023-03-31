package com.example.aws_servlet.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "carInfo" , urlPatterns = {"/car"})
public class CarInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        // Map<String, String> 을 가지고 있다.
        // Map : id, model 을 키값으로 가지고 있다. {id : id, model : model}
        // parameter 는 id 값으로 꺼낸다.
        // 요청 데이터는 id 다.
        // 응답 데이터 {"id" : "123", "model" : "BMW"}
        Map<String, String> carInfo = new HashMap<>();
        carInfo.put("1", "소나타");
        carInfo.put("2", "K5");
        carInfo.put("3", "SM6");

        String id = req.getParameter("id");
        String model = carInfo.get(id);

        if(model == null) {
            resp.sendError(404);
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("model", model);

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("dataset: " + jsonObject.toString());
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // json 형식으로 보낸다.
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String inputLine;
        inputLine = reader.lines().collect(Collectors.joining());

        List<Map<String, String>> carInfo = gson.fromJson(inputLine, List.class);
        PrintWriter out = resp.getWriter();
        carInfo.forEach(obj -> {
            System.out.println("id("+ obj.get("id") +"):" + obj.get("model"));
            out.println("id("+ obj.get("id") +"):" + obj.get("model"));
        });

    }
}
