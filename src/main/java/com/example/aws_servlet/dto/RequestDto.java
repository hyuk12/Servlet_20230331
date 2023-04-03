package com.example.aws_servlet.dto;

import com.google.gson.Gson;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.stream.Collectors;

public class RequestDto<T> {
    public static <T> T convertRequestBody(HttpServletRequest req, Class<?> c) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String requestBody = reader.lines().collect(Collectors.joining());

        Gson gson = new Gson();
        T obj = (T) gson.fromJson(requestBody, c);
        return obj;


    }
}
