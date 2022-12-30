package com.codehub.techniconrenovations.resources;

import jakarta.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AuthResourceTest {
    
    private static final AuthResource authResource = new AuthResource();

    /**
     * Test of login method, of class AuthResource.
     */
    @Test
    public void testLogin() {
        Response response = authResource.login("Manos", "manos123!");
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of register method, of class AuthResource.
     */
    @Test
    public void testRegister() {
        Response response = authResource.register("Emmanouil", "manos123!","Manolis","Athanas","mail@mail.com","ST main 32","6978769321",0);
        assertEquals(200, response.getStatus());
    }
}
