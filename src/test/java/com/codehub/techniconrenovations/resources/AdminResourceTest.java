package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import jakarta.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdminResourceTest {

    private static final AdminResource adminResource = new AdminResource();

    /**
     * Test of ping method, of class AdminResource.
     */
    @Test
    public void testPing() {
        Response response = adminResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    /**
     * Test of getPendingRepairs method, of class AdminResource.
     */
    @Test
    public void testGetPendingRepairs() {
        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of proposeCost method, of class AdminResource.
     */
    @Test
    public void testProposeCost() {
        double cost = 100.0;
        int repairId = 1;
        Response response = adminResource.proposeCost(cost, repairId);
        assertEquals(200, response.getStatus());
        RestApiResult result = (RestApiResult) response.getEntity();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of proposeStartEndDates method, of class AdminResource.
     */
    @Test
    public void testProposeStartEndDates() {
        String startDate = "01/01/2022";
        String endDate = "31/01/2022";
        int repairId = 2;
        Response response = adminResource.proposeStartEndDates(startDate, endDate, repairId);
        assertEquals(200, response.getStatus());
        RestApiResult result = (RestApiResult) response.getEntity();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of getAllRepairs method, of class AdminResource.
     */
    @Test
    public void testGetAllRepairs() {
        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of getProperties method, of class AdminResource.
     */
    @Test
    public void testGetProperties() {
        RestApiResult result = adminResource.getProperties();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of getOwners method, of class AdminResource.
     */
    @Test
    public void testGetOwners() {
        RestApiResult result = adminResource.getOwners();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of permanentlyDeleteProperties method, of class AdminResource.
     */
    @Test
    public void testPermanentlyDeleteProperties() {
        Response response = adminResource.permanentlyDeleteProperties();
        assertEquals(200, response.getStatus());
        RestApiResult result = (RestApiResult) response.getEntity();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of permanentlyDeletePropertyOwner method, of class AdminResource.
     */
    @Test
    public void testPermanentlyDeletePropertyOwner() {
        Response response = adminResource.permanentlyDeletePropertyOwner();
        assertEquals(200, response.getStatus());
        RestApiResult result = (RestApiResult) response.getEntity();
        assertEquals(200, result.getErrorCode());
    }

    /**
     * Test of permanentlyDeleteRepairs method, of class AdminResource.
     */
    @Test
    public void testPermanentlyDeleteRepairs() {
        Response response = adminResource.permanentlyDeleteRepairs();
        assertEquals(200, response.getStatus());
        RestApiResult result = (RestApiResult) response.getEntity();
        assertEquals(200, result.getErrorCode());
    }
}
