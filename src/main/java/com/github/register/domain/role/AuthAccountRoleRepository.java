package com.github.register.domain.role;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
public interface AuthAccountRoleRepository extends CrudRepository<AuthAccountRole, Integer> {

    @Override
    List<AuthAccountRole> findAll();

    <S extends AuthAccountRole> S save(S entity);

    <S extends AuthAccountRole> Iterable<S> saveAll(Iterable<S> entities);

    Optional<AuthAccountRole> findById(Integer id);

    Iterable<AuthAccountRole> findAllById(Iterable<Integer> ids);
}
