package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.resources.DataResource;
import static com.codehub.techniconrenovations.util.UtilFunctions.CheckDate;
import jakarta.inject.Inject;
import jakarta.persistence.RollbackException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import org.slf4j.LoggerFactory;
import static com.codehub.techniconrenovations.util.UtilFunctions.stringToDate;

public class DataImport {

    @Inject
    PropertyRepository propertyRepository;
    @Inject
    PropertyOwnerRepository propertyOwnerRepository;
    @Inject
    PropertyRepairRepository propertyRepairRepository;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DataResource.class);

    public void getPropertiesFile() {
        try {
            URL url = getClass().getResource("/data/Properties.csv");
            String line;
            BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
            while ((line = br.readLine()) != null) {
                String[] properties = line.split(",");
                Property property = new Property(
                        properties[0],
                        properties[1],
                        Integer.parseInt(properties[2]),
                        PropertyType.valueOf(properties[3]),
                        new PropertyOwner(Integer.parseInt(properties[4]))
                );
                propertyRepository.createProperty(property);
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void getPropertyOwnersFile() {
        try {
            URL url = getClass().getResource("/data/PropertyOwners.csv");
            String line;
            BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
            while ((line = br.readLine()) != null) {
                String[] owners = line.split(",");
                PropertyOwner propertyOwner = new PropertyOwner(
                        Integer.parseInt(owners[0]),
                        owners[1],
                        owners[2],
                        owners[3],
                        owners[4],
                        owners[5],
                        owners[6],
                        owners[7],
                        owners[8]
                );
                propertyOwnerRepository.createPropertyOwner(propertyOwner);
            }
        } catch (RollbackException | IOException | NumberFormatException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void getPropertyRepairsFile() {
        try {
            URL url = getClass().getResource("/data/PropertyRepairs.csv");
            String line;
            BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
            while ((line = br.readLine()) != null) {
                String[] repairs = line.split(",");
                PropertyRepair propertyRepair = new PropertyRepair(
                        new PropertyOwner(Integer.parseInt(repairs[0])),
                        new Property(repairs[1]),
                        RepairType.valueOf(repairs[2]),
                        stringToDate(repairs[3]),
                        repairs[4],
                        stringToDate(repairs[5]),
                        stringToDate(repairs[6]),
                        BigDecimal.valueOf(Double.parseDouble(repairs[7])),
                        repairs[8],
                        Boolean.parseBoolean(repairs[9]),
                        Status.valueOf(repairs[10]),
                        stringToDate(repairs[11]),
                        stringToDate(repairs[12])
                );
                if (CheckDate(propertyRepair.getActualStartDate())) {
                    propertyRepair.setActualStartDate(null);
                }
                if (CheckDate(propertyRepair.getActualEndDate())) {
                    propertyRepair.setActualEndDate(null);
                }
                propertyRepairRepository.createPropertyRepair(propertyRepair);
            }
        } catch (IOException | NumberFormatException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void run() {
        getPropertyOwnersFile();
        getPropertiesFile();
        getPropertyRepairsFile();
    }

}
