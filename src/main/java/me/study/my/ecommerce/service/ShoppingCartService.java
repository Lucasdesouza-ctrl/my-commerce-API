package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.ProductDTO;
import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ProductEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.CartItemRepository;
import me.study.my.ecommerce.repository.ProductRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartResponseDTO addProductToCart (UUID productId, int quantity){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var cart = user.getShoppingCart();

        if (cart == null){
            cart = new ShoppingCartEntity();
            cart.setUser(user);

            cartRepository.save(cart);
        }
        Optional<CartItemEntity> existingItem = cart.getItens().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItemEntity newItem = new CartItemEntity();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);

            cart.getItens().add(newItem);
        }

        cartRepository.save(cart);



        return cart;
    }

}
