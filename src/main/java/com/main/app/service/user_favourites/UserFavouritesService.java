package com.main.app.service.user_favourites;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.user_favourites.UserFavourites;
import org.springframework.data.domain.Pageable;

public interface UserFavouritesService {

    Entities getAllFavouritesForUserId(Long id, Pageable pageable);

    UserFavourites setFavouriteForUser(Long userId, Long variationId);

    UserFavourites unSetFavouriteForUser(Long userId,Long variationId);


}
