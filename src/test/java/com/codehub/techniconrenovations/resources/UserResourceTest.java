package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserResourceTest {

    @Test
    public void testPing() {
        UserResource userResource = new UserResource();
        Response response = userResource.ping();
        assertEquals(200, response.getStatus());
        assertEquals("pang", response.getEntity());
    }

    @Test
    public void testCorrectPropertyAddressWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        PropertyDto dto = new PropertyDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyAddress(123, "123456", "123 Address")).thenReturn(false);

        Response response = userResource.correctPropertyAddress(dto);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectPropertyConstructionYearWithInvalidData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        PropertyDto dto = new PropertyDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyConstructionYear(12345, "abc123", 2021)).thenReturn(false);

        Response response = userResource.correctPropertyconstructionYear(dto);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testGetProperties() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        List<PropertyDto> properties = Arrays.asList(new PropertyDto());
        when(propertyOwnerServices.getProperties(123)).thenReturn(properties);

        RestApiResult result = userResource.getProperties(123);
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
        assertEquals(properties, result.getData());
    }

    @Test
    public void testGetPropertiesWithEmptyResult() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        List<PropertyDto> properties = new ArrayList<>();
        when(propertyOwnerServices.getProperties(123)).thenReturn(properties);

        RestApiResult result = userResource.getProperties(123);
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
    }

    @Test
    public void testAcceptOrDeclineRepairWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        RepairDto dto = new RepairDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.acceptOrDeclineRepair(123, 1, true)).thenReturn(false);

        Response response = userResource.acceptOrDeclineRepair(dto);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testGetRepairStatus() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        List<RepairDto> repairs = Arrays.asList(new RepairDto());
        when(propertyOwnerServices.getRepairStatus(123)).thenReturn(repairs);
        RestApiResult result = userResource.getRepairStatus(123);
        assertEquals(200, result.getErrorCode());
        assertEquals("Successful", result.getDescription());
    }

    @Test
    public void testGetRepairStatusNoRepairs() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.getRepairStatus(123)).thenReturn(new ArrayList<>());

        RestApiResult result = userResource.getRepairStatus(123);
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
    }

    @Test
    public void testCorrectOwnerUsernameFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        UserDto dto = new UserDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerUsername(123, "newUsername")).thenReturn(false);
        Response response = userResource.correctOwnerUsername(dto);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectOwnerEmailFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        UserDto dto = new UserDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerEmail(123, "new@email.com")).thenReturn(false);
        Response response = userResource.correctOwnerEmail(dto);
        assertEquals(401, response.getStatus());
    }

    @Test
    public void testCorrectOwnerPasswordFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        UserDto dto = new UserDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerPassword(123, "newPassword")).thenReturn(false);

        Response response = userResource.correctOwnerPassword(dto);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testSafelyDeleteProperty() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        PropertyDto dto = new PropertyDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeleteProperty(123, "123456")).thenReturn(true);
        Response response = userResource.safelyDeleteProperty(dto);
        assertEquals(404, response.getStatus());
    }


    @Test
    public void testSafelyDeletePropertyRepair() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        RepairDto dto = new RepairDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyRepair(123, 1)).thenReturn(true);
        Response response = userResource.safelyDeletePropertyRepair(dto);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testSafelyDeletePropertyOwner() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        UserDto dto = new UserDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyOwner(123)).thenReturn(true);
        Response response = userResource.safelyDeletePropertyOwner(dto);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testSafelyDeletePropertyOwnerWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        UserDto dto = new UserDto();
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyOwner(123)).thenReturn(false);
        Response response = userResource.safelyDeletePropertyOwner(dto);
        assertEquals(404, response.getStatus());
    }
}
