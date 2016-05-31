package com.splitpix.templates.web.api.starter.repository;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.splitpix.templates.web.api.starter.domain.BaseDomain;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class BaseRepository<D extends BaseDomain> implements IRepository<D> {

    protected TypeToken<D> typeToken = new TypeToken<D>(getClass()) {};
    protected Class<D> type = (Class<D>)this.typeToken.getRawType();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Stream<D> findAll() {
        return this.findAll(10);
    }

    @Override
    public Stream<D> findAll(int limit) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "lastModifiedDate"),
            new Sort.Order(Sort.Direction.DESC, "createdDate"));

        return this.findAll(sort, limit);
    }

    @Override
    public Stream<D> findAll(Sort sort, int limit) {
        return this.findAll(sort, limit, null);
    }

    @Override
    public Stream<D> findAll(Sort sort, int limit, String lastId) {
        TypedAggregation<D> typedAggregation = newAggregation(this.type,
                match(!Strings.isNullOrEmpty(lastId) ? Criteria.where("_id").lt(new ObjectId(lastId)) : new Criteria()),
                sort(sort), limit(limit));

        AggregationResults<D> aggregationResults = this.mongoTemplate.aggregate(typedAggregation,
                this.type);

        return aggregationResults.getMappedResults().stream();
    }


}
