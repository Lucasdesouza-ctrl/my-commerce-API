package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.CartMapper;
import me.study.my.ecommerce.dto.CartResponseDTO;
import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ProductEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.exception.ResourceNotFoundException;
import me.study.my.ecommerce.repository.ProductRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {
    @Value("${admin.email}")
    private String ADMIN_EMAIL;

    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartResponseDTO addProductToCart(UUID productId, int quantity) {
        var user = currentUser();

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Product quantity is not valid.");
        }
        if (product.getQuantity() == 0) {
            throw new RuntimeException("Product quantity is not valid.");
        }


        var cart = user.getShoppingCart();

        if (cart == null) {
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
            CartItemEntity newItem = new CartItemEntity();
            newItem.setId(product.getId());
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setUnitPrice(product.getPrice());

            cart.getItens().add(newItem);
        }

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO getItemsInCart() {
        var user = currentUser();

        var cart = user.getShoppingCart();
        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO deleteItemInCart(UUID itemId) {
        var user = currentUser();
        var cart = user.getShoppingCart();

        CartItemEntity itemToDelete = getItemInCart(itemId, cart);
        cart.getItens().remove(itemToDelete);

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO updateQuantity(UUID itemId, int newQuantity) {
        if (newQuantity <= 0) throw new RuntimeException("New quantity invalid");

        var user = currentUser();
        var cart = user.getShoppingCart();

        var itemToUpdate = getItemInCart(itemId, cart);
        itemToUpdate.setQuantity(newQuantity);

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    private UserEntity currentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (email.equals(ADMIN_EMAIL)) {
            throw new RuntimeException("Admin user are not able to have a Cart.");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private CartItemEntity getItemInCart(UUID itemId, ShoppingCartEntity cart) {
        return cart.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item not found in cart"));
    }

}
