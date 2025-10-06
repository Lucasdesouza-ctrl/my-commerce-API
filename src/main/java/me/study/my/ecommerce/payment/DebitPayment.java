package me.study.my.ecommerce.payment;

public class DebitPayment extends PaymentType{


    public DebitPayment(String cardNumber,
                        String bankName,
                        String cardHolder,
                        String expiration,
                        String cvv) {
        super(cardNumber, bankName, cardHolder, expiration, cvv);
    }
}
