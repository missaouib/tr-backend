package com.main.app.controller.attribute_value;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.attribute_value.AttributeValueDTO;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.service.attribute_value.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.main.app.converter.attribute_value.AttributeValueConverter.*;

@RestController
@RequestMapping("/attributevalue")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    @GetMapping(path="/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeValue>> getAll(){
        return new ResponseEntity<>(attributeValueService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<AttributeValue>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeValueService.getAllBySearchParam(searchParam, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeValueDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeValueService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/attribute/{attributeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AttributeValueDTO>> getByAttributeId(@PathVariable Long attributeId, @RequestParam(name = "searchParam") String searchParam, Pageable pageable) {
        return new ResponseEntity<>(listToDTOList(attributeValueService.getAllByAttributeIdWithPageable(attributeId, searchParam, pageable)), HttpStatus.OK);
    }

    @GetMapping(path = "/attr/{name}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AttributeValueDTO>> getByAttributeName(@PathVariable String name, Pageable pageable) {
        return new ResponseEntity<>(listToDTOList(attributeValueService.getAllByAttributeNameWithPageable(name,pageable)), HttpStatus.OK);
    }


    @PostMapping(path = "/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeValueDTO> add(@RequestBody @Valid AttributeValueDTO attributeValueDTO) {
        return new ResponseEntity<>(entityToDTO(attributeValueService.save(DTOtoEntity(attributeValueDTO))), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeValueDTO> edit(@RequestBody AttributeValueDTO attributeValueDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeValueService.edit(DTOtoEntity(attributeValueDTO), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeValueDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeValueService.delete(id)), HttpStatus.OK);
    }

}
