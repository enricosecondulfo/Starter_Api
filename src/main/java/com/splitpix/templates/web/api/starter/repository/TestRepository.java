package com.splitpix.templates.web.api.starter.repository;

import com.splitpix.templates.web.api.starter.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TestRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void test() {
        TypedAggregation<Test> aggregation = newAggregation(Test.class,
                sort(Sort.Direction.ASC, previousOperation()));
    }
}
