package me.study.my.ecommerce.payment;

import me.study.my.ecommerce.entity.CartItemEntity;
import me.study.my.ecommerce.entity.ProductEntity;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.repository.ProductRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceTest.class);

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  ShoppingCartRepository cartRepository;

    @InjectMocks
    private PaymentService paymentService;

    private PaymentDTO paymentDTO;

    private UserEntity user;
    private ShoppingCartEntity cart;
    private ProductEntity product;



    @BeforeEach
    void setup (){

        var authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("user@email.com");

        product = new ProductEntity();
        product.setQuantity(20);
        product.setPrice(50.0);

        var item = new CartItemEntity();
        item.setProduct(product);
        item.setQuantity(2);
        item.setUnitPrice(50.0);

        cart = new ShoppingCartEntity();
        cart.setItens(new ArrayList<>(List.of(item)));

        user = new UserEntity();
        user.setShoppingCart(cart);

        when(userRepository.findByEmail("user@email.com"))
                .thenReturn(Optional.of(user));

        paymentDTO = new PaymentDTO("Credit",
                "Itau",
                3,
                "123456789",
                "User",
                "09/26",
                "233");



    }

    @Test
    @DisplayName("Should Create Payment With Success")
    public void shouldCreatePaymentWithSuccess(){

        PaymentResponse response = paymentService.payment(paymentDTO);

        assertNotNull(response);
        assertEquals("Your credit payment was successful. Value: 100.0 in 3", response.paymentMenssage());
        log.info("Payment response: {}", response.paymentMenssage());


    }



}