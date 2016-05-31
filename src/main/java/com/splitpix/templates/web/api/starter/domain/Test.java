package com.splitpix.templates.web.api.starter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Test {

    @Id
    private String id;
    private String name;
    private String testOther;

    public Test() {}

    public Test(String name, String testOther) {
        this.name = name;
        this.testOther = testOther;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestOther() {
        return testOther;
    }

    public void setTestOther(String testOther) {
        this.testOther = testOther;
    }
}
