package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.AdminServices;
import com.codehub.techniconrenovations.util.UtilFunctions;
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
    Property property;
    PropertyDto propertyDto;
    PropertyOwner propertyOwner;
    UserDto userDto;
    PropertyRepair repair;
    RepairDto repairDto;
    List<PropertyDto> properties;
    List<RepairDto> repairs;
    List<UserDto> users;
    
    @Before
    public void setUp() {
        adminResource = new AdminResource();
        adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        propertyOwner = new PropertyOwner(1, "name", "surname", "address", "phoneNumber", "email", "username", "password", "typeOfUser");
        userDto = new UserDto(propertyOwner);
        users = new ArrayList<>();
        users.add(userDto);
        property = new Property("E9", "123 Main St", 2000, PropertyType.APARTMENT_BUILDING, propertyOwner);
        propertyDto = new PropertyDto(property);
        repair = new PropertyRepair(propertyOwner, property, RepairType.PAINTING, UtilFunctions.stringToDate("00/00/0000"), "shortDescription", "description", Status.PENDING);
        repairDto = new RepairDto(repair);
        properties = new ArrayList<>();
        properties.add(propertyDto);
        repairs = new ArrayList<>();
        repairs.add(repairDto);
    }

    @Test
    public void testPing() {
        Response response = adminResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    @Test
    public void testGetPendingRepairs() {
        when(adminServices.getPendingRepairs()).thenReturn(repairs);
        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(repairs, result.getData());
    }

    @Test
    public void testGetPendingRepairsWithNoResults() {
        when(adminServices.getPendingRepairs()).thenReturn(new ArrayList<>());
        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testProposeCost() {
        Response response = adminResource.proposeCost(repairDto);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testProposeStartEndDates() {
        Response response = adminResource.proposeStartEndDates(repairDto);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testGetAllRepairs() {
        when(adminServices.getAllRepairs()).thenReturn(repairs);
        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(repairs, result.getData());
    }

    @Test
    public void testGetAllRepairsWithNoResults() {
        when(adminServices.getAllRepairs()).thenReturn(new ArrayList<>());
        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetProperties() {
        property.setPropertyId("123");
        property.setPropertyAddress("123 Main St");
        properties.add(propertyDto);
        when(adminServices.getProperties()).thenReturn(properties);
        RestApiResult result = adminResource.getProperties();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(properties, result.getData());
    }

    @Test
    public void testGetPropertiesWithNoResults() {
        when(adminServices.getProperties()).thenReturn(new ArrayList<>());
        RestApiResult result = adminResource.getProperties();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetOwners() {
        when(adminServices.getOwners()).thenReturn(users);
        RestApiResult result = adminResource.getOwners();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(users, result.getData());
    }

    @Test
    public void testGetOwnersWithNoResults() {
        when(adminServices.getOwners()).thenReturn(new ArrayList<>());
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
