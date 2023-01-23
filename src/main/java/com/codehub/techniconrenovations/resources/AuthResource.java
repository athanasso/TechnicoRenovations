package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.util.InputHandler;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    @Consumes("application/json")
    @Produces("application/json")
    @PermitAll
    public PropertyOwner login(UserDto dto) {
        try {
            PropertyOwner p = propertyOwnerServices.logIn(dto.getUsername(), dto.getPassword());
            if (p != null) {
                Response.status(200)
                        .entity("Successful")
                        .build();
                return p;
            } else {
                logger.debug("wrong credentials inputed");
                Response.status(404)
                        .entity("Invalid Credentials")
                        .build();
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            Response.status(401)
                    .entity("Something went wrong!")
                    .build();
            return null;
        }
    }

    @POST
    @Path("register")
    @Consumes("application/json")
    @PermitAll
    public Response register(UserDto dto) {
        try {
            if (propertyOwnerServices.register(dto.getVatNumber(), dto.getName(), dto.getSurname(), dto.getAddress(), dto.getPhoneNumber(), dto.getEmail(), dto.getUsername(), dto.getPassword(), "user")) {
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
            logger.error(e.getMessage());
            return Response.status(401)
                    .entity("There was a problem with your registration. Username or email already taken")
                    .build();
        }
    }
}
