package org.techdive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        Resposta resposta = new Resposta("Olá Mundo!!!");
        return Response.ok(resposta).build();
    }

}