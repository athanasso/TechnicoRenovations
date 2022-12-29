package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("auth")
public class AuthResource {

    @Inject
    PropertyOwnerServices propertyOwnerServices;
    
    @POST
    @Path("login")
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password) {
        if (propertyOwnerServices.logIn(username, password)) {
            return Response.status(0)
                    .entity(new RestApiResult<>(username, 0, "Successful"))
                    .build();
        } else {
            return Response.status(300)
                    .entity(new RestApiResult<>(null, 300, "Invalid Credentials"))
                    .build();
        }
    }

    @POST
    @Path("register")
    public Response register(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("firstName") String name,
            @FormParam("lastName") String surname,
            @FormParam("email") String email,
            @FormParam("address") String address,
            @FormParam("phoneNumber") String phoneNumber,
            @FormParam("vatNumber") int vatNumber) {
        if (propertyOwnerServices.register(vatNumber, name, surname, address, phoneNumber, email, username, password)) {
            return Response.status(0)
                    .entity(new RestApiResult<>(vatNumber, 0, "Successful"))
                    .build();
        } else {
            return Response.status(400)
                    .entity(new RestApiResult<>(null, 400, "There was a problem with your registration. Username or email already taken"))
                    .build();
        }
    }
}
