package org.acme.controller;

import org.acme.model.Item;
import org.acme.service.ItemService;
import utils.ResponseUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {
    @Inject
    ItemService itemService;
    @GET
    public Response get() {
        return itemService.get();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        return itemService.get(id);
    }

    @POST
    @Transactional
    public Response post(Map<String, Object> request){
        return itemService.post(request);
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request){
        return itemService.put(id, request);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id){
        return itemService.delete(id);
    }
}
