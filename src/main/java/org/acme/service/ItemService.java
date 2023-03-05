package org.acme.service;

import org.acme.model.Item;
import utils.ResponseUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ItemService {
    public Response get(){
        List<Item> item = Item.findAll().list();
        return ResponseUtils.createResponse(true,"200",item);
    }

    @Transactional
    public Response post(Map<String, Object> request){
        if(request.get("name") == null || request.get("count") == null || request.get("price") == null || request.get("type") == null || request.get("description") == null ){
            return ResponseUtils.createResponse(false,"400","incomplete attribute");
        }
        Item item = new Item();
        item.name = request.get("name").toString();
        item.count = request.get("count").toString();
        item.price = request.get("price").toString();
        item.type = request.get("type").toString();
        item.description = request.get("description").toString();
        item.createdAt = LocalDateTime.now();
        item.updatedAt = LocalDateTime.now();
        item.persist();
        return ResponseUtils.createResponse(true,"201","data created successfully");
    }

    @Transactional
    public Response put(Long id, Map<String, Object> request){
        Item item = Item.findById(id);
        if(item == null){
            return ResponseUtils.createResponse(false,"400","item id not found");
        }
        if(request.get("name") == null || request.get("count") == null || request.get("price") == null || request.get("type") == null || request.get("description") == null ){
            return ResponseUtils.createResponse(false,"400","incomplete attribute");
        }
        item.name = request.get("name").toString();
        item.name = request.get("name").toString();
        item.count = request.get("count").toString();
        item.price = request.get("price").toString();
        item.type = request.get("type").toString();
        item.description = request.get("description").toString();
        item.updatedAt = LocalDateTime.now();
        item.persist();
        return ResponseUtils.createResponse(true,"200","data updated successfully");
    }

    @Transactional
    public Response delete(Long id){
        Item item = Item.findById(id);
        if(item == null){
            return ResponseUtils.createResponse(false,"400","item id not found");
        }
        //save to database
        item.delete();
        return ResponseUtils.createResponse(true,"200","data has been successfully deleted");
    }
}
