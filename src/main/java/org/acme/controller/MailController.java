package org.acme.controller;

import org.acme.service.MailService;
import org.acme.utils.ResponseUtils;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/mail")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MailController {
   @Inject
    MailService mailService;

    @POST
    public Response sendEmail(Map<String, Object> request) throws IOException {
        mailService.sendEmail(request.get("email").toString());
        return ResponseUtils.createResponse(true,"200","sent email has successfully");
    }
    @POST
    @Path("/excel")
    public Response sendExcelToEmail(Map<String, Object> request) throws IOException {
        mailService.sendExcelToEmail(request.get("email").toString());
        return ResponseUtils.createResponse(true,"200","sent email excel has successfully");
    }
}
