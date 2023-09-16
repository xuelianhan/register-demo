package com.github.register.resource;

import com.github.register.RegisterApplication;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sniper
 * @date
 */
@SpringBootTest(classes = RegisterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JAXRSResourceBase extends com.github.register.DBRollbackBase {

    @Value("${local.server.port}")
    protected int port;

    private String accessToken = null;

    Invocation.Builder build(String path) {
        Invocation.Builder builder = ClientBuilder.newClient().target("http://localhost:" + port + "/restful" + path)
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
                .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        if (accessToken != null) {
            builder.header("Authorization", "bearer " + accessToken);
        }
        return builder;
    }

    JSONObject json(Response response) throws JSONException {
        return new JSONObject(response.readEntity(String.class));
    }

    JSONArray jsonArray(Response response) throws JSONException {
        return new JSONArray(response.readEntity(String.class));
    }

    /**
     * The unit test always uses the user of sniper for logins.
     * http://localhost:8080/oauth/token?username=sniper&password=MFfTW3uNI4eqhwDkG7HP9p2mzEUu%2Fr2&grant_type=password&client_id=imagebase&client_secret=ImageBase2023Run
     */
    void login() {
        String url = "http://localhost:" + port + "/oauth/token?username=sniper&password=MFfTW3uNI4eqhwDkG7HP9p2mzEUu%2Fr2&grant_type=password&client_id=imagebase&client_secret=ImageBase2023Run";
        Response resp = ClientBuilder.newClient().target(url).request().get();
        try {
            accessToken = json(resp).getString("access_token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void logout() {
        accessToken = null;
    }

    void authenticatedScope(Runnable runnable) {
        try {
            login();
            runnable.run();
        } finally {
            logout();
        }
    }

    <T> T authenticatedGetter(Supplier<T> supplier) {
        try {
            login();
            return supplier.get();
        } finally {
            logout();
        }
    }

    Response get(String path) {
        return build(path).get();
    }

    Response delete(String path) {
        return build(path).delete();
    }

    Response post(String path, Object entity) {
        return build(path).post(Entity.json(entity));
    }

    Response put(String path, Object entity) {
        return build(path).put(Entity.json(entity));
    }

    Response patch(String path) {
        return build(path).method("PATCH", Entity.text("MUST_BE_PRESENT"));
    }

    static void assertOK(Response response) {
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 200/OK");
    }

    static void assertNoContent(Response response) {
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 204/NO_CONTENT");
    }

    static void assertBadRequest(Response response) {
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 400/BAD_REQUEST");
    }

    static void assertForbidden(Response response) {
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 403/FORBIDDEN");
    }

    static void assertServerError(Response response) {
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 500/INTERNAL_SERVER_ERROR");
    }

    static void assertNotFound(Response response) {
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus(), "Expected HTTP Status Code: 404/NOT_FOUND");
    }


}
