package com.github.register.resource;

import com.github.register.application.AccountApplicationService;
import com.github.register.domain.account.Account;
import com.github.register.domain.account.validation.AuthenticatedAccount;
import com.github.register.domain.account.validation.NotConflictAccount;
import com.github.register.domain.account.validation.UniqueAccount;
import com.github.register.infrastructure.jaxrs.CommonResponse;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
     * Get user details based on user name
     * e.g. http://127.0.0.1:8080/accounts/1
     */
    @GET
    @Path("/{username}")
    //@Cacheable(key = "#username")
    public Account getUser(@PathParam("username") String username) {
        return service.findAccountByUsername(username);
    }

    /**
     * Creating a new user (registration)
     */
    @POST
    @CacheEvict(key = "#user.username")
    public Response createUser(@Valid @UniqueAccount Account user) {
        return CommonResponse.op(() -> service.createAccount(user));
    }

    /**
     * Updating user information
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response updateUser(@Valid @AuthenticatedAccount @NotConflictAccount Account user) {
        return CommonResponse.op(() -> service.updateAccount(user));
    }


    /**
     * Marking an individual user as deleted
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response markUserDeleted(@Valid @AuthenticatedAccount @NotConflictAccount Account user, @NotNull Integer deletedUserId) {
        return CommonResponse.op(() -> service.markAccountDeletedById(deletedUserId));
    }

    /**
     * Mark multiple users as delete
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response markUsersDelete(@Valid @AuthenticatedAccount @NotConflictAccount Account user, @NotEmpty List<Integer> ids) {
        return CommonResponse.op(() -> service.markAccountDeletedByIds(ids));
    }



}
