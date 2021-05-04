package com.main.app.controller.category;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.category.CategoryDTO;
import com.main.app.domain.model.category.Category;
import com.main.app.service.category.CategoryService;
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
import java.util.List;

import static com.main.app.converter.category.CategoryConverter.DTOtoEntity;
import static com.main.app.converter.category.CategoryConverter.entityToDTO;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping(path="/all")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Category>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }


    @GetMapping(path="/search")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<Category>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam){
        return new ResponseEntity<>(categoryService.getAllBySearchParam(searchParam, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(categoryService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/name/{name}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable String name) {
        return new ResponseEntity<>(entityToDTO(categoryService.findByCategoryName(name)), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> add(@RequestBody @Valid CategoryDTO productCategoryDTO) {
        return new ResponseEntity<>(entityToDTO(categoryService.save(DTOtoEntity(productCategoryDTO))), HttpStatus.OK);
    }

    @PostMapping(path = "/image/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void uploadImage(@PathVariable Long id, @RequestPart MultipartFile[] images) throws IOException {
        categoryService.uploadImage(id, images);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> edit(@RequestBody CategoryDTO productCategoryDTO, @PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(categoryService.edit(DTOtoEntity(productCategoryDTO), id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
        return new ResponseEntity<>(entityToDTO(categoryService.delete(id)), HttpStatus.OK);
    }

    @GetMapping(path = "/parent/{name}")
    public ResponseEntity<List<CategoryDTO>> getAllWhereNameIsParentCategory(@PathVariable String name) {
        return new ResponseEntity<>(categoryService.getAllWhereNameIsParentCategory(name), HttpStatus.OK);
    }

}
