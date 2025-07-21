package me.study.my.ecommerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CartItensEntity {

    @Id
    private Long id;

    private String name;
    private int quantity;
    private double price;
}
