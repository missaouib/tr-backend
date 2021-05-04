package com.main.app.repository.user_favourites;

import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user_favourites.UserFavourites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavouritesRepository extends JpaRepository<UserFavourites, Long> {

    Page<UserFavourites> findAllByUserIdAndDeletedFalse(Long userId,Pageable pageable);

    Page<UserFavourites> findAllByIdInAndDeletedFalse(List<Long> idsList, Pageable pageable);

    List<UserFavourites> findAllByUserId(Long userId);

    Optional<UserFavourites> findByVariationId(Long variationId);

}
