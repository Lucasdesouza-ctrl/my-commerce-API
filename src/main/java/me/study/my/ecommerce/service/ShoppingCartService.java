package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.dto.CartMapper;
import me.study.my.ecommerce.dto.CartResponseDTO;
import me.study.my.ecommerce.dto.ItemMapper;
import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ProductEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
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
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartResponseDTO addProductToCart (UUID productId, int quantity){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        if(product.getQuantity() < quantity){
            throw new RuntimeException("Product quantity is not valid.");
        }
        if(product.getQuantity() == 0){
            throw new RuntimeException("Product quantity is not valid.");
        }



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


            CartItemEntity newItem = new CartItemEntity();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setUnitPrice(product.getPrice());

            cart.getItens().add(newItem);
        }

        cartRepository.save(cart);

        cart.getItens().size();
        CartResponseDTO cartResponse = cartMapper.toDTO(cart);

        return  cartResponse;
    }

    public CartResponseDTO getItemsInCart (){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found") );

        var cart = user.getShoppingCart();

        CartResponseDTO cartResponse = cartMapper.toDTO(cart);

        return cartResponse;
    }

    public CartResponseDTO deleteItemInCart (UUID itemId){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var cart = user.getShoppingCart();

        CartItemEntity itemToDelete = cart.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item not found in cart"));

           cart.getItens().remove(itemToDelete);

           cartRepository.save(cart);

           CartResponseDTO cartResponse = cartMapper.toDTO(cart);

           return cartResponse;
    }

    public CartResponseDTO updateQuantity(UUID itemId, int newQuantity){

        if (newQuantity <= 0){
            throw new RuntimeException("New quantity invalid");
        }

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartEntity  cart = user.getShoppingCart();

        CartItemEntity itemToUpdate = cart.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item not found in cart"));


        itemToUpdate.setQuantity(newQuantity);

        cartRepository.save(cart);

        CartResponseDTO cartResponse = cartMapper.toDTO(cart);

        return cartResponse;
    }




}
