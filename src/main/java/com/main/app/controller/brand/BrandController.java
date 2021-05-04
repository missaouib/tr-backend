package com.main.app.controller.brand;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.brand.BrandDTO;
import com.main.app.domain.model.brand.Brand;
import com.main.app.service.brand.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.main.app.converter.brand.BrandConverter.*;


@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrandController {

    private final BrandService brandService;

    @GetMapping(path="/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Brand>> getAll(){
        return new ResponseEntity<>(brandService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path="/search")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Brand>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(brandService.getAllBySearchParam(searchParam, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BrandDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(brandService.getOne(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BrandDTO> add(@RequestBody @Valid BrandDTO brandDTO) {
        return new ResponseEntity<>(entityToDTO(brandService.save(DTOtoEntity(brandDTO))), HttpStatus.OK);
    }

    @PostMapping(path = "/image/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadImage(@PathVariable Long id, @RequestPart MultipartFile[] images, @RequestPart MultipartFile[] homepageImages) throws IOException {
        brandService.uploadImage(id, images, homepageImages);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BrandDTO> edit(@RequestBody BrandDTO brandDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(brandService.edit(DTOtoEntity(brandDTO), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BrandDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(brandService.delete(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/safe/{id}")
    public ResponseEntity<BrandDTO> getOneById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToSafeDTO(brandService.getOne(id)), HttpStatus.OK);
    }

}
