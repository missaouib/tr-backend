package com.main.app.converter.user_favourites;

import com.main.app.domain.dto.attribute.AttributeDTO;
import com.main.app.domain.dto.user_favourites.UserFavouritesDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.domain.model.variation.Variation;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFavouritesConverter {



    public static UserFavouritesDTO entityToDTO(UserFavourites userFavourites){
        return UserFavouritesDTO
                .builder()
                .id(userFavourites.getId())
                .user_id(userFavourites.getUser().getId())
                .variation_id(userFavourites.getVariation().getId())
                .name(userFavourites.getVariation().getName())
                .slug(userFavourites.getVariation().getSlug())
                .sku(userFavourites.getVariation().getSku())
                .price(userFavourites.getVariation().getPrice())
                .primaryImageUrl(userFavourites.getVariation().getPrimaryImageUrl())
                .build();
    }


    public static List<UserFavouritesDTO> listToDTOList(List<UserFavourites> userFavourites) {
        return userFavourites
                .stream()
                .map(userFavourite -> entityToDTO(userFavourite))
                .collect(Collectors.toList());
    }

}
