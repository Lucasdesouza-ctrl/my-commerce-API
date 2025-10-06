package me.study.my.ecommerce.payment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(PaymentEntity payment);
    PaymentEntity toEntity (PaymentDTO paymentDTO);
}
