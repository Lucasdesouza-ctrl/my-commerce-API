package me.study.my.ecommerce.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.study.my.ecommerce.entity.UserEntity;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    private UserEntity userPayment;

    private PaymentType paymentType;
}
