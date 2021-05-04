package com.main.app.controller.shopping_cart;

import com.main.app.domain.dto.shopping_cart.ShoppingCartDto;
import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.service.shopping_cart.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.main.app.converter.shopping_cart.ShoppingCartConverter.toDto;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDto> getShoppingCartById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(toDto(shoppingCartService.findShoppingCartById(id)));
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDto> initializeShoppingCart(@RequestBody ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart();
        return ResponseEntity.ok().body(toDto(shoppingCartService.addItemToShoppingCart(shoppingCart.getId(), shoppingCartItemDto)));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<ShoppingCartDto> addItemToShoppingCart(@PathVariable("id") Long id, @RequestBody ShoppingCartItemDto shoppingCartItemDto) {
        return ResponseEntity.ok().body(toDto(shoppingCartService.addItemToShoppingCart(id, shoppingCartItemDto)));
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<ShoppingCartDto> removeItemFromShoppingCart(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok().body(toDto(shoppingCartService.removeShoppingCartItem(id, itemId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeShoppingCart(@PathVariable("id") Long id) {
        shoppingCartService.removeAndClearShoppingCart(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<ShoppingCartDto> changeShoppingCartItemQuantity(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId, @RequestBody ShoppingCartItemDto shoppingCartItemDto) {
        return ResponseEntity.ok().body(toDto(shoppingCartService.changeShoppingCartItemQuantity(id, itemId, shoppingCartItemDto)));
    }

    @GetMapping("/{id}/size")
    public ResponseEntity<Long> getShoppingCartSize(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(shoppingCartService.getShoppingCartSize(id));
    }


    @PostMapping("/{id}/connect")
    public ResponseEntity<ShoppingCartDto> connectShoppingCartToCurrentUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(toDto(shoppingCartService.connectShoppingCartToUser(id)));
    }

    @GetMapping
    public ResponseEntity<ShoppingCartDto> getUserShoppingCart() {
        return ResponseEntity.ok().body(toDto(shoppingCartService.findShoppingCartByUser()));
    }
}