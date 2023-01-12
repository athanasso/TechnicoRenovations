package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import jakarta.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthResourceTest {

    @Test
    public void testLogin() {
        AuthResource authResource = new AuthResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        authResource.propertyOwnerServices = propertyOwnerServices;
        PropertyOwner expectedResult = new PropertyOwner();
        when(propertyOwnerServices.logIn("username", "password")).thenReturn(expectedResult);

        PropertyOwner response = authResource.login("username", "password");
        assertEquals(response, expectedResult);
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        AuthResource authResource = new AuthResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        authResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.logIn("username", "password")).thenReturn(null);

        PropertyOwner response = authResource.login("username", "password");
        assertEquals(null, response);
    }

    @Test
    public void testRegisterSuccessful() {
        AuthResource authResource = new AuthResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        authResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.register(123, "name", "surname", "address", "1234567890", "email", "username", "password", "user")).thenReturn(true);

        Response response = authResource.register("username", "password", "name", "surname", "email", "address", "1234567890", 123);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testRegisterWithInvalidCredentials() {
        AuthResource authResource = new AuthResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        authResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.register(123, "name", "surname", "address", "12345678", "email", "username", "password", "typeOfUser")).thenReturn(false);

        Response response = authResource.register("username", "password", "name", "surname", "email", "address", "12345678", 123);
        assertEquals(404, response.getStatus());
        assertEquals("Invalid Credentials", response.getEntity());
    }
}
