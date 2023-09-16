package com.github.register.domain.role;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
public interface AuthRoleRepository extends CrudRepository<AuthRole, Integer> {

    <S extends AuthRole> S save(S entity);

    <S extends AuthRole> Iterable<S> saveAll(Iterable<S> entities);

    Optional<AuthRole> findById(Integer id);

    Collection<AuthRole> findByIdIn(Collection<AuthRole> ids);

    void deleteById(Integer id);
}
