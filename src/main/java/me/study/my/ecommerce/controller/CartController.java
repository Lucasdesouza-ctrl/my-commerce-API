package me.study.my.ecommerce.controller;

import lombok.RequiredArgsConstructor;

import me.study.my.ecommerce.dto.CartItemDTO;
import me.study.my.ecommerce.dto.CartResponseDTO;
import me.study.my.ecommerce.dto.ItemMapper;
import me.study.my.ecommerce.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shopping_cart")
public class CartController {

    private final ShoppingCartService cartService;

    private final ItemMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_basic')")
    public ResponseEntity<CartResponseDTO> addItemInCart(
            @PathVariable UUID productId
            , @PathVariable int quantity
    ) {
      CartResponseDTO addItem = cartService.addProductToCart(productId, quantity);

        return ResponseEntity.ok(addItem);
    }
}
