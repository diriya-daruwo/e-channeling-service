/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ucsc.msc.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author lana
 */
@Path("/message")
public class MediTestService {
    
    @GET
    @Path("string/{param}")
    public Response printMessage(@PathParam("param") String msg) {
        String result = "Restful example : " + msg;
        return Response.status(200).entity(result).build();
 
    }
    
    @GET
    @Path("{id : \\d+}")
    public Response getByUserId(@PathParam("id") String id){
        return Response.status(200).entity("getUserById is called, id : " + id).build();
    }
    
    @GET
    @Path("/query")
    public Response getQueryParam(@QueryParam("id") int id){
        return Response.status(200).entity("The query parament : "+id).build();
    }
}
