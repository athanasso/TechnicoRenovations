package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserResource {
    
    @Inject 
    InputHandler inputHandler = new InputHandler();

    @Inject
    PropertyOwnerServices propertyOwnerServices;

    @GET
    @Path("ping")
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @POST
    @Path("property/create_property")
    @Consumes("application/json")
    public void registerProperty(@PathParam("vatNumber") int vatNumber,
            @PathParam("e9") String e9,
            @PathParam("address") String address,
            @PathParam("constructionYear") int constructionYear,
            @PathParam("propertyType") String propertyType) {
        propertyOwnerServices.registerProperty(vatNumber, inputHandler.e9(e9), inputHandler.address(address), inputHandler.constructionYear(constructionYear), inputHandler.selectPropertyType(propertyType));
    }
    
    @POST
    @Path("property/correct_property_address")
    @Consumes("application/json")
    public void correctPropertyAddress(@PathParam("vatNumber") int vatNumber,
            @PathParam("propertyId") String propertyId,
            @PathParam("address") String address) {
        propertyOwnerServices.correctPropertyAddress(vatNumber, propertyId, inputHandler.address(address));
    }
    
    @POST
    @Path("property/correct_property_type")
    @Consumes("application/json")
    public void correctPropertyType(@PathParam("vatNumber") int vatNumber,
            @PathParam("propertyId") String propertyId,
            @PathParam("propertyType") String propertyType) {
        propertyOwnerServices.correctPropertyType(vatNumber, propertyId, inputHandler.selectPropertyType(propertyType));
    }
    
    @POST
    @Path("property/correct_property_year")
    @Consumes("application/json")
    public void correctPropertyconstructionYear(@PathParam("vatNumber") int vatNumber,
            @PathParam("propertyId") String propertyId,
            @PathParam("constructionYear") int year) {
        propertyOwnerServices.correctPropertyconstructionYear(vatNumber, propertyId, inputHandler.constructionYear(year));
    }
    
    @POST
    @Path("property/create_property_repair")
    @Consumes("application/json")
    public void registerPropertyRepair(@PathParam("vatNumber") int vatNumber,
            @PathParam("e9") String e9,
            @PathParam("description") String description,
            @PathParam("shortDescription") String shortDescription,
            @PathParam("propertyType") String repairType) {
        propertyOwnerServices.registerPropertyRepair(vatNumber, e9, description, shortDescription, inputHandler.selectRepairType(repairType));
    }
    
    @GET
    @Path("get_properties/{vatNumber}")
    @Produces("application/json")
    public List<Property> getProperties(@QueryParam("vatNumber") int vatNumber) {
        return propertyOwnerServices.getProperties(vatNumber);
    }
    
    @POST
    @Path("property/repair_status")
    @Consumes("application/json")
    public void acceptOrDeclineRepair(@PathParam("vatNumber") int vatNumber,
            @PathParam("repairId") int repairId,
            @PathParam("status") boolean status) {
        propertyOwnerServices.acceptOrDeclineRepair(vatNumber, repairId, status);
    }
    
    @GET
    @Path("get_repair_status/{vatNumber}")
    @Produces("application/json")
    public List<PropertyRepair> getRepairStatus(@QueryParam("vatNumber") int vatNumber) {
        return propertyOwnerServices.getRepairStatus(vatNumber);
    }
    
    @POST
    @Path("property/correct_owner_username")
    @Consumes("application/json")
    public void correctOwnerUsername(@PathParam("vatNumber") int vatNumber,
            @PathParam("username") String username) {
        propertyOwnerServices.correctOwnerUsername(vatNumber, inputHandler.names(username));
    }
    
    @POST
    @Path("property/correct_owner_email")
    @Consumes("application/json")
    public void correctOwnerEmail(@PathParam("vatNumber") int vatNumber,
            @PathParam("email") String email) {
        propertyOwnerServices.correctOwnerEmail(vatNumber, inputHandler.email(email));
    }
    
    @POST
    @Path("property/correct_owner_password")
    @Consumes("application/json")
    public void correctOwnerPassword(@PathParam("vatNumber") int vatNumber,
            @PathParam("password") String password) {
        propertyOwnerServices.correctOwnerPassword(vatNumber, inputHandler.password(password));
    }
    
    @POST
    @Path("property/delete_property")
    @Consumes("application/json")
    public void safelyDeleteProperty(@PathParam("vatNumber") int vatNumber,
            @PathParam("e9") String e9) {
        propertyOwnerServices.safelyDeleteProperty(vatNumber, inputHandler.e9(e9));
    }
    
    @POST
    @Path("property/delete_property_repair")
    @Consumes("application/json")
    public void safelyDeletePropertyRepair(@PathParam("vatNumber") int vatNumber,
            @PathParam("repairId") int repairId) {
        propertyOwnerServices.safelyDeletePropertyRepair(vatNumber, repairId);
    }
    
    @POST
    @Path("property/delete_owner")
    @Consumes("application/json")
    public void safelyDeletePropertyOwner(@PathParam("vatNumber") int vatNumber) {
        propertyOwnerServices.safelyDeletePropertyOwner(vatNumber);
    }
}
