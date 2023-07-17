package com.org.platform.configurations.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.org.platform.utils.Constants.APPLICATION_JSON_TYPE;

@Slf4j
public class PlatformServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON_TYPE);
        log.info("CustomServlet doGet() method is invoked");
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("CustomServlet doPost() method is invoked");
        super.doPost(request, response);
    }

}
