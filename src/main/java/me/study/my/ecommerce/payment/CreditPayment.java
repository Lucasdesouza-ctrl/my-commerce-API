package me.study.my.ecommerce.payment;


public class CreditPayment extends PaymentType{



    private int installment;


    public CreditPayment(String cardNumber,
                         String bankName,
                         String cardHolder,
                         String expiration,
                         String cvv,
                         int installment) {
        super(cardNumber, bankName, cardHolder, expiration, cvv);
    }
}
