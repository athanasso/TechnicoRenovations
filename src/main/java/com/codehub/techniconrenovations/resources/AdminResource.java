package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.services.AdminServices;
import com.codehub.techniconrenovations.services.impl.AdminServicesImpl;
import com.codehub.techniconrenovations.util.UtilFunctions;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("admin")
public class AdminResource {

    private PropertyRepository propertyRepository;
    private PropertyOwnerRepository propertyOwnerRepository;
    private PropertyRepairRepository propertyRepairRepository;

    private final AdminServices adminServices = new AdminServicesImpl(propertyRepository, propertyOwnerRepository, propertyRepairRepository);

    @GET
    @Path("ping")
    public Response ping() {
        return Response
                .ok("pang")
                .build();
    }

    @GET
    @Path("get_pending_repairs")
    @Produces("application/json")
    public RestApiResult getPendingRepairs() {
        try {
            return new RestApiResult<>(adminServices.getPendingRepairs(), 200, "Succesful!");
        } catch (Exception e) {
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("propose_cost")
    @Consumes("application/json")
    public Response proposeCost(@PathParam("cost") double cost,
            @PathParam("repairId") int repairId) {
        try {
            adminServices.proposeCost(cost, repairId);
            return Response.status(200)
                    .entity(new RestApiResult<>(repairId, 200, "Successful!"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("propose_dates")
    @Consumes("application/json")
    public Response proposeStartEndDates(@PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @PathParam("repairId") int repairId) {
        try {
            adminServices.proposeStartEndDates(UtilFunctions.convertToDate(startDate), UtilFunctions.convertToDate(endDate), repairId);
            return Response.status(200)
                    .entity(new RestApiResult<>(repairId, 200, "Successful!"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Something went wrong!"))
                    .build();
        }
    }

    @GET
    @Path("get_property_repairs")
    @Produces("application/json")
    public RestApiResult getAllRepairs() {
        try {
            return new RestApiResult<>(adminServices.getAllRepairs(), 200, "Successful!");
        } catch (Exception e) {
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @GET
    @Path("get_properties")
    @Produces("application/json")
    public RestApiResult getProperties() {
        try {
            return new RestApiResult<>(adminServices.getProperties(), 200, "Successful!");
        } catch (Exception e) {
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @GET
    @Path("get_owners")
    @Produces("application/json")
    public RestApiResult getOwners() {
        try {
            return new RestApiResult<>(adminServices.getOwners(), 200, "Successful!");
        } catch (Exception e) {
            return new RestApiResult<>(e, 401, "Something went wrong!");
        }
    }

    @POST
    @Path("delete_properties")
    public Response permanentlyDeleteProperties() {
        try {
            adminServices.permanentlyDeleteProperties();
            return Response.status(200)
                    .entity(new RestApiResult<>(null, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("delete_owners")
    public Response permanentlyDeletePropertyOwner() {
        try {
            adminServices.permanentlyDeletePropertyOwner();
            return Response.status(200)
                    .entity(new RestApiResult<>(null, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Something went wrong!"))
                    .build();
        }
    }

    @POST
    @Path("delete_repairs")
    public Response permanentlyDeleteRepairs() {
        try {
            adminServices.permanentlyDeleteRepairs();
            return Response.status(200)
                    .entity(new RestApiResult<>(null, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Something went wrong!"))
                    .build();
        }
    }
}
