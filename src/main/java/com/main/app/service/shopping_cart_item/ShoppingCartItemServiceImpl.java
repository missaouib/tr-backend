package com.main.app.service.shopping_cart_item;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.repository.shopping_cart_item.ShoppingCartItemRepository;
import com.main.app.service.product.ProductService;
import com.main.app.service.variation.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.main.app.static_data.Messages.SHOPPING_CART_ITEM_NOT_EXIST;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final VariationService variationService;

    private final ProductService productService;

    @Override
    public ShoppingCartItem findById(Long id) {
        return shoppingCartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, SHOPPING_CART_ITEM_NOT_EXIST));
    }

    @Override
    public ShoppingCartItem removeItemById(Long id) {
        ShoppingCartItem shoppingCartItem = findById(id);
        shoppingCartItem.setDeleted(true);
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem changeItemQuantity(Long id, ShoppingCartItemDto dto) {
        ShoppingCartItem shoppingCartItem = findById(id);
        shoppingCartItem.setQuantity(dto.getQuantity());
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem create(ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setQuantity(shoppingCartItemDto.getQuantity());
        shoppingCartItem.setVariation(shoppingCartItemDto.getVariationId() != null ? variationService.getOne(shoppingCartItemDto.getVariationId()) : null );
        shoppingCartItem.setProduct(shoppingCartItemDto.getProductId() != null ? productService.getOne(shoppingCartItemDto.getProductId()) : null);
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem findByVariationAndShoppingCart(Long variationId, Long shoppingCartId) {
        return shoppingCartItemRepository.findByVariationIdAndShoppingCartId(variationId, shoppingCartId);
    }

    @Override
    public ShoppingCartItem findByProductAndShoppingCart(Long productId, Long shoppingCartId) {
        return shoppingCartItemRepository.findByProductIdAndShoppingCartId(productId, shoppingCartId);
    }
}
