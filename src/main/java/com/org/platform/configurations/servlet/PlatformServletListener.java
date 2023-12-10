package com.org.platform.configurations.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class PlatformServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO : do something while starting your service
        log.info("CustomListener is initialized || application is deployed on the server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO : do something
        log.info("CustomListener is destroyed || application is un-deployed from the server");
    }

}
