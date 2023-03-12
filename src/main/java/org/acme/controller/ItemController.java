package org.acme.controller;

import com.opencsv.exceptions.CsvValidationException;
import net.sf.jasperreports.engine.JRException;
import org.acme.pages.PageRequest;
import org.acme.service.ExportService;
import org.acme.service.ImportService;
import org.acme.service.ItemService;
import org.acme.utils.FileFormItem;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {
    @Inject
    ItemService itemService;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @GET
    public Response get() {
        return itemService.get();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id){
        return itemService.get(id);
    }

    @GET
    @Path("/count")
    public Response count(){
        return itemService.count();
    }

    @GET
    @Path("/{name}")
    public Response getItemByName(@PathParam("name") String name){
        return itemService.getItemByName(name);
    }

    @GET
    @Path("/pagination")
    public Response getItemPagination(@BeanParam PageRequest pageRequest){
        return itemService.getItemPagination(pageRequest);
    }

    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response exportPdf() throws JRException {
        return exportService.exportPdfItem();
    }

    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {
        return exportService.exportExcelItem();
    }

    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCsv() throws IOException {
        return exportService.exportCsvItem();
    }

    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormItem request) {
        try{
            return importService.importExcel(request);
        } catch (IOException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormItem request) {
        try{
            return importService.importCSV(request);
        } catch (IOException | CsvValidationException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
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

    @DELETE
    @Path("/destroyall")
    @Transactional
    public Response deleteAll(){
        return itemService.deleteAll();
    }
}
