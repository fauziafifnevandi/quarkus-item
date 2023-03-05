package utils;

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
}
