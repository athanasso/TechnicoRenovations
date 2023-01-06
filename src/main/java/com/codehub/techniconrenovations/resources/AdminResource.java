package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.AdminServices;
import com.codehub.techniconrenovations.util.UtilFunctions;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
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

@Path("admin")
public class AdminResource {

    private static final Logger logger = LoggerFactory.getLogger(AdminResource.class);

    @Inject
    AdminServices adminServices;

    @GET
    @Path("ping")
    @RolesAllowed({"admin"})
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @GET
    @Path("get_pending_repairs")
    @Produces("application/json")
    @RolesAllowed({"admin"})
    public RestApiResult getPendingRepairs() {
        try {
            List<RepairDto> repairs = adminServices.getPendingRepairs();
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
    @Path("propose_cost")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"admin"})
    public Response proposeCost(@PathParam("cost") double cost,
            @PathParam("repairId") int repairId) {
        try {
            adminServices.proposeCost(cost, repairId);
            return Response.status(200)
                    .entity("Successful!")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("propose_dates")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @RolesAllowed({"admin"})
    public Response proposeStartEndDates(@PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @PathParam("repairId") int repairId) {
        try {
            adminServices.proposeStartEndDates(UtilFunctions.stringToDate(startDate), UtilFunctions.stringToDate(endDate), repairId);
            return Response.status(200)
                    .entity("Successful!")
                    .build();
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @GET
    @Path("get_property_repairs")
    @Produces("application/json")
    @RolesAllowed({"admin"})
    public RestApiResult getAllRepairs() {
        try {
            List<PropertyRepair> repairs = adminServices.getAllRepairs();
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

    @GET
    @Path("get_properties")
    @Produces("application/json")
    @RolesAllowed({"admin"})
    public RestApiResult getProperties() {
        try {
            List<Property> properties = adminServices.getProperties();
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

    @GET
    @Path("get_owners")
    @Produces("application/json")
    @RolesAllowed({"admin"})
    public RestApiResult getOwners() {
        try {
            List<UserDto> users = adminServices.getOwners();
            if (users.isEmpty()) {
                return new RestApiResult<>("empty", 404, "UnSuccessful");
            } else {
                return new RestApiResult<>(users, 200, "Successful");
            }
        } catch (Exception e) {
            logger.error("" + e);
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("delete_properties")
    @RolesAllowed({"admin"})
    public Response permanentlyDeleteProperties() {
        try {
            adminServices.permanentlyDeleteProperties();
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
    @Path("delete_owners")
    @RolesAllowed({"admin"})
    public Response permanentlyDeletePropertyOwner() {
        try {
            adminServices.permanentlyDeletePropertyOwner();
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
    @Path("delete_repairs")
    @RolesAllowed({"admin"})
    public Response permanentlyDeleteRepairs() {
        try {
            adminServices.permanentlyDeleteRepairs();
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
