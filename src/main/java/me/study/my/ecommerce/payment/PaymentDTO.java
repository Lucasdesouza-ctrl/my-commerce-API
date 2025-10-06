package me.study.my.ecommerce.payment;

public record PaymentDTO(String typename,
                         String bankName,
                         int installment,
                         String cardNumber,
                         String cardHolder,
                         String expiration,
                         String cvv) {
}


