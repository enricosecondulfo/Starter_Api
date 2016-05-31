package com.splitpix.templates.web.api.starter.domain;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

public class BaseDomain implements Serializable {

    @Id
    private String id;

    private Instant createdDate;

    private Instant lastModifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
