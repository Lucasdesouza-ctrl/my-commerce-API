package me.study.my.ecommerce.repository;

import me.study.my.ecommerce.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
}
