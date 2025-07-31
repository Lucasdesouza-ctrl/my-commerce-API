package me.study.my.ecommerce.service;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;

}
