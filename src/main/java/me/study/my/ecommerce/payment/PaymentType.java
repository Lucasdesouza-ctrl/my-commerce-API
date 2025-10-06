package me.study.my.ecommerce.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class PaymentType {

    private String cardNumber;

    private String bankName;

    private String cardHolder;

    private String expiration;

    private String cvv;
}
