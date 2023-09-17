package com.github.register.resource;

import com.github.register.domain.account.Account;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sniper
 * @date
 */
public class AccountResourceTest extends JAXRSResourceBase {

    @Test
    void getUserWithExistAccount() {
        Response resp = get("/accounts/sniper");
        assertOK(resp);
        Account sniper = resp.readEntity(Account.class);
        assertEquals("sniper", sniper.getUsername(), "should return user: sniper");
    }

    @Test
    void getUserWithNotExistAccount() {
        assertNoContent(get("/accounts/nobody"));
    }


    @Test
    void getUserList() {
        authenticatedScope(() -> {
            assertOK(get("/accounts"));
        });
    }

    @Test
    void createUser() {
        Account account = new Account();
        account.setUsername("goldengate");
        account.setEmail("goldengater@github.com");
        assertBadRequest(post("/accounts", account));
        account.setTelephone("13888888890");
        account.setName("nobody");
        assertNoContent(get("/accounts/account"));
        assertOK(post("/accounts", account));
        assertOK(get("/accounts/sniper"));
    }

    @Test
    void updateUser() {
        authenticatedScope(() -> {
            Response resp = get("/accounts/sniper");
            Account account = resp.readEntity(Account.class);
            account.setName("sniper007");
            assertOK(put("/accounts", account));
            assertEquals("sniper007", get("/accounts/sniper").readEntity(Account.class).getName(), "should get the new name now");
        });
    }

    @Test
    void markUserDeleted() {
        authenticatedScope(() -> {
            assertOK(delete("/accounts/markUserDeleted/3"));
        });
    }

    @Test
    void markUsersDeleted() {
        authenticatedScope(() -> {
            assertOK(delete("/accounts/markUsersDeleted?ids=2,3"));
        });
    }

}
