package com.main.app.service.user_favourites;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user_favourites.UserFavouritesDTO;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.domain.model.variation.Variation;
import com.main.app.repository.user.UserRepository;
import com.main.app.repository.user_favourites.UserFavouritesRepository;
import com.main.app.repository.variation.VariationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;

import static com.main.app.converter.user_favourites.UserFavouritesConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.favouritesToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFavouritesImpl implements UserFavouritesService{

    private final UserFavouritesRepository userFavouritesRepository;

    private final UserRepository userRepository;

    private final VariationRepository variationRepository;

    @Override
    public Entities getAllFavouritesForUserId(Long id, Pageable pageable) {

        Page<UserFavourites> pagedFavourites = userFavouritesRepository.findAllByUserIdAndDeletedFalse(id,pageable);
        List<Long> ids = favouritesToIds(pagedFavourites);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<UserFavourites> usersFavourites = userFavouritesRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(usersFavourites));
        entities.setTotal(pagedFavourites.getTotalElements());

        return entities;
    }

    @Override
    public UserFavourites setFavouriteForUser(Long userId, Long variationId) {

        //TODO provjera da li je CurrentUser

        if(!userRepository.findOneByIdAndDeletedFalse(userId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }
        if(!variationRepository.findOneByIdAndDeletedFalse(variationId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST);
        }
        User user = userRepository.findOneByIdAndDeletedFalse(userId).get();
        Variation variation = variationRepository.findOneByIdAndDeletedFalse(variationId).get();

        List<UserFavourites> allFavourites = userFavouritesRepository.findAllByUserId(userId);

        for(int i=0;i<allFavourites.size();i++){
            if(allFavourites.get(i).getVariation().getId() == variationId){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_WITH_NAME_ALREADY_SET);
            }
        }

        UserFavourites userFavourites = new UserFavourites(user,variation);

        UserFavourites savedUserFavourite = userFavouritesRepository.save(userFavourites);

        return savedUserFavourite;

    }

    @Override
    public UserFavourites unSetFavouriteForUser(Long userId,Long variationId) {

        //TODO provjera da li je CurrentUser

        if(!userRepository.findOneByIdAndDeletedFalse(userId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }
        if(!variationRepository.findOneByIdAndDeletedFalse(variationId).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST);
        }

        List<UserFavourites> allFavourites = userFavouritesRepository.findAllByUserId(userId);
        UserFavourites savedFavourite = null;

        for (UserFavourites userFavourite: allFavourites) {
            if(userFavourite.getVariation().getId() == variationId){
                userFavourite.setDeleted(true);
                userFavourite.setDateDeleted(Calendar.getInstance().toInstant());
                savedFavourite = userFavouritesRepository.save(userFavourite);
            }
        }

        if(savedFavourite == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_TO_UNSET_NOT_EXIST);
        }

        return savedFavourite;

    }
}
