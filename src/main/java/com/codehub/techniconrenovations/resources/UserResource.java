package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    @RolesAllowed({"admin","user"})
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @POST
    @Path("property/create_property")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response registerProperty(PropertyDto dto) {
        try {
            if (!propertyOwnerServices.registerProperty(dto.getOwnerVatNumber(), dto.getPropertyId(), dto.getPropertyAddress(), dto.getYearOfConstruction(), inputHandler.selectPropertyType(dto.getPropertyType()))) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Duplicate entry")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_address")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctPropertyAddress(PropertyDto dto) {
        try {
            if (!propertyOwnerServices.correctPropertyAddress(dto.getOwnerVatNumber(), dto.getPropertyId(), dto.getPropertyAddress())) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_type")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctPropertyType(PropertyDto dto) {
        try {
            if (!propertyOwnerServices.correctPropertyType(dto.getOwnerVatNumber(), dto.getPropertyId(), inputHandler.selectPropertyType(dto.getPropertyType()))) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/correct_property_year")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctPropertyconstructionYear(PropertyDto dto) {
        try {
            if (!propertyOwnerServices.correctPropertyconstructionYear(dto.getOwnerVatNumber(), dto.getPropertyId(), dto.getYearOfConstruction())) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("repair/create_property_repair")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response registerPropertyRepair(RepairDto dto) {
        try {
            if (!propertyOwnerServices.registerPropertyRepair(dto.getOwnerVatNumber(), dto.getPropertyId(), dto.getDescription(), dto.getShortDescription(), inputHandler.selectRepairType(dto.getRepairType()))) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @GET
    @Path("get_properties/{vatNumber}")
    @Produces("application/json")
    @RolesAllowed({"admin","user"})
    public RestApiResult getProperties(@PathParam("vatNumber") int vatNumber) {
        try {
            List<PropertyDto> properties = propertyOwnerServices.getProperties(vatNumber);
            if (properties.isEmpty()) {
                return new RestApiResult<>("empty", 404, "UnSuccessful");
            } else {
                return new RestApiResult<>(properties, 200, "Successful");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new RestApiResult<>(e.getMessage(), 401, "Something went wrong!");
        }
    }

    @POST
    @Path("repair/repair_status")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response acceptOrDeclineRepair(RepairDto dto) {
        try {
            if (!propertyOwnerServices.acceptOrDeclineRepair(dto.getOwnerVatNumber(), dto.getRepairId(), Boolean.parseBoolean(dto.getStatus()))) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }
    
    @POST
    @Path("repair/description")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctDescription(RepairDto dto) {
        try {
            if (!propertyOwnerServices.changeDescription(dto.getOwnerVatNumber(), dto.getRepairId(), dto.getDescription())) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @GET
    @Path("get_repair_status/{vatNumber}")
    @Produces("application/json")
    @RolesAllowed({"admin","user"})
    public RestApiResult getRepairStatus(@PathParam("vatNumber") int vatNumber) {
        try {
            List<RepairDto> repairs = propertyOwnerServices.getRepairStatus(vatNumber);
            if (repairs.isEmpty()) {
                return new RestApiResult<>("empty", 404, "UnSuccessful");
            } else {
                return new RestApiResult<>(repairs, 200, "Successful");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new RestApiResult<>(e.getMessage(), 401, "Something went wrong!");
        }
    }

    @POST
    @Path("owner/correct_owner_username")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctOwnerUsername(UserDto dto) {
        try {
            if (!propertyOwnerServices.correctOwnerUsername(dto.getVatNumber(), dto.getUsername())) {
                logger.error("user with" + dto.getVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("owner/correct_owner_email")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctOwnerEmail(UserDto dto) {
        try {
            if (!propertyOwnerServices.correctOwnerEmail(dto.getVatNumber(), inputHandler.email(dto.getEmail()))) {
                logger.error("user with" + dto.getVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("owner/correct_owner_password")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response correctOwnerPassword(UserDto dto) {
        try {
            if (!propertyOwnerServices.correctOwnerPassword(dto.getVatNumber(), dto.getPassword())) {
                logger.error("user with" + dto.getVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("property/delete_property")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response safelyDeleteProperty(PropertyDto dto) {
        try {
            if (!propertyOwnerServices.safelyDeleteProperty(dto.getOwnerVatNumber(), dto.getPropertyId())) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("repair/delete_property_repair")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response safelyDeletePropertyRepair(RepairDto dto) {
        try {
            if (!propertyOwnerServices.safelyDeletePropertyRepair(dto.getOwnerVatNumber(), dto.getRepairId())) {
                logger.error("user with" + dto.getOwnerVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getOwnerVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("owner/delete_owner")
    @Consumes("application/json")
    @RolesAllowed({"admin","user"})
    public Response safelyDeletePropertyOwner(UserDto dto) {
        try {
            if (!propertyOwnerServices.safelyDeletePropertyOwner(dto.getVatNumber())) {
                logger.error("user with" + dto.getVatNumber() + "has wrong data");
                return Response.status(404)
                        .entity("Doesn't exist")
                        .build();
            }
            logger.debug("user with" + dto.getVatNumber() + "succesful query");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }
}
