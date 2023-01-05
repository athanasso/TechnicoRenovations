package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("auth")
public class AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);

    InputHandler inputHandler = new InputHandler();

    @Inject
    PropertyOwnerServices propertyOwnerServices;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PermitAll
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password) {
        try {
            PropertyOwner p = propertyOwnerServices.logIn(username, password);
            if (p != null) {
                if (p.getTypeOfUser() == "user") {
                    //return page for user
                } else if (p.getTypeOfUser() == "admin") {
                    //return page for admin
                }
                return Response.status(200)
                        .entity("Successful")
                        .build();
            } else {
                logger.debug("wrong credentials inputed");
                return Response.status(404)
                        .entity("Invalid Credentials")
                        .build();
            }
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("Something went wrong!")
                    .build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PermitAll
    public Response register(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("email") String email,
            @FormParam("address") String address,
            @FormParam("phoneNumber") String phoneNumber,
            @FormParam("vatNumber") int vatNumber) {
        try {
            if (propertyOwnerServices.register(vatNumber, name, surname, address, inputHandler.phoneNumber(phoneNumber), email, username, password, "user")) {
                return Response.status(200)
                        .entity("Successful")
                        .build();
            } else {
                logger.debug("wrong credentials inputed");
                return Response.status(404)
                        .entity("Invalid Credentials")
                        .build();
            }
        } catch (Exception e) {
            logger.error("" + e);
            return Response.status(401)
                    .entity("There was a problem with your registration. Username or email already taken")
                    .build();
        }
    }
}
