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
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.UtilFunctions;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserResourceTest {
    
    UserResource userResource;
    PropertyOwnerServices propertyOwnerServices;
    Property property;
    PropertyDto propertyDto;
    PropertyOwner propertyOwner;
    UserDto userDto;
    PropertyRepair repair;
    RepairDto repairDto;
    List<PropertyDto> properties;
    List<RepairDto> repairs;

    @Before
    public void setUp() {
        userResource = new UserResource();
        propertyOwnerServices = mock(PropertyOwnerServices.class);
        userResource.propertyOwnerServices = propertyOwnerServices;
        userResource.propertyOwnerServices = propertyOwnerServices;
        propertyOwner = new PropertyOwner(1, "name", "surname", "address", "phoneNumber", "email", "username", "password", "typeOfUser");
        userDto = new UserDto(propertyOwner);
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
    public void testRegisterProperty_duplicateEntry() {
        Response response = userResource.registerProperty(propertyDto);
        assertEquals(404, response.getStatus());
    }
    
    @Test
    public void testCorrectPropertyAddress() {    
        when(userResource.propertyOwnerServices.correctPropertyAddress(1, "E9", "123 Main St")).thenReturn(true);
        Response response = userResource.correctPropertyAddress(propertyDto);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetProperties() {
        when(propertyOwnerServices.getProperties(123)).thenReturn(properties);
        RestApiResult result = userResource.getProperties(123);
        assertEquals(200, result.getErrorCode());
        assertEquals(properties, result.getData());
    }

    @Test
    public void testGetUser() {
        RestApiResult result = userResource.getUser(123);
        assertEquals(200, result.getErrorCode());
    }

    @Test
    public void testGetRepairStatus() {
        when(propertyOwnerServices.getRepairStatus(123)).thenReturn(repairs);
        RestApiResult result = userResource.getRepairStatus(123);
        assertEquals(200, result.getErrorCode());
        assertEquals(repairs, result.getData());
    }
}