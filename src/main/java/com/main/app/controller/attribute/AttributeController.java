package com.main.app.controller.attribute;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.attribute.AttributeDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.service.attribute.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.main.app.converter.attribute.AttributeConverter.DTOtoEntity;
import static com.main.app.converter.attribute.AttributeConverter.entityToDTO;


@RestController
@RequestMapping("/attribute")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping(path="/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Attribute>> getAll(){
        return new ResponseEntity<>(attributeService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Attribute>> getAllBySearchParam(Pageable pageable, @RequestParam(required=false , name="participatesInVariation") boolean participatesInVariation,  @RequestParam(required=false , name="enteredManually") boolean enteredManually , @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeService.getAllBySearchParam(searchParam, participatesInVariation, enteredManually ,pageable), HttpStatus.OK);
    }

    @GetMapping(path="/participation")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Attribute>> getAllParticipationFalse(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeService.getAllByFalseParticipation(searchParam,pageable), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/name")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> getByName(@RequestParam String name) {
        return new ResponseEntity<>(entityToDTO(attributeService.getOneByName(name)), HttpStatus.OK);
    }

    @GetMapping(path = "non-category")
    public ResponseEntity<Entities<Attribute>> getAllNonCategory(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(attributeService.getAllNonCategoryAttributes(searchParam,pageable), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> add(@RequestBody @Valid AttributeDTO attributeDTO) {
        return new ResponseEntity<>(entityToDTO(attributeService.save(DTOtoEntity(attributeDTO))), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> edit(@RequestBody AttributeDTO attributeDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeService.edit(DTOtoEntity(attributeDTO), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(attributeService.delete(id)), HttpStatus.OK);
    }

}
