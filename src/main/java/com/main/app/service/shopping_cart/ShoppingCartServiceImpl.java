package com.main.app.service.shopping_cart;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.domain.model.user.User;
import com.main.app.repository.shopping_cart.ShoppingCartRepository;
import com.main.app.repository.shopping_cart_item.ShoppingCartItemRepository;
import com.main.app.repository.user.UserRepository;
import com.main.app.service.shopping_cart_item.ShoppingCartItemService;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.main.app.static_data.Messages.SHOPPING_CART_NOT_EXIST;
import static com.main.app.static_data.Messages.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartItemService shoppingCartItemService;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final CurrentUserService currentUserService;

    private final UserRepository userRepository;


    @Override
    public ShoppingCart findShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SHOPPING_CART_NOT_EXIST));
    }

    @Override
    public ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        currentUserService.getCurrentUser().ifPresent(shoppingCart::setUser);
        shoppingCart.setStatus(true);
        return shoppingCartRepository.save(shoppingCart);
    }



    @Override
    public ShoppingCart addItemToShoppingCart(Long id, ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCart shoppingCart = findShoppingCartById(id);
        ShoppingCartItem existingShoppingCartItem = new ShoppingCartItem();
        if(shoppingCartItemDto.getVariationId() != null){
             existingShoppingCartItem = shoppingCartItemService.findByVariationAndShoppingCart(shoppingCartItemDto.getVariationId(), id);
        }else{
            existingShoppingCartItem = shoppingCartItemService.findByProductAndShoppingCart(shoppingCartItemDto.getProductId(), id);
        }


        if(existingShoppingCartItem != null) {
            existingShoppingCartItem.setQuantity(existingShoppingCartItem.getQuantity() + shoppingCartItemDto.getQuantity());
        } else {
            ShoppingCartItem shoppingCartItem = shoppingCartItemService.create(shoppingCartItemDto);
            shoppingCartItem.setShoppingCart(shoppingCart);
            shoppingCartItemRepository.save(shoppingCartItem);
            shoppingCart.getItems().add(shoppingCartItem);
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeShoppingCartItem(Long id, Long itemId) {
        ShoppingCart shoppingCart = findShoppingCartById(id);
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.findById(itemId);
        shoppingCart.getItems().remove(shoppingCartItem);
        shoppingCartItemService.removeItemById(itemId);
        return shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public ShoppingCart changeShoppingCartItemQuantity(Long id, Long itemId, ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCart shoppingCart = findShoppingCartById(id);
        shoppingCartItemService.changeItemQuantity(itemId, shoppingCartItemDto);
        return shoppingCart;
    }

    @Override
    public Long getShoppingCartSize(Long id) {
        return shoppingCartItemRepository.countByShoppingCartId(id);
    }



    @Override
    public ShoppingCart connectShoppingCartToUser(Long id) {
//        long idd = 2;
//        User user = userRepository.findById(idd).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
//        Optional<ShoppingCart> userShoppingCart = shoppingCartRepository.findById(id);

        User user = currentUserService.getCurrentUser().orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
//        Optional<ShoppingCart> userShoppingCart = shoppingCartRepository.findByUserId(user.getId());


        //userShoppingCart.ifPresent(this::removeShoppingCart);
//        if (!userShoppingCart.isPresent()) {
//            removeShoppingCart(userShoppingCart.get());
//        }

        ShoppingCart shoppingCart = findShoppingCartById(id);
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }


    private void removeShoppingCart(ShoppingCart shoppingCart){
        for (ShoppingCartItem item: shoppingCart.getItems()) {
            item.setDeleted(true);
            shoppingCartItemRepository.save(item);
        }
        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public void removeAndClearShoppingCart(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SHOPPING_CART_NOT_EXIST));
        for(ShoppingCartItem shoppingCartItem: shoppingCart.getItems()) {
            shoppingCartItem.setDeleted(true);
            shoppingCartItemRepository.save(shoppingCartItem);
        }
        shoppingCart.setDeleted(true);
        shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public ShoppingCart findShoppingCartByUser() {
        User user = currentUserService.getCurrentUser().orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_EXIST));
        return shoppingCartRepository.findAllByUserIdOrderByDateCreatedDesc(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SHOPPING_CART_NOT_EXIST)).get(0);
    }

}
