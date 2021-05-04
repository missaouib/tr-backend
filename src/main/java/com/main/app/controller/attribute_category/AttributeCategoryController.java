package com.main.app.controller.attribute_category;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.attribute_category.AttributeCategoryDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.product.Product;
import com.main.app.service.attribute_category.AttributeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/attributecategory")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeCategoryController {

    private final AttributeCategoryService attributeCategoryService;


    @GetMapping(path="/all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeCategory>> getAll(Pageable pageable){
        return new ResponseEntity<>(attributeCategoryService.getAll(pageable), HttpStatus.OK);
    }


    @PostMapping(path = "/add")
    public ResponseEntity<Entities<AttributeCategoryDTO>> add(@RequestBody AttributeCategoryDTO attributeCategoryDTO){
        return new ResponseEntity<>(attributeCategoryService.save(attributeCategoryDTO), HttpStatus.OK);
    }

    @PutMapping(path = "/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AttributeCategory>> edit(@RequestBody AttributeCategoryDTO attributeCategoryDTO){
        return new ResponseEntity<>(attributeCategoryService.edit(attributeCategoryDTO),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{name}/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeCategory> deleteAttr(@PathVariable String name, @PathVariable Long id){
        return new ResponseEntity<>(attributeCategoryService.deleteAttribute(name,id),HttpStatus.OK);
    }


    @DeleteMapping(path = "/{name}")
    public ResponseEntity<List<AttributeCategory>> delete(@PathVariable String name){
        return new ResponseEntity<>(attributeCategoryService.delete(name),HttpStatus.OK);
    }


    @GetMapping(path = "/search")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeCategory>> getAllBySearchParam(Pageable pageable,  @RequestParam(name = "name") String name , @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeCategoryService.getAllBySearchParam(name,searchParam,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/searchUnique")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeCategory>> searchUnique(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeCategoryService.getAllSearchUnique(searchParam,pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/name/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeCategory>> getAllByCategoryName(Pageable pageable, @PathVariable String id){
        return new ResponseEntity<>(attributeCategoryService.getAllByAttributeCategoryName(id,pageable),HttpStatus.OK);
    }

}
