package com.tricol.tricol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void doSomething(String user) {
        logger.info("User {} called doSomething", user);
        logger.debug("Debug info: {}", someDebugData());
    }

    private String someDebugData() {
        return "example debug data";
    }
}
