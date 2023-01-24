package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.util.DataImport;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("data")
public class DataResource {

    private static final Logger logger = LoggerFactory.getLogger(DataResource.class);

    @Inject
    DataImport dataImport;

    @POST
    @Path("import")
    @RolesAllowed({"admin"})
    public Response ImportData() {
        try {
            logger.debug("Importing data to database...");
            dataImport.run();
            logger.debug("Data imported successfully");
            return Response.status(200)
                    .entity("Successful")
                    .build();
        } catch (Exception e) {
            logger.error("Something went wrong " + e.getMessage());
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }
}
