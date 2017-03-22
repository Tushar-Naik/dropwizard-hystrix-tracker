package io.dropwizard.client.filter.application;

import io.dropwizard.client.filter.filter.ClientRestriction;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author tushar.naik
 * @version 1.0  22/03/17 - 2:24 PM
 */

@Path("/service")
@Slf4j
public class TestResource {

    @Path("/api/{path}/api1")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ClientRestriction
    public Response getResponse(@PathParam("path") String path) {
        return Response.ok("Hello client " + path).build();
    }

}
