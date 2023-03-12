package org.acme.service;

import io.quarkus.panache.common.Page;
import org.acme.model.Item;
import org.acme.pages.PageRequest;
import org.acme.utils.ResponseUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BeanParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;

@ApplicationScoped
public class ItemService {
    public Response get(){
        List<Item> item = Item.findAll().list();
        return ResponseUtils.createResponse(true,"200",item);
    }

    public Response get(@PathParam("id") Long id){
        List<Item> peserta = Item.find("id",id).list();
        if(peserta.isEmpty() ){
            return ResponseUtils.createResponse(false, "400","peserta id "+!peserta.isEmpty());
        }
        return ResponseUtils.createResponse(true, "200", peserta);
    }

    public Response getItemPagination(@BeanParam PageRequest pageRequest){
        if (Item.findAll().page(Page.of(pageRequest.pageCurrent, pageRequest.pageSize)).list().isEmpty()){
            return ResponseUtils.createResponse(true,"201","out of range parameter");
        }
        List <Item> itemPagination = Item.findAll().page(Page.of(pageRequest.pageCurrent, pageRequest.pageSize)).list();
        long pageCurrent = pageRequest.pageCurrent;
        long pageSize = pageRequest.pageSize;
        long itemCount = Item.count();
        double pageTotalDouble = ceil(itemCount/pageSize);
        long pageTotal = (long)pageTotalDouble;
        return ResponseUtils.createResponsePaginate(true,"200",itemPagination,pageCurrent,pageSize,pageTotal,itemCount );
    }

    public Response count(){
        long itemCount = Item.count();
        if(itemCount == 0){
            return ResponseUtils.createResponse(false,"400","data item is empty");
        }
        return ResponseUtils.createResponse(true,"200", itemCount);
    }

    public Response getItemByName(@PathParam("name") String name){
        if (Item.find("name LIKE ?1", "%" + name + "%").count() == 0)
            return ResponseUtils.createResponse(false,"400","data item by name is empty");
        List<Item> itemName = Item.find("name LIKE ?1", "%" + name + "%").list();
        return ResponseUtils.createResponse(true, "200", itemName);
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
        item.delete();
        return ResponseUtils.createResponse(true,"200","data has been successfully deleted");
    }

    @Transactional
    public Response deleteAll(){
        Item.deleteAll();
        return ResponseUtils.createResponse(true,"200","all data has been successfully deleted");
    }
}
