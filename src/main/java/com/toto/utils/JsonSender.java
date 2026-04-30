package com.toto.utils;

import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class JsonSender {
    public static void sendJson(HttpExchange exchange, int statusCode, Object response, ObjectMapper objectMapper)
        throws IOException {

        String json = objectMapper.writeValueAsString(response);
        System.out.println(json);
        byte[] bytes = json.getBytes();

        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
