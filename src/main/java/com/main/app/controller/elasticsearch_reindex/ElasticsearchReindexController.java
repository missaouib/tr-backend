package com.main.app.controller.elasticsearch_reindex;

import com.main.app.service.elasticsearch_reindex.ElasticsearchReindexService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reindex")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticsearchReindexController {

    private final ElasticsearchReindexService elasticsearchReindexService;

    @GetMapping(path="/all")
    public ResponseEntity<Void> reindexAll(){
        elasticsearchReindexService.reindexAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/users")
    public ResponseEntity<Void> reindexUsers(){
        elasticsearchReindexService.reindexUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path="/category")
    public ResponseEntity<Void> reindexCategories(){
        elasticsearchReindexService.reindexCategory();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/attribute")
    public ResponseEntity<Void> reindexAttribute(){
        elasticsearchReindexService.reindexAttribute();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/attributeValue")
    public ResponseEntity<Void> reindexAttributeValue(){
        elasticsearchReindexService.reindexAttributeValue();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path="/attributeCategory")
    public ResponseEntity<Void> reindexAttributeCategory(){
        elasticsearchReindexService.reindexAttributeCategory();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/brand")
    public ResponseEntity<Void> reindexBrand(){
        elasticsearchReindexService.reindexBrand();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/product")
    public ResponseEntity<Void> reindexProducts(){
        elasticsearchReindexService.reindexProduct();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path="/variation")
    public ResponseEntity<Void> reindexVariations(){
        elasticsearchReindexService.reindexVariation();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/orders")
    public ResponseEntity<Void> reindexOrders(){
        elasticsearchReindexService.reindexOrders();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

