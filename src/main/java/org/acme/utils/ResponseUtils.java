package org.acme.utils;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Response createResponse(boolean success, String message, Object data){
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("code", message);
        response.put("data", data);
        if (success) {
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }
    public static Response createResponsePaginate(boolean success, String message, Object data, long pageCurrent, long pageSize, long pageTotal, long totalItem){
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("code", message);
        response.put("data", data);
        response.put("pageCurrent", pageCurrent);
        response.put("pageSize", pageSize);
        response.put("pageTotal", pageTotal);
        response.put("totalItem", totalItem);
        if (success) {
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }
}
