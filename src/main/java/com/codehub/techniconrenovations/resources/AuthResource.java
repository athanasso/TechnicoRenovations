package com.codehub.techniconrenovations.resources;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import com.codehub.techniconrenovations.services.impl.PropertyOwnerServicesImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("auth")
public class AuthResource {

    PropertyRepository propertyRepository;
    PropertyOwnerRepository propertyOwnerRepository;
    PropertyRepairRepository propertyRepairRepository;
    
    @Inject
    PropertyOwnerServices propertyOwnerServices = new PropertyOwnerServicesImpl(propertyRepository, propertyOwnerRepository, propertyRepairRepository);
    
    @POST
    @Path("login")
    public Response login(@FormParam("username") String username,
            @FormParam("password") String password) {
        try{
            propertyOwnerServices.logIn(username, password);
            return Response.status(200)
                    .entity(new RestApiResult<>(username, 200, "Successful"))
                    .build();
        } catch (Exception e) {
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "Invalid Credentials"))
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
        try {
            propertyOwnerServices.register(vatNumber, name, surname, address, phoneNumber, email, username, password);   
            return Response.status(200)
                    .entity(new RestApiResult<>(vatNumber, 200, "Successful"))
                    .build();
        } catch (Exception e){
            return Response.status(401)
                    .entity(new RestApiResult<>(e, 401, "There was a problem with your registration. Username or email already taken"))
                    .build();
        }
    }
}
