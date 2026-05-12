package com.toto.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

public class FakeHttpExchange extends HttpExchange {

    private final URI uri;
    private final ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
    private int statusCode;

    public FakeHttpExchange(String path) {
        this.uri = URI.create(path);
    }

    @Override
    public URI getRequestURI() {
        return uri;
    }

    @Override
    public String getRequestMethod() {
        return "GET";
    }

    @Override
    public OutputStream getResponseBody() {
        return responseBody;
    }

    @Override
    public void sendResponseHeaders(int rCode, long responseLength) {
        this.statusCode = rCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseAsString() {
        return responseBody.toString();
    }

    @Override public Headers getRequestHeaders() { return new Headers(); }
    @Override public Headers getResponseHeaders() { return new Headers(); }
    @Override public InputStream getRequestBody() { return new ByteArrayInputStream(new byte[0]); }
    @Override public void close() {}
    @Override public InetSocketAddress getRemoteAddress() { return null; }
    @Override public InetSocketAddress getLocalAddress() { return null; }
    @Override public String getProtocol() { return "HTTP/1.1"; }
    @Override public Object getAttribute(String name) { return null; }
    @Override public void setAttribute(String name, Object value) {}
    @Override public void setStreams(InputStream i, OutputStream o) {}
    @Override public com.sun.net.httpserver.HttpPrincipal getPrincipal() { return null; }

    @Override
    public HttpContext getHttpContext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHttpContext'");
    }

    @Override
    public int getResponseCode() {
        return statusCode;
    }
}
