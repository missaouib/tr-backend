package com.main.app.controller.variation;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.variation.VariationDTO;
import com.main.app.domain.model.variation.Variation;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.main.app.converter.variation.VariationConverter.DTOtoEntity;
import static com.main.app.converter.variation.VariationConverter.entityToDTO;


@RestController
@RequestMapping("/variation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VariationController {

    private final VariationService variationService;

    @GetMapping(path="/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Variation>> getAll(){
        return new ResponseEntity<>(variationService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Variation>> getAllBySearchParam(Pageable pageable,
                                                                   @RequestParam(name = "searchParam") String searchParam,
                                                                   @RequestParam(name = "productId") String productId){
        return new ResponseEntity<>(variationService.getAllBySearchParam(searchParam, productId, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VariationDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(variationService.getOne(id)), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VariationDTO> edit(@RequestBody VariationDTO variationDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(variationService.edit(DTOtoEntity(variationDTO), id)), HttpStatus.OK);
    }





    @PostMapping(path = "/image/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadImage(@PathVariable Long id, @RequestPart MultipartFile[] images) throws IOException {
        variationService.uploadImage(id, images);
    }

    @PutMapping(path = "/toggleactive/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VariationDTO> toggleActive(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(variationService.toggleActivate(id)), HttpStatus.OK);
    }







    @GetMapping(path = "/attributes/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<String>> getAttributeNamesById(@PathVariable Long id) {
        return new ResponseEntity<>(variationService.getAllAttributeNamesById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/attributevalues/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<String>> getAttributeValueNamesById(@PathVariable Long id) {
        return new ResponseEntity<>(variationService.getAllAttributeValueNamesById(id), HttpStatus.OK);
    }


    @GetMapping(path = "/product/{id}")
    public  ResponseEntity<Entities<VariationDTO>> geByProductId(@PathVariable Long id){
        return new ResponseEntity<>(variationService.findAllForProductId(id),HttpStatus.OK);
    }




    @GetMapping(path="/find")
    public ResponseEntity<VariationDTO> getVariationByAttributeValueNames(@RequestParam(name = "searchParam") List<String> searchParam,
                                                                          @RequestParam(name = "productId") String productId) throws Exception {
        return new ResponseEntity<>(entityToDTO(variationService.getVariationByAttributeValueIdCombination(searchParam, productId)), HttpStatus.OK);
    }


}
