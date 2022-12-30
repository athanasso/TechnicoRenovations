package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    public Response registerProperty(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9,
            @FormParam("address") String address,
            @FormParam("constructionYear") int constructionYear,
            @FormParam("propertyType") String propertyType) {
        try {
            propertyOwnerServices.registerProperty(vatNumber, inputHandler.e9(e9), inputHandler.address(address), inputHandler.constructionYear(constructionYear), inputHandler.selectPropertyType(propertyType));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_address")
    @Consumes("application/json")
    public Response correctPropertyAddress(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("address") String address) {
        try {
            propertyOwnerServices.correctPropertyAddress(vatNumber, propertyId, inputHandler.address(address));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_type")
    @Consumes("application/json")
    public Response correctPropertyType(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("propertyType") String propertyType) {
        try {
            propertyOwnerServices.correctPropertyType(vatNumber, propertyId, inputHandler.selectPropertyType(propertyType));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_year")
    @Consumes("application/json")
    public Response correctPropertyconstructionYear(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("constructionYear") int year) {
        try {
            propertyOwnerServices.correctPropertyconstructionYear(vatNumber, propertyId, inputHandler.constructionYear(year));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/create_property_repair")
    @Consumes("application/json")
    public Response registerPropertyRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9,
            @FormParam("description") String description,
            @FormParam("shortDescription") String shortDescription,
            @FormParam("propertyType") String repairType) {
        try {
            propertyOwnerServices.registerPropertyRepair(vatNumber, e9, description, shortDescription, inputHandler.selectRepairType(repairType));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @GET
    @Path("get_properties/{vatNumber}")
    @Produces("application/json")
    public RestApiResult<List<Property>> getProperties(@PathParam("vatNumber") int vatNumber) {
        try {
            return propertyOwnerServices.getProperties(vatNumber);
        } catch (Exception e) {
            return new RestApiResult<>(null, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("property/repair_status")
    @Consumes("application/json")
    public Response acceptOrDeclineRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("repairId") int repairId,
            @FormParam("status") boolean status) {
        try {
            propertyOwnerServices.acceptOrDeclineRepair(vatNumber, repairId, status);
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @GET
    @Path("get_repair_status/{vatNumber}")
    @Produces("application/json")
    public RestApiResult<List<PropertyRepair>> getRepairStatus(@PathParam("vatNumber") int vatNumber) {
        try {
            return propertyOwnerServices.getRepairStatus(vatNumber);
        } catch (Exception e) {
            return new RestApiResult<>(null, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("property/correct_owner_username")
    @Consumes("application/json")
    public Response correctOwnerUsername(@FormParam("vatNumber") int vatNumber,
            @FormParam("username") String username) {
        try {
            propertyOwnerServices.correctOwnerUsername(vatNumber, inputHandler.names(username));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/correct_owner_email")
    @Consumes("application/json")
    public Response correctOwnerEmail(@FormParam("vatNumber") int vatNumber,
            @FormParam("email") String email) {
        try {
            propertyOwnerServices.correctOwnerEmail(vatNumber, inputHandler.email(email));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/correct_owner_password")
    @Consumes("application/json")
    public Response correctOwnerPassword(@FormParam("vatNumber") int vatNumber,
            @FormParam("password") String password) {
        try {
            propertyOwnerServices.correctOwnerPassword(vatNumber, inputHandler.password(password));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/delete_property")
    @Consumes("application/json")
    public Response safelyDeleteProperty(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9) {
        try {
            propertyOwnerServices.safelyDeleteProperty(vatNumber, inputHandler.e9(e9));
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/delete_property_repair")
    @Consumes("application/json")
    public Response safelyDeletePropertyRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("repairId") int repairId) {
        try {
            propertyOwnerServices.safelyDeletePropertyRepair(vatNumber, repairId);
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("property/delete_owner")
    @Consumes("application/json")
    public Response safelyDeletePropertyOwner(@FormParam("vatNumber") int vatNumber) {
        try {
            propertyOwnerServices.safelyDeletePropertyOwner(vatNumber);
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(null, 401, "Something went wrong!"))
                    .build();
        }
    }
}
