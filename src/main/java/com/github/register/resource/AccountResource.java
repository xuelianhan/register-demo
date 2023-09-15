package com.github.register.resource;

import com.github.register.application.AccountApplicationService;
import com.github.register.domain.account.Account;
import com.github.register.domain.account.validation.AuthenticatedAccount;
import com.github.register.domain.account.validation.NotConflictAccount;
import com.github.register.domain.account.validation.UniqueAccount;
import com.github.register.domain.auth.Role;
import com.github.register.infrastructure.jaxrs.CommonResponse;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Account Resource
 * <p>
 * Expose resources to the client in a Restful form,
 * providing an entry point to manage the user's resources {@link Account}.
 * @see <a href="https://mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/"></a>
 *
 * @author zhouzhiming
 * @author sniper
 * @date Fri Seq 15, 2023
 **/
@Path("/accounts")
@Component
@CacheConfig(cacheNames = "resource.account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private AccountApplicationService service;

    /**
     * Get user details based on user name
     * e.g. http://127.0.0.1:8080/restful/accounts/sniper
     */
    @GET
    @Path("/{username}")
    //@Cacheable(key = "#username")
    public Account getUser(@PathParam("username") String username) {
        log.info("username:{}", username);
        return service.findAccountByUsername(username);
    }

    /**
     * Creating a new user (registration)
     */
    @POST
    @CacheEvict(key = "#user.username")
    public Response createUser(@Valid @UniqueAccount Account user) {
        log.info("user:{}", user);
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
    @GET
    @Path("/markUserDeleted")
    @RolesAllowed(Role.ADMIN)
    public Response markUserDeleted(@Valid @AuthenticatedAccount Account user, @QueryParam("deletedUserId")Integer deletedUserId) {
        return CommonResponse.op(() -> service.markAccountDeletedById(deletedUserId));
    }

    /**
     * Mark multiple users as delete
     */
    @POST
    @Path("/markUsersDeleted/")
    @RolesAllowed(Role.ADMIN)
    public Response markUsersDeleted(@Valid @AuthenticatedAccount Account user, @FormParam("ids") List<Integer> ids) {
        return CommonResponse.op(() -> service.markAccountDeletedByIds(ids));
    }


}
