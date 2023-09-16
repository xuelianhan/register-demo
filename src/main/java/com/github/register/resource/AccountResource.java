package com.github.register.resource;

import com.github.register.application.AccountApplicationService;
import com.github.register.domain.account.Account;
import com.github.register.domain.account.validation.AuthenticatedAccount;
import com.github.register.domain.account.validation.NotConflictAccount;
import com.github.register.domain.account.validation.UniqueAccount;
import com.github.register.domain.auth.Role;
import com.github.register.infrastructure.jaxrs.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Account Resource
 * <p>
 * Expose resources to the client in a Restful form,
 * providing an entry point to manage the user's resources {@link Account}.
 * @see <a href="https://mkyong.com/webservices/jax-rs/jax-rs-pathparam-example/"></a>
 *
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
     * Get user details based on username
     * e.g. GET http://127.0.0.1:8080/restful/accounts/sniper
     */
    @GET
    @Path("/{username}")
    @Cacheable(key = "#username")
    public Account getUser(@PathParam("username") String username) {
        log.info("username:{}", username);
        return service.findAccountByUsername(username);
    }

    /**
     * Get all accounts information.
     * e.g. GET http://127.0.0.1:8080/restful/accounts
     * @return
     */
    @GET
    @Cacheable(key = "'ALL_USER'")
    @RolesAllowed(Role.ADMIN)
    public List<Account> getUserList() {
        return service.findAllAccounts();
    }


    /**
     * Creating a new user (registration)
     * e.g. POST http://127.0.0.1:8080/restful/accounts/user
     */
    @POST
    @CacheEvict(key = "#user.username")
    public Response createUser(@Valid @UniqueAccount Account user) {
        log.info("user:{}", user);
        return CommonResponse.op(() -> service.createAccount(user));
    }

    /**
     * Updating user information(Edit function)
     * e.g. PUT http://127.0.0.1:8080/restful/accounts/user
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response updateUser(@Valid @AuthenticatedAccount @NotConflictAccount Account user) {
        return CommonResponse.op(() -> service.updateAccount(user));
    }


    /**
     * Marking an individual user as deleted
     * e.g. DELETE http://127.0.0.1:8080/restful/accounts/markUserDeleted/3
     */
    @DELETE
    @Path("/markUserDeleted")
    @RolesAllowed(Role.ADMIN)
    public Response markUserDeleted(@PathParam("deletedUserId")Integer deletedUserId) {
        return CommonResponse.op(() -> service.markAccountDeletedById(deletedUserId));
    }

    /**
     * Mark multiple users as delete
     * e.g. DELETE http://127.0.0.1:8080/restful/accounts/markUsersDeleted?ids=2,3
     */
    @DELETE
    @Path("/markUsersDeleted")
    @RolesAllowed(Role.ADMIN)
    public Response markUsersDeleted(@QueryParam("ids") String ids) {
        return CommonResponse.op(() -> service.markAccountDeletedByIds(ids));
    }


}
