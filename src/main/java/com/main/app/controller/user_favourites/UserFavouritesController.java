package com.main.app.controller.user_favourites;


import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user_favourites.UserFavouritesDTO;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.domain.model.variation.Variation;
import com.main.app.service.user_favourites.UserFavouritesService;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.main.app.converter.user_favourites.UserFavouritesConverter.entityToDTO;

@RestController
@RequestMapping("/user_favourites")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFavouritesController {

    private final UserFavouritesService userFavouritesService;
    private final VariationService variationService;

    @GetMapping("/all")
    public ResponseEntity<Entities<UserFavourites>> getAllForUser(Pageable pageable, @RequestBody UserFavouritesDTO userFavouritesDTO){
        return new ResponseEntity<>(userFavouritesService.getAllFavouritesForUserId(userFavouritesDTO.getUser_id(),pageable), HttpStatus.OK);
    }

    @PostMapping("/set")
    public ResponseEntity<UserFavouritesDTO> setFavourite(@RequestBody UserFavouritesDTO userFavouritesDTO){
        return new ResponseEntity<>(entityToDTO(userFavouritesService.setFavouriteForUser(userFavouritesDTO.getUser_id(),userFavouritesDTO.getVariation_id())),HttpStatus.OK);
    }

    @PostMapping("/unset")
    public ResponseEntity<UserFavouritesDTO> unSetFavourite(@RequestBody UserFavouritesDTO userFavouritesDTO) {
        return new ResponseEntity<>(entityToDTO(userFavouritesService.unSetFavouriteForUser(userFavouritesDTO.getUser_id(),userFavouritesDTO.getVariation_id())),HttpStatus.OK);
    }
}
