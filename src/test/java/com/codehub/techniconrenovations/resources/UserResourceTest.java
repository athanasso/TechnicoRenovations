package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserResourceTest {
    
    @Inject
    private final UserResource userResource = new UserResource();

    /**
     * Test of ping method, of class UserResource.
     */
    @Test
    public void testPing() {
        Response response = userResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    /**
     * Test of registerProperty method, of class UserResource.
     */
    @Test
    public void testRegisterProperty() {
        // Call the registerProperty method with some sample input data
        Response response = userResource.registerProperty(1123123, "E10095", "123 Main St", 2010, "DETACHED_HOUSE");

        // Verify that the method returned the expected HTTP status code
        assertEquals(200, response.getStatus());
    }


    /**
     * Test of correctPropertyAddress method, of class UserResource.
     */
    @Test
    public void testCorrectPropertyAddress() {
        // Call the correctPropertyAddress method with some sample input data
        Response response = userResource.correctPropertyAddress(1123123, "E1001", "456 Main St");

        // Verify that the method returned the expected HTTP status code
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of correctPropertyType method, of class UserResource.
     */
    @Test
    public void testCorrectPropertyType() {
        // Call the correctPropertyType method with some sample input data
        Response response = userResource.correctPropertyType(1123123, "E1001", "APARTMENT_BUILDING");

        // Verify that the method returned the expected HTTP status code
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of correctPropertyconstructionYear method, of class UserResource.
     */
    @Test
    public void testCorrectPropertyConstructionYear() {
        // Call the correctPropertyconstructionYear method with some sample input data
        Response response = userResource.correctPropertyconstructionYear(1123123, "E1001", 2015);

        // Verify that the method returned the expected HTTP status code
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of registerPropertyRepair method, of class UserResource.
     */
    @Test
    public void testRegisterPropertyRepair() {
        // Call the registerPropertyRepair method with some sample input data
        Response response = userResource.registerPropertyRepair(1123123, "E10095", "Insulate the house's windows", "Insulate windows", "INSULATION");

        // Verify that the method returned the expected HTTP status code
        assertEquals(200, response.getStatus());
    }

    /**
     * Test of getProperties method, of class UserResource.
     */
    @Test
    public void testGetProperties() {
        RestApiResult result = userResource.getProperties(1231233);
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of acceptOrDeclineRepair method, of class UserResource.
     */
    @Test
    public void testAcceptOrDeclineRepair() {
        Response response = userResource.acceptOrDeclineRepair(1123123, 1, true);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of getRepairStatus method, of class UserResource.
     */
    @Test
    public void testGetRepairStatus() {
        RestApiResult result = userResource.getRepairStatus(1123123);
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of correctOwnerUsername method, of class UserResource.
     */
    @Test
    public void testCorrectOwnerUsername() {
        Response response = userResource.correctOwnerUsername(1123123, "new_username");
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of correctOwnerEmail method, of class UserResource.
     */
    @Test
    public void testCorrectOwnerEmail() {
        Response response = userResource.correctOwnerEmail(1123123, "new_email@example.com");
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of correctOwnerPassword method, of class UserResource.
     */
    @Test
    public void testCorrectOwnerPassword() {
        Response response = userResource.correctOwnerPassword(1123123, "new_password");
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of safelyDeleteProperty method, of class UserResource.
     */
    @Test
    public void testSafelyDeleteProperty() {
        Response response = userResource.safelyDeleteProperty(1123123, "E1001");
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of safelyDeletePropertyRepair method, of class UserResource.
     */
    @Test
    public void testSafelyDeletePropertyRepair() {
        Response response = userResource.safelyDeletePropertyRepair(1123123, 1);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    /**
     * Test of safelyDeletePropertyOwner method, of class UserResource.
     */
    @Test
    public void testSafelyDeletePropertyOwner() {
        Response response = userResource.safelyDeletePropertyOwner(1123123);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }
}
