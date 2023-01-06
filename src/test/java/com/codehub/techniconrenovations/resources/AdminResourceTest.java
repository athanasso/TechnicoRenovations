package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.AdminServices;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminResourceTest {

    @Test
    public void testPing() {
        AdminResource adminResource = new AdminResource();
        Response response = adminResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    @Test
    public void testGetPendingRepairs() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<RepairDto> repairs = new ArrayList<>();
        RepairDto repair = new RepairDto();
        repair.setRepairId(1);
        repair.setProposedCost(new BigDecimal(1000.0));
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
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<RepairDto> repairs = new ArrayList<>();
        when(adminServices.getPendingRepairs()).thenReturn(repairs);

        RestApiResult result = adminResource.getPendingRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testProposeCost() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;

        Response response = adminResource.proposeCost(1000.0, 1);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testProposeStartEndDates() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;

        Response response = adminResource.proposeStartEndDates("01/01/2022", "01/10/2022", 1);
        assertEquals(200, response.getStatus());
        assertEquals("Successful!", response.getEntity());
    }

    @Test
    public void testGetAllRepairs() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<PropertyRepair> repairs = new ArrayList<>();
        PropertyRepair repair = new PropertyRepair();
        repair.setRepairId(1);
        repair.setProposedCost(new BigDecimal(1000.0));
        repair.setActualStartDate(new Date("01/01/2022"));
        repair.setActualEndDate(new Date("01/10/2022"));
        repairs.add(repair);
        when(adminServices.getAllRepairs()).thenReturn(repairs);

        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(repairs, result.getData());
    }

    @Test
    public void testGetAllRepairsWithNoResults() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<PropertyRepair> repairs = new ArrayList<>();
        when(adminServices.getAllRepairs()).thenReturn(repairs);

        RestApiResult result = adminResource.getAllRepairs();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetProperties() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<Property> properties = new ArrayList<>();
        Property property = new Property();
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
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<Property> properties = new ArrayList<>();
        when(adminServices.getProperties()).thenReturn(properties);

        RestApiResult result = adminResource.getProperties();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testGetOwners() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<UserDto> owners = new ArrayList<>();
        UserDto owner = new UserDto(); 
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
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        List<UserDto> owners = new ArrayList<>();
        when(adminServices.getOwners()).thenReturn(owners);

        RestApiResult result = adminResource.getOwners();
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
        assertEquals("empty", result.getData());
    }

    @Test
    public void testPermanentlyDeletePropertyOwner() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;
        Response result = adminResource.permanentlyDeletePropertyOwner();
        assertEquals(200, result.getStatus());
        assertEquals("Successful", result.getEntity());
    }

    @Test
    public void testPermanentlyDeleteRepairs() {
        AdminResource adminResource = new AdminResource();
        AdminServices adminServices = mock(AdminServices.class);
        adminResource.adminServices = adminServices;

        Response result = adminResource.permanentlyDeleteRepairs();
        assertEquals(200, result.getStatus());
        assertEquals("Successful", result.getEntity());
    }
}
