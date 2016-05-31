package com.splitpix.templates.web.api.starter.controller;

import com.splitpix.templates.web.api.starter.domain.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tests")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public Test get() {
        return new Test("test", "testing");
    }
}
