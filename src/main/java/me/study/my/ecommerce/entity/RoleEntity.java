package me.study.my.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    @Getter
    public enum RolesEnum {
        ADMIN(1L),
        BASIC(2L);

        long roleId;

        RolesEnum(long roleId) {
            this.roleId = roleId;
        }
    }
}

