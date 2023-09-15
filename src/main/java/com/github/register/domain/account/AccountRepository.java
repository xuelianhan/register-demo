package com.github.register.domain.account;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * User Object Data Repository
 *
 * @author
 * @date
 **/
@CacheConfig(cacheNames = "repository.account")
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Override
    Iterable<Account> findAll();


    @Cacheable(key = "#username")
    Account findByUsername(String username);

    /**
     * Determine the uniqueness, no duplicates of username, email, or phone are allowed
     */
    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    /**
     * Determine the uniqueness, no duplicates of username, email, or phone are allowed
     */
    Collection<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    /**
     * Determining the existence, the existence of username is an existence.
     */
    @Cacheable(key = "#username")
    boolean existsByUsername(String username);


    // 覆盖以下父类中需要处理缓存失效的方法
    // 父类取不到CacheConfig的配置信息，所以不能抽象成一个通用的父类接口中完成
    @Caching(evict = {
            @CacheEvict(key = "#entity.id"),
            @CacheEvict(key = "#entity.username")
    })
    <S extends Account> S save(S entity);

    @CacheEvict
    <S extends Account> Iterable<S> saveAll(Iterable<S> entities);

    @Cacheable(key = "#id")
    Optional<Account> findById(Integer id);

    List<Account> findAllByIdIn(Set<Integer> ids);

    @Cacheable(key = "#id")
    boolean existsById(Integer id);

    @CacheEvict(key = "#id")
    void deleteById(Integer id);

    @CacheEvict(key = "#entity.id")
    void delete(Account entity);

    @CacheEvict(allEntries = true)
    void deleteAll(Iterable<? extends Account> entities);

    @CacheEvict(allEntries = true)
    void deleteAll();
}
