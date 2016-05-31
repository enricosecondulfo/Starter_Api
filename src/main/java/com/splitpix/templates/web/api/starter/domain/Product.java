package com.splitpix.templates.web.api.starter.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product extends BaseDomain {

    private String name;

    public Product() {}

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
