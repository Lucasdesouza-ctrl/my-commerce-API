package me.study.my.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import me.study.my.ecommerce.entity.ShoppingCartEntity;
import me.study.my.ecommerce.entity.UserEntity;
import me.study.my.ecommerce.exception.ResourceNotFoundException;
import me.study.my.ecommerce.repository.ProductRepository;
import me.study.my.ecommerce.repository.ShoppingCartRepository;
import me.study.my.ecommerce.repository.UserRepository;
import me.study.my.ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${admin.email}")
    private String ADMIN_EMAIL;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository cartRepository;


    @Transactional
    public PaymentResponse payment(PaymentDTO paymentDTO) {

        PaymentType payment;
        String response;

        var user = currentUser();

        var cart = user.getShoppingCart();

        var value = cart.getTotalPrice();

        var cartItems = cart.getItens();


        cartItems.forEach(item -> {

            var product = item.getQuantity();

        });

        if (paymentDTO.typename().equals("Debit")) {


            payment = new DebitPayment(paymentDTO.cardNumber(),
                    paymentDTO.bankName(),
                    paymentDTO.cardHolder(),
                    paymentDTO.expiration(),
                    paymentDTO.cvv());

            response = "Your debit payment was successful. Value:" + value;

        } else if (paymentDTO.typename().equals("Credit")) {
            payment = new CreditPayment(paymentDTO.cardNumber(),
                    paymentDTO.bankName(),
                    paymentDTO.cardHolder(),
                    paymentDTO.expiration(),
                    paymentDTO.cvv(),
                    paymentDTO.installment());
            response = "Your credit payment was successful. Value: " + value + " in " + paymentDTO.installment();

        } else {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentDTO.typename());
        }

        cartItems.forEach(item ->{
            var product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

        });

        cart.getItens().clear();
        cartRepository.save(cart);


        return new PaymentResponse(response);
    }

    private UserEntity currentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (email.equals(ADMIN_EMAIL)) {
            throw new IllegalArgumentException("Admin user are not able to have a Cart.");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
