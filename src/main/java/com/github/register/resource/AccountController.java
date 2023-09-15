package com.github.register.resource;

import com.github.register.application.AccountApplicationService;
import com.github.register.domain.account.Account;
import com.github.register.domain.account.validation.AuthenticatedAccount;
import com.github.register.domain.account.validation.NotConflictAccount;
import com.github.register.domain.account.validation.UniqueAccount;
import com.github.register.infrastructure.jaxrs.CommonResponse;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户资源
 * <p>
 * 对客户端以Restful形式暴露资源，提供对用户资源{@link Account}的管理入口
 *
 * @author
 * @date
 **/
@Path("/accounts")
@Component
@CacheConfig(cacheNames = "resource.account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    private AccountApplicationService service;

    /**
     * 根据用户名称获取用户详情
     * e.g. http://127.0.0.1:8080/accounts/1
     */
    @GET
    @Path("/{username}")
    @Cacheable(key = "#username")
    public Account getUser(@PathParam("username") String username) {
        return service.findAccountByUsername(username);
    }

    /**
     * 创建新的用户(注册)
     */
    @POST
    @CacheEvict(key = "#user.username")
    public Response createUser(@Valid @UniqueAccount Account user) {
        return CommonResponse.op(() -> service.createAccount(user));
    }

    /**
     * 更新用户信息
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response updateUser(@Valid @AuthenticatedAccount @NotConflictAccount Account user) {
        return CommonResponse.op(() -> service.updateAccount(user));
    }


    /**
     * 标记单个用户为删除状态
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response markUserDeleted(@Valid @AuthenticatedAccount @NotConflictAccount Account user, Integer deletedUserId) {
        return CommonResponse.op(() -> service.markAccountDeletedById(deletedUserId));
    }

    /**
     * 标记多个用户为删除状态
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response markUsersDelete(@Valid @AuthenticatedAccount @NotConflictAccount Account user, List<Integer> ids) {
        return CommonResponse.op(() -> service.markAccountDeletedByIds(ids));
    }



}
