package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.services.AdminServices;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminResourceTest {
    
    AdminResource adminResource;
    AdminServices adminServices;
    List<RepairDto> repairs;
    RepairDto repair;
    List<PropertyDto> properties;
    PropertyDto property;
    List<UserDto> owners;
    UserDto owner; 
    
    @Before
    public void setUp() {
        adminResource = new AdminResource();
        adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        repairs = new ArrayList<>();
        repair = new RepairDto();
        properties = new ArrayList<>();
        property = new PropertyDto();
        owners = new ArrayList<>();
        owner = new UserDto(); 
    }

    @Test
    public void testPing() {
        Response response = adminResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    @Test
    public void testGetPendingRepairs() {
        repair.setRepairId(1);
        repair.setProposedCost("1000.0");
        repair.setActualStartDate("01/01/2022");
        repair.setActualEndDate("01/10/2022");
        repairs.add(repair);
        when(adminServices.getPendingRepairs()).thenReturn(repairs);
        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(repairs, result.getData());
    }

    @Test
    public void testGetPendingRepairsWithNoResults() {
        when(adminServices.getPendingRepairs()).thenReturn(repairs);
        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testProposeCost() {
        Response response = adminResource.proposeCost(repair);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testProposeStartEndDates() {
        Response response = adminResource.proposeStartEndDates(repair);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testGetAllRepairs() {
        repair.setRepairId(1);
        repair.setProposedCost("1000.0");
        repair.setActualStartDate("01/01/2022");
        repair.setActualEndDate("01/10/2022");
        repairs.add(repair);
        when(adminServices.getAllRepairs()).thenReturn(repairs);

        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(repairs, result.getData());
    }

    @Test
    public void testGetAllRepairsWithNoResults() {
        when(adminServices.getAllRepairs()).thenReturn(repairs);
        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetProperties() {
        property.setPropertyId("123");
        property.setPropertyAddress("123 Main St");
        properties.add(property);
        when(adminServices.getProperties()).thenReturn(properties);
        RestApiResult result = adminResource.getProperties();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(properties, result.getData());
    }

    @Test
    public void testGetPropertiesWithNoResults() {
        when(adminServices.getProperties()).thenReturn(properties);
        RestApiResult result = adminResource.getProperties();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetOwners() {
        owner.setVatNumber(1);
        owner.setName("John");
        owner.setSurname("Doe");
        owners.add(owner);
        when(adminServices.getOwners()).thenReturn(owners);

        RestApiResult result = adminResource.getOwners();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(owners, result.getData());
    }

    @Test
    public void testGetOwnersWithNoResults() {
        when(adminServices.getOwners()).thenReturn(owners);
        RestApiResult result = adminResource.getOwners();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testPermanentlyDeletePropertyOwner() {
        Response result = adminResource.permanentlyDeletePropertyOwner();
        assertEquals(200, result.getStatus());
        assertEquals("Successful", result.getEntity());
    }

    @Test
    public void testPermanentlyDeleteRepairs() {
        Response result = adminResource.permanentlyDeleteRepairs();
        assertEquals(200, result.getStatus());
        assertEquals("Successful", result.getEntity());
    }
    
    @Test
    public void testPermanentlyDeleteProperties() {        
        Response result = adminResource.permanentlyDeleteProperties();
        assertEquals(200, result.getStatus());
        assertEquals("Successful", result.getEntity());
    }
}
