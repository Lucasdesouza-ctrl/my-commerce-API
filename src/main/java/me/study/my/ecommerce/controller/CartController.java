package me.study.my.ecommerce.controller;

import lombok.RequiredArgsConstructor;

import me.study.my.ecommerce.dto.CartResponseDTO;
import me.study.my.ecommerce.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shopping_cart")
public class CartController {

    private final ShoppingCartService cartService;

    @PostMapping("{productId}/{quantity}")
    public ResponseEntity<CartResponseDTO> addItemInCart(
            @PathVariable UUID productId
            , @PathVariable int quantity
    ) {
      CartResponseDTO addItem = cartService.addProductToCart(productId, quantity);

        return ResponseEntity.ok(addItem);
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getItemsInCart (){

        CartResponseDTO getItems = cartService.getItemsInCart();
        return ResponseEntity.ok(getItems);
    }


    @DeleteMapping("{itemId}")
    public ResponseEntity <CartResponseDTO> deleteItemInCart (@PathVariable UUID itemId){

        CartResponseDTO cartResponse = cartService.deleteItemInCart(itemId);

        return ResponseEntity.ok(cartResponse);

    }

    @PutMapping("{itemId}/{quantity}")
    public ResponseEntity <CartResponseDTO> updateItemQuantity (
            @PathVariable UUID itemId,
            @PathVariable int quantity){

        CartResponseDTO cartResponse = cartService.updateQuantity(itemId, quantity);

        return  ResponseEntity.ok(cartResponse);

    }
}
