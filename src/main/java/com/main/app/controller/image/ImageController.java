package com.main.app.controller.image;

import com.main.app.domain.dto.image.ImageDTO;
import com.main.app.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.main.app.converter.image.ImageConverter.*;


@RestController
@RequestMapping("/image")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService imageService;

    @GetMapping(path = "/{productId}")
    public ResponseEntity<List<ImageDTO>> getAllByProductId(@PathVariable Long productId) {
        return new ResponseEntity<>(listToDTOList(imageService.getAllImagesByProductId(productId)), HttpStatus.OK);
    }


    @PutMapping(path = "/{id}/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImageDTO> setNewPrimaryImage(@PathVariable Long id, @PathVariable Long productId) {
        return new ResponseEntity<>(entityToDTO(imageService.setPrimary(id, productId)), HttpStatus.OK);
    }


    @DeleteMapping("/{id}/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImageDTO> delete(@PathVariable Long id, @PathVariable Long productId) {
        return new ResponseEntity<>(entityToDTO(imageService.delete(id, productId)), HttpStatus.OK);
    }





    @GetMapping(path = "/variation/{variationId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ImageDTO>> getAllByVariationId(@PathVariable Long variationId) {
        return new ResponseEntity<>(listToDTOList(imageService.getAllImagesByVariationId(variationId)), HttpStatus.OK);
    }

    @PutMapping(path = "/variation/{id}/{variationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImageDTO> setNewPrimaryImageForVariation(@PathVariable Long id, @PathVariable Long variationId) {
        return new ResponseEntity<>(entityToDTO(imageService.setPrimaryImageForVariation(id, variationId)), HttpStatus.OK);
    }


    @DeleteMapping("/variation/{id}/{variationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImageDTO> deleteForVariation(@PathVariable Long id, @PathVariable Long variationId) {
        return new ResponseEntity<>(entityToDTO(imageService.deleteForVariation(id, variationId)), HttpStatus.OK);
    }

}
