package com.splitpix.templates.web.api.starter.repository;

import com.splitpix.templates.web.api.starter.domain.BaseDomain;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

public interface IRepository<D extends BaseDomain> {

    public Stream<D> findAll();

    public Stream<D> findAll(int limit);

    public Stream<D> findAll(Sort sort, int limit);

    public Stream<D> findAll(Sort sort, int limit, String id);
}
