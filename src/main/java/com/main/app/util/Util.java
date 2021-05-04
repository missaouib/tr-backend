package com.main.app.util;

import com.main.app.domain.dto.attribute_category.AttributeCategoryDTO;
import com.main.app.domain.dto.attribute_category.AttributeCategoryUniqueDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.comment.Comment;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.elastic.dto.EntityElasticDTO;
import com.main.app.elastic.dto.attribute.AttributeElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryUniqueElasticDTO;
import com.main.app.elastic.dto.attribute_value.AttributeValueElasticDTO;
import com.main.app.elastic.dto.brand.BrandElasticDTO;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.dto.order.OrdersElasticDTO;
import com.main.app.elastic.dto.product.ProductElasticDTO;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.dto.variation.VariationElasticDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Util {

    public static String generateUniqueString() {
        return UUID.randomUUID().toString();
    }

    public static List<Long> entitiesToIds(Page<EntityElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> usersToIds(Page<UserElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> adminUsersToIds(Page<User> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> categoriesToIds(Page<CategoryElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> attributesToIds(Page<AttributeElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> attrToIds(Page<Attribute> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> attributeValuesToIds(Page<AttributeValueElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> attrValuesToIds(Page<AttributeValue> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> brandsToIds(Page<BrandElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> productsToIds(Page<ProductElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }


    public static List<Long> variationsToIds(Page<VariationElasticDTO> entities) {
        List<Long> ids = new ArrayList<>();
        entities.forEach(item -> {
            ids.add(item.getId());
        });
        return ids;
    }

    public static List<Long> ordersToIds(Page<CustomerOrder> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> dtoOrdersToIds(Page<OrdersElasticDTO> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> favouritesToIds(Page<UserFavourites> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> commentToIds(Page<Comment> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> attributeCategoryToIdsObject(Page<AttributeCategory> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }


    public static List<Long> attributeCategoryToIds(Page<AttributeCategoryElasticDTO> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> uniqueToIds(ArrayList<AttributeCategoryUniqueDTO> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

    public static List<Long> uniqueElasticToIds(Page<AttributeCategoryUniqueElasticDTO> entities){
        List<Long> ids = new ArrayList<>();
        entities.forEach(item->{
            ids.add(item.getId());
        });
        return  ids;
    }

}
