package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
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
    public void testRegisterProperty() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.registerProperty(123, "123456", "123 Address", 2020, PropertyType.APARTMENT_BUILDING)).thenReturn(true);

        Response response = userResource.registerProperty(123, "123456", "123 Address", 2020, "1");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testRegisterPropertyWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.registerProperty(123, "123456", "123 Address", 2020, PropertyType.APARTMENT_BUILDING)).thenReturn(false);

        Response response = userResource.registerProperty(123, "123456", "123 Address", 2020, "Apartment");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectPropertyAddress() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyAddress(123, "123456", "123 Address")).thenReturn(true);

        Response response = userResource.correctPropertyAddress(123, "123456", "123 Address");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectPropertyAddressWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyAddress(123, "123456", "123 Address")).thenReturn(false);

        Response response = userResource.correctPropertyAddress(123, "123456", "123 Address");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectPropertyType() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyType(12345678, "1234", PropertyType.APARTMENT_BUILDING)).thenReturn(true);

        Response response = userResource.correctPropertyType(12345678, "1234", "1");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectPropertyTypeWithInvalidVatNumber() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyType(12345, "abc123", PropertyType.APARTMENT_BUILDING)).thenReturn(false);

        Response response = userResource.correctPropertyType(12345, "abc123", "1");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectPropertyConstructionYear() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyconstructionYear(12345, "abc123", 2021)).thenReturn(true);

        Response response = userResource.correctPropertyconstructionYear(12345, "abc123", 2021);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectPropertyConstructionYearWithInvalidData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctPropertyconstructionYear(12345, "abc123", 2021)).thenReturn(false);

        Response response = userResource.correctPropertyconstructionYear(12345, "abc123", 2021);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testRegisterPropertyRepair() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.registerPropertyRepair(1123123, "E1001", "Description", "Short Description", RepairType.PLUMBING)).thenReturn(true);

        Response response = userResource.registerPropertyRepair(1123123, "E1001", "Description", "Short Description", "4");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testRegisterPropertyRepairWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.registerPropertyRepair(123, "123456", "Description", "Short Description", RepairType.PLUMBING)).thenReturn(false);

        Response response = userResource.registerPropertyRepair(123, "123456", "Description", "Short Description", "4");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testGetProperties() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        List<Property> properties = Arrays.asList(new Property());
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
        List<Property> properties = new ArrayList<>();
        when(propertyOwnerServices.getProperties(123)).thenReturn(properties);

        RestApiResult result = userResource.getProperties(123);
        assertEquals(404, result.getErrorCode());
        assertEquals("UnSuccessful", result.getDescription());
    }

    @Test
    public void testAcceptRepair() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.acceptOrDeclineRepair(123, 1, true)).thenReturn(true);

        Response response = userResource.acceptOrDeclineRepair(123, 1, true);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testDeclineRepair() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.acceptOrDeclineRepair(123, 1, false)).thenReturn(true);

        Response response = userResource.acceptOrDeclineRepair(123, 1, false);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testAcceptOrDeclineRepairWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.acceptOrDeclineRepair(123, 1, true)).thenReturn(false);

        Response response = userResource.acceptOrDeclineRepair(123, 1, true);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testGetRepairStatus() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        List<PropertyRepair> repairs = Arrays.asList(new PropertyRepair());
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
    public void testCorrectOwnerUsernameSuccess() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerUsername(123, "newUsername")).thenReturn(true);

        Response response = userResource.correctOwnerUsername(123, "newUsername");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectOwnerUsernameFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerUsername(123, "newUsername")).thenReturn(false);

        Response response = userResource.correctOwnerUsername(123, "newUsername");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectOwnerEmailSuccess() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerEmail(123, "new@email.com")).thenReturn(true);

        Response response = userResource.correctOwnerEmail(123, "new@email.com");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectOwnerEmailFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerEmail(123, "new@email.com")).thenReturn(false);
        Response response = userResource.correctOwnerEmail(123, "new@email.com");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testCorrectOwnerPasswordSuccess() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerPassword(123, "newPassword")).thenReturn(true);

        Response response = userResource.correctOwnerPassword(123, "newPassword");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testCorrectOwnerPasswordFailure() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.correctOwnerPassword(123, "newPassword")).thenReturn(false);

        Response response = userResource.correctOwnerPassword(123, "newPassword");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testSafelyDeleteProperty() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeleteProperty(123, "123456")).thenReturn(true);
        Response response = userResource.safelyDeleteProperty(123, "123456");
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testSafelyDeletePropertyWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeleteProperty(123, "123456")).thenReturn(false);
        Response response = userResource.safelyDeleteProperty(123, "123456");
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testSafelyDeletePropertyRepair() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyRepair(123, 1)).thenReturn(true);
        Response response = userResource.safelyDeletePropertyRepair(123, 1);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testSafelyDeletePropertyRepairWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyRepair(123, 1)).thenReturn(false);
        Response response = userResource.safelyDeletePropertyRepair(123, 1);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist", response.getEntity());
    }

    @Test
    public void testSafelyDeletePropertyOwner() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyOwner(123)).thenReturn(true);
        Response response = userResource.safelyDeletePropertyOwner(123);
        assertEquals(200, response.getStatus());
        assertEquals("Successful", response.getEntity());
    }

    @Test
    public void testSafelyDeletePropertyOwnerWithWrongData() {
        UserResource userResource = new UserResource();
        PropertyOwnerServices propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        when(propertyOwnerServices.safelyDeletePropertyOwner(123)).thenReturn(false);
        Response response = userResource.safelyDeletePropertyOwner(123);
        assertEquals(404, response.getStatus());
        assertEquals("Doesn't exist",response.getEntity());
    }
}
