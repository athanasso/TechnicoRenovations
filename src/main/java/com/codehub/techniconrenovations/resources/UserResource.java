package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("user")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    InputHandler inputHandler = new InputHandler();

    @Inject
    PropertyOwnerServices propertyOwnerServices;

    @GET
    @Path("ping")
    @RolesAllowed({"user"})
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @POST
    @Path("property/create_property")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response registerProperty(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9,
            @FormParam("address") String address,
            @FormParam("constructionYear") int constructionYear,
            @FormParam("propertyType") String propertyType) {
        try {
            if (!propertyOwnerServices.registerProperty(vatNumber, e9, address, constructionYear, inputHandler.selectPropertyType(propertyType))) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_address")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctPropertyAddress(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("address") String address) {
        try {
            if (!propertyOwnerServices.correctPropertyAddress(vatNumber, propertyId, address)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_type")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctPropertyType(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("propertyType") String propertyType) {
        try {
            if (!propertyOwnerServices.correctPropertyType(vatNumber, propertyId, inputHandler.selectPropertyType(propertyType))) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_year")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctPropertyconstructionYear(@FormParam("vatNumber") int vatNumber,
            @FormParam("propertyId") String propertyId,
            @FormParam("constructionYear") int year) {
        try {
            if (!propertyOwnerServices.correctPropertyconstructionYear(vatNumber, propertyId, year)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/create_property_repair")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response registerPropertyRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9,
            @FormParam("description") String description,
            @FormParam("shortDescription") String shortDescription,
            @FormParam("propertyType") String repairType) {
        try {
            if (!propertyOwnerServices.registerPropertyRepair(vatNumber, e9, description, shortDescription, inputHandler.selectRepairType(repairType))) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @GET
    @Path("get_properties/{vatNumber}")
    @Produces("application/json")
    @RolesAllowed({"user"})
    public RestApiResult getProperties(@PathParam("vatNumber") int vatNumber) {
        try {
            List<PropertyDto> properties = propertyOwnerServices.getProperties(vatNumber);
            if (properties.isEmpty()) {
                return new RestApiResult<>("empty", 404, "UnSuccessful");
            } else {
                return new RestApiResult<>(properties, 200, "Successful");
            }
        } catch (Exception e) {
            logger.error("" + e);
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("property/repair_status")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response acceptOrDeclineRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("repairId") int repairId,
            @FormParam("status") boolean status) {
        try {
            if (!propertyOwnerServices.acceptOrDeclineRepair(vatNumber, repairId, status)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @GET
    @Path("get_repair_status/{vatNumber}")
    @Produces("application/json")
    @RolesAllowed({"user"})
    public RestApiResult getRepairStatus(@PathParam("vatNumber") int vatNumber) {
        try {
            List<RepairDto> repairs = propertyOwnerServices.getRepairStatus(vatNumber);
            if (repairs.isEmpty()) {
                return new RestApiResult<>("empty", 404, "UnSuccessful");
            } else {
                return new RestApiResult<>(repairs, 200, "Successful");
            }
        } catch (Exception e) {
            logger.error("" + e);
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("property/correct_owner_username")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctOwnerUsername(@FormParam("vatNumber") int vatNumber,
            @FormParam("username") String username) {
        try {
            if (!propertyOwnerServices.correctOwnerUsername(vatNumber, username)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_owner_email")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctOwnerEmail(@FormParam("vatNumber") int vatNumber,
            @FormParam("email") String email) {
        try {
            if (!propertyOwnerServices.correctOwnerEmail(vatNumber, inputHandler.email(email))) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_owner_password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response correctOwnerPassword(@FormParam("vatNumber") int vatNumber,
            @FormParam("password") String password) {
        try {
            if (!propertyOwnerServices.correctOwnerPassword(vatNumber, password)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/delete_property")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response safelyDeleteProperty(@FormParam("vatNumber") int vatNumber,
            @FormParam("e9") String e9) {
        try {
            if (!propertyOwnerServices.safelyDeleteProperty(vatNumber, e9)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/delete_property_repair")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response safelyDeletePropertyRepair(@FormParam("vatNumber") int vatNumber,
            @FormParam("repairId") int repairId) {
        try {
            if (!propertyOwnerServices.safelyDeletePropertyRepair(vatNumber, repairId)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/delete_owner")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"user"})
    public Response safelyDeletePropertyOwner(@FormParam("vatNumber") int vatNumber) {
        try {
            if (!propertyOwnerServices.safelyDeletePropertyOwner(vatNumber)) {
                logger.error("user with" + vatNumber + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + vatNumber + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }
}
