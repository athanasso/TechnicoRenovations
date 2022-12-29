package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.services.AdminServices;
import com.codehub.techniconrenovations.util.UtilFunctions;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("admin")
public class AdminResource {
    
    @Inject
    AdminServices adminServices;
    
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
    public List<PropertyRepair> getPendingRepairs() {
        return adminServices.getPendingRepairs();
    }
    
    @POST
    @Path("propose_cost")
    @Consumes("application/json")
    public void proposeCost(@PathParam("cost") double cost,
            @PathParam("repairId") int repairId) {
        adminServices.proposeCost(cost, repairId);
    }
    
    @POST
    @Path("propose_dates")
    @Consumes("application/json")
    public void proposeStartEndDates(@PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate,
            @PathParam("repairId") int repairId) {
        adminServices.proposeStartEndDates(UtilFunctions.convertToDate(startDate), UtilFunctions.convertToDate(endDate), repairId);
    }
    
    @GET
    @Path("get_property_repairs")
    @Produces("application/json")
    public List<PropertyRepair> getAllRepairs() {
        return adminServices.getAllRepairs();
    }
    
    @GET
    @Path("get_properties")
    @Produces("application/json")
    public List<Property> getProperties() {
        return adminServices.getProperties();
    }
    
    @GET
    @Path("get_owners")
    @Produces("application/json")
    public List<PropertyOwner> getOwners() {
        return adminServices.getOwners();
    }
    
    @POST
    @Path("delete_properties")
    public void permanentlyDeleteProperties() {
        adminServices.permanentlyDeleteProperties();
    }
    
    @POST
    @Path("delete_owners")
    public void permanentlyDeletePropertyOwner() {
        adminServices.permanentlyDeletePropertyOwner();
    }
    
    @POST
    @Path("delete_repairs")
    public void permanentlyDeleteRepairs() {
        adminServices.permanentlyDeleteRepairs();
    }
}
