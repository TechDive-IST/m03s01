package org.techdive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World! !!! ";
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Resposta hello2() {
//        return new Resposta("Olá Mundo!!");
//    }

}