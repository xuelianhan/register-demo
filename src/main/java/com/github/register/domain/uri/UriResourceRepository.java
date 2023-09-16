package com.github.register.domain.uri;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
public interface UriResourceRepository extends CrudRepository<UriResource, Integer> {

    @Override
    List<UriResource> findAll();

    UriResource findByUriName(String uriName);

    <S extends UriResource> S save(S entity);

    <S extends UriResource> Iterable<S> saveAll(Iterable<S> entities);

    Optional<UriResource> findById(Integer id);

    Iterable<UriResource> findAllById(Iterable<Integer> ids);

}
