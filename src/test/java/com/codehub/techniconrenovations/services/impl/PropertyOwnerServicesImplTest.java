package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertyOwnerServicesImplTest {

    @InjectMocks
    private PropertyOwnerServicesImpl propertyOwnerServices;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;

    @Mock
    private PropertyRepairRepository propertyRepairRepository;

    private List<Property> properties;
    private List<PropertyRepair> repairs;
    private PropertyOwner propertyOwner;

    @Before
    public void setUp() {
        propertyOwner = new PropertyOwner(1234567890);
        
        properties = new ArrayList<>();
        Property property = new Property();
        property.setPropertyId("12345");
        property.setPropertyAddress("123 Main St");
        property.setYearOfConstruction(2000);
        property.setPropertyOwner(propertyOwner);
        property.setPropertyType(PropertyType.DETACHED_HOUSE);
        properties.add(property);

        repairs = new ArrayList<>();
        PropertyRepair repair = new PropertyRepair();
        repair.setRepairId(1);
        repair.setProperty(property);
        repair.setDesciption("Leak in the roof");
        repair.setStatus(Status.IN_PROGRESS);
        repair.setPropertyOwner(propertyOwner);
        repair.setProperty(property);
        repairs.add(repair);
    }
    
    @Test
    public void testRegister() {
        when(propertyOwnerRepository.createPropertyOwner(any(PropertyOwner.class))).thenReturn(true);
        boolean result = propertyOwnerServices.register(1234567890, "John", "Doe", "123 Main St", "123-456-7890", "john.doe@email.com", "johndoe", "password", "user");
        System.out.println(result);
        assertTrue(result);
    }

    @Test
    public void testGetProperties() {
        when(propertyRepository.searchVat(1234567890)).thenReturn(properties);
        List<PropertyDto> result = propertyOwnerServices.getProperties(1234567890);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetRepairStatus() {
        when(propertyRepository.searchVat(1234567890)).thenReturn(properties);
        List<RepairDto> result = propertyOwnerServices.getRepairStatus(1234567890);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetRepairStatus_emptyList() {
        when(propertyRepository.searchVat(1234567890)).thenReturn(new ArrayList<>());
        List<RepairDto> result = propertyOwnerServices.getRepairStatus(1234567890);
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testRegisterProperty() {
        when(propertyRepository.searchPropertyId("12345")).thenReturn(null);
        boolean result = propertyOwnerServices.registerProperty(1234567890, "12345", "123 Main St", 2000, PropertyType.DETACHED_HOUSE);
        assertTrue(result);
    }
}
